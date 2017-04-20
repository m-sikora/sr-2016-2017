package rabbitmqlab;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zero on 2017-04-19.
 */
public class Doctor {
    public int id;
    private Channel channel;

    public Doctor() throws Exception {
        this(-1);
    }

    public Doctor(int id) throws Exception {
        this.id = id;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare("req", BuiltinExchangeType.DIRECT);

        channel.exchangeDeclare("res", BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "res", Integer.toString(id));

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                String key = envelope.getRoutingKey();

                System.out.println("Received: " + message + " (" + key + ")");
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }

    public void sendRequest(String type, String message) throws Exception{
        channel.basicPublish("req", type, null, new String(this.id + ";" + message).getBytes("UTF-8"));
    }

    public static void main (String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter doctor id: ");
        Doctor doc = new Doctor(Integer.parseInt(br.readLine()));
        System.out.println("Created doctor " + doc.id);

        while (true) {
            // read msg
            System.out.println("Enter patient data: ");
            String message = br.readLine();
            if (message == "r")
                continue;

            System.out.println("Enter operation type: ");
            String key = br.readLine();

            // break condition
            if ("exit".equals(message)) {
                break;
            }


            // publish
            doc.sendRequest(key, message);
            System.out.println("Sent: " + message + " on " + key);
        }
    }
}

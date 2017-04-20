package rabbitmqlab;

import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by zero on 2017-04-19.
 */
public class Technician {
    private ArrayList<OperationType> knownTypes = new ArrayList<>();
    private Channel channel;

    public Technician() throws Exception {
        this(OperationType.OPERATION_ANKLE, OperationType.OPERATION_ELBOW);
    }


    public Technician(OperationType type1, OperationType type2) throws Exception {
        knownTypes.add(type1);
        knownTypes.add(type2);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare("req", BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare("results", BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, "req", knownTypes.get(0).name);
        System.out.println("bound queue " + knownTypes.get(0).name);
        channel.queueBind(queueName, "req", knownTypes.get(1).name);
        System.out.println("bound queue " + knownTypes.get(1).name);
        System.out.println("created queue: " + queueName);



        // consumer (message handling)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                String key = envelope.getRoutingKey();

                String[] parts = message.split(";");
                if (parts.length != 2) {
                    return;
                }

                String docId = parts[0];
                String operationInfo = parts[1];

                System.out.println("Received: " + operationInfo + " on " + key + " from " + docId);

                System.out.println("Enter results...");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String res = br.readLine();

                channel.basicPublish("res", docId, null, res.getBytes("UTF-8"));
            }
        };

        channel.basicConsume(queueName, true, consumer);

    }

    public boolean knowsType(OperationType type) {
        if (knownTypes.contains(type))
            return true;
        return false;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the first profession: ");
        String key1 = br.readLine();
        System.out.println("Enter the second profession: ");
        String key2 = br.readLine();

        Technician tech = new Technician(OperationType.getByName(key1), OperationType.getByName(key2));

    }
}

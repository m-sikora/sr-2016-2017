package pl.edu.agh.dsrg.sr.chat;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;
import org.jgroups.util.Base64;
import pl.edu.agh.dsrg.sr.chat.protos.ChatOperationProtos;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zero on 2017-03-29.
 */
public class Channel {
    public static HashMap<String, Channel> stringToChannelMap = new HashMap<String, Channel>();
    public static HashMap<String, ArrayList<String>> stringToUsersMap = new HashMap<String, ArrayList<String>>();

    public class NicknameAlreadyInUse extends Exception {
        public NicknameAlreadyInUse(String message) {
            super(message);
        }
    }

    private ArrayList<String> users;
    private String channelName;
    private JChannel jChannel;

    public JChannel getjChannel() {
        return jChannel;
    }

    public void setjChannel(JChannel jChannel) {
        this.jChannel = jChannel;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Channel() {
        this("default channel name", "user0");
    }

    public Channel(String channelName, String userName) {
        this.setChannelName(channelName);
        this.setUsers(new ArrayList<String>());

        try {
            this.jChannel = new JChannel(false);
            ProtocolStack protocolStack = new ProtocolStack();
            jChannel.setProtocolStack(protocolStack);
            protocolStack.addProtocol(new UDP())
                    .addProtocol(new PING())
                    .addProtocol(new MERGE3())
                    .addProtocol(new FD_SOCK())
                    .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                    .addProtocol(new VERIFY_SUSPECT())
                    .addProtocol(new BARRIER())
                    .addProtocol(new NAKACK2())
                    .addProtocol(new UNICAST3())
                    .addProtocol(new STABLE())
                    .addProtocol(new GMS())
                    .addProtocol(new UFC())
                    .addProtocol(new MFC())
                    .addProtocol(new FRAG2())
                    .addProtocol(new STATE_TRANSFER())
                    .addProtocol(new FLUSH());
            protocolStack.init();

            jChannel.connect(channelName);
            final Channel currentChannel = this;
            jChannel.setReceiver(new ReceiverAdapter(){
                @Override
                public void receive(Message msg) {
                    try {
                        ChatOperationProtos.ChatMessage chatMessage =
                                ChatOperationProtos.ChatMessage.parseFrom(msg.getBuffer());
                        System.out.println(msg.getSrc().toString() + " " + chatMessage.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void setState(InputStream inputStream) {

                }
            });
        } catch (Exception e) {e.printStackTrace();}

        this.addUser(userName);
        try {
            if (!stringToChannelMap.containsKey(channelName))
                stringToChannelMap.put(channelName, this);
            Main.managementChannel.getState(null, 9999);
        } catch (Exception e) {e.printStackTrace();}

        Main.sendChatOperation(userName, channelName, jChannel, ChatOperationProtos.ChatAction.ActionType.JOIN);



        stringToChannelMap.put(channelName, this);
    }

    public void addUser(String nickname) {
        if (users.contains(nickname)) {

        } else {
            this.users.add(nickname);
        }
    }

    public void removeUser(String nickname) throws NicknameAlreadyInUse {
        if (users.contains(nickname)) {
            users.remove(nickname);
        }
    }

    public void leaveMe(String nickname) {
        Main.sendChatOperation(nickname, channelName, jChannel, ChatOperationProtos.ChatAction.ActionType.LEAVE);
    }
}

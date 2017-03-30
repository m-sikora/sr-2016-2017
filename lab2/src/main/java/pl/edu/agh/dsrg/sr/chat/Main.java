package pl.edu.agh.dsrg.sr.chat; /**
 * Created by zero on 2017-03-29.
 */

import jdk.nashorn.internal.scripts.JO;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;
import pl.edu.agh.dsrg.sr.chat.protos.ChatOperationProtos;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;

import pl.edu.agh.dsrg.sr.chat.protos.ChatOperationProtos;

import static pl.edu.agh.dsrg.sr.chat.Channel.stringToChannelMap;
import static pl.edu.agh.dsrg.sr.chat.Channel.stringToUsersMap;

public class Main {
    public static void sendChatOperation(String nickname, String channelName, JChannel jChannel,
                                         ChatOperationProtos.ChatAction.ActionType actionType) {
        byte[] bytes = ChatOperationProtos.ChatAction.newBuilder().
                setNickname(nickname).
                setChannel(channelName).
                setAction(actionType).build().toByteArray();

        Message msg = new Message();
        msg.setBuffer(bytes);
        try {
            managementChannel.send(msg);
            System.out.println("sent " + actionType.toString() + " to " + channelName + " as " + nickname);
        } catch (Exception e) {e.printStackTrace();}
    }

    static boolean exit = false;
    static BufferedReader ioInput = new BufferedReader(new InputStreamReader(System.in));
    static String nickname = null;
    static ArrayList<String> users = new ArrayList<String>();

    static JChannel managementChannel;
    static String managementChannelName = "ChatManagement321321";
    static ProtocolStack protocolStack = new ProtocolStack();


    public static void addUser(String nickname) {
        if (!users.contains(nickname)) {
            users.add(nickname);
        }
    }

    public static void removeUser(String nickname) {
        if (users.contains(nickname)) {
            users.remove(nickname);
        }
    }

    public static void rollback() {
        sendChatOperation(nickname, managementChannelName, managementChannel,
                ChatOperationProtos.ChatAction.ActionType.LEAVE);
        exit = true;
    }

    public static String joinChannel(String channelName) {
        if (stringToChannelMap.containsKey(channelName)) {
            return "already joined channel " + channelName;
        }

        stringToChannelMap.put(channelName, new Channel(channelName, nickname));
        return "joined channel " + channelName;
    }

    public static String leaveChannel(String channelName) {
        if (!stringToChannelMap.containsKey(channelName)) {
            return "not in channel " + channelName;
        }

        Channel channel = stringToChannelMap.get(channelName);
        channel.leaveMe(nickname);
        stringToChannelMap.remove(channelName);
        return "left channel " + channelName;
    }

    public static String printChannelsInfo() {
        System.out.println("Currently in channels:");
        for (Channel c : stringToChannelMap.values()) {
            System.out.println(c.getChannelName());
            System.out.print( " along with users: [\n");
            for (String s : c.getUsers()) {
                System.out.println("     " + s);
            }
            System.out.print( "]\n\n");
        }

        System.out.println("Users found in the network:");
        for (String s : users) {
            System.out.println("     " + s);
        }

        return "";
    }

    public static String printFullChannelsInfo() {
        System.out.println("Found channels:");
        for (String s : stringToUsersMap.keySet()) {
            System.out.print(s);
            System.out.print( ": [\n");
            for (String u : stringToUsersMap.get(s)) {
                System.out.println("     " + u);
            }
            System.out.print( "]\n\n");
        }

        return "";
    }

    public static void init() {
        try {
            managementChannel = new JChannel(false);
            managementChannel.setProtocolStack(protocolStack);

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

            managementChannel.connect(managementChannelName);
            managementChannel.setReceiver(new ReceiverAdapter(){
                @Override
                public void receive(Message msg) {
                    try {
                        ChatOperationProtos.ChatAction chatAction =
                                ChatOperationProtos.ChatAction.parseFrom(msg.getBuffer());

                        if (chatAction.getAction() == ChatOperationProtos.ChatAction.ActionType.JOIN) {
                            addUser(chatAction.getNickname());

                            if (stringToChannelMap.containsKey(chatAction.getChannel())) {
                                stringToChannelMap.get(chatAction.getChannel())
                                        .addUser(chatAction.getNickname());
                            }

                            if (!stringToUsersMap.containsKey(chatAction.getChannel())) {
                                stringToUsersMap.put(chatAction.getChannel(), new ArrayList<String>());
                            } if (!stringToUsersMap.get(chatAction.getChannel())
                                    .contains(chatAction.getNickname())) {
                                stringToUsersMap.get(chatAction.getChannel()).add(chatAction.getNickname());
                            }

                        } else if(chatAction.getAction() == ChatOperationProtos.ChatAction.ActionType.LEAVE) {
                            if (chatAction.getChannel() == managementChannelName)
                                removeUser(chatAction.getNickname());
                            else if (stringToChannelMap.containsKey(chatAction.getChannel())) {
                                stringToChannelMap.get(chatAction.getChannel())
                                        .removeUser(chatAction.getNickname());
                            }

                            if (stringToUsersMap.containsKey(chatAction.getChannel()) &&
                                    stringToUsersMap.get(chatAction.getChannel())
                                            .contains(chatAction.getNickname())) {
                                stringToUsersMap.get(chatAction.getChannel()).remove(chatAction.getNickname());
                            }
                        }

                        System.out.println("Received (r) " + chatAction.getAction().toString() + " on " +
                                chatAction.getChannel() + " related to " + chatAction.getNickname());
                    }
                    catch (Exception e) {e.printStackTrace();}
                }

                @Override
                public void setState(InputStream inputStream) {
                	System.out.println("@@@@@@@@@@@@@@@@@@@@@@#!@#@!#@!CHCHEUCHE");
                    try {
                        ChatOperationProtos.ChatState chatState =
                                ChatOperationProtos.ChatState.parseFrom(inputStream);
                        for(ChatOperationProtos.ChatAction chatAction : chatState.getStateList()){
                            if (chatAction.getAction() == ChatOperationProtos.ChatAction.ActionType.JOIN) {
                                addUser(chatAction.getNickname());

                                if (stringToChannelMap.containsKey(chatAction.getChannel())) {
                                    stringToChannelMap.get(chatAction.getChannel())
                                            .addUser(chatAction.getNickname());
                                }

                                if (!stringToUsersMap.containsKey(chatAction.getChannel())) {
                                    stringToUsersMap.put(chatAction.getChannel(), new ArrayList<String>());
                                } else if (!stringToUsersMap.get(chatAction.getChannel())
                                        .contains(chatAction.getNickname())) {
                                    stringToUsersMap.get(chatAction.getChannel()).add(chatAction.getNickname());
                                }

                            } else if(chatAction.getAction() == ChatOperationProtos.ChatAction.ActionType.LEAVE) {
                                if (chatAction.getChannel() == managementChannelName)
                                    removeUser(chatAction.getNickname());
                                else if (stringToChannelMap.containsKey(chatAction.getChannel())) {
                                    stringToChannelMap.get(chatAction.getChannel())
                                            .removeUser(chatAction.getNickname());
                                }
                            }

                            System.out.println("Received (s) " + chatAction.getAction().toString() + " on " +
                                    chatAction.getChannel() + " related to " + chatAction.getNickname());
                        }
                    } catch (Exception e) {e.printStackTrace();}
                }

                @Override
                public void getState(OutputStream outputStream) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    ChatOperationProtos.ChatState.Builder builder = ChatOperationProtos.ChatState.newBuilder();
                    for (Channel c : stringToChannelMap.values()) {
                        for (String s : c.getUsers()) {
                            builder.addState(ChatOperationProtos.ChatAction.newBuilder().setNickname(s)
                                    .setChannel(c.getChannelName())
                                    .setAction(ChatOperationProtos.ChatAction.ActionType.JOIN)).build();
                        }
                    }
                    for (String s : users) {
                        builder.addState(ChatOperationProtos.ChatAction.newBuilder().setNickname(s)
                                .setChannel(managementChannelName)
                                .setAction(ChatOperationProtos.ChatAction.ActionType.JOIN)).build();
                    }
                    try {
                        builder.build().writeTo(outputStream);
                    } catch (IOException e) {e.printStackTrace();}
                }
            });

            managementChannel.getState(null, 9999);

            sendChatOperation(nickname, managementChannelName, managementChannel,
                    ChatOperationProtos.ChatAction.ActionType.JOIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String updateChannelList() {
        return "error";
    }

    public static String parseCommand(String cmd) {
        if (Pattern.compile("/setname \\w*").matcher(cmd).matches()) {
            nickname = cmd.substring(9);
            return "nickname set";
        }
        else if (Pattern.compile("/setname .*").matcher(cmd).matches()) {
            return "incorrect nickname format";
        }
        else if (Pattern.compile("/checkname").matcher(cmd).matches()) {
            if (nickname != null) {
                return "current nickname: " + nickname;
            }
            else
                return "no nickname specified";
        }
        else if (Pattern.compile("/exit").matcher(cmd).matches()) {
            rollback();
            return "";
        }
        else if (Pattern.compile("/browse").matcher(cmd).matches()) {
            return printChannelsInfo();
        }
        else if (Pattern.compile("/browseAll").matcher(cmd).matches()) {
            return printFullChannelsInfo();
        }
        else if (Pattern.compile("/join \\w*").matcher(cmd).matches()) {
            return joinChannel(cmd.substring(6));
        }
        else if (Pattern.compile("/leave \\w*").matcher(cmd).matches()) {
            leaveChannel(cmd.substring(7));
            return "left";
        }

        for(Channel c : stringToChannelMap.values())
            try {
                c.getjChannel().send(null, ChatOperationProtos.ChatMessage.newBuilder().setMessage(nickname + ": "
                        + cmd).build().toByteArray());
            } catch (Exception e) {e.printStackTrace();}

        return "";
    }

    public static void main(String[] args) {
        System.out.println("Enter the nickname...");
        try {
            nickname = ioInput.readLine();
        } catch (IOException e) {e.printStackTrace();}

        init();
        while (!exit) {
            try {
                System.out.println(parseCommand(ioInput.readLine()));
            } catch (IOException e) {e.printStackTrace();}
        }
        System.out.println("bb");
        return;
    }
}

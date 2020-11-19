/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package Server.UDP;

import util.Message;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class ServerListeningThread extends Thread {

    private final DatagramSocket listeningSocket;
    private String senderName = "anonymous";
    private final ServerWritingThread writingThread;
    private final InetAddress multicastAddress;
    private final int multicastPort;

    ServerListeningThread(DatagramSocket datagramSocket, ServerWritingThread writingThread, InetAddress multicastAddress, int multicastPort) {
        this.listeningSocket = datagramSocket;
        this.writingThread = writingThread;
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public synchronized void run() {
        try {
            Message message;
            byte[] data;
            ByteArrayInputStream byteArrayInputStream;
            ObjectInputStream objectInputStream;
            ByteArrayOutputStream byteArrayOutputStream;
            ObjectOutputStream objectOutputStream;
            while (true) {
                try {
                    data = new byte[1000];
                    DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
                    listeningSocket.receive(datagramPacket);
                    byteArrayInputStream = new ByteArrayInputStream(data);
                    objectInputStream = new ObjectInputStream(byteArrayInputStream);

                    try {
                        message = (Message) objectInputStream.readObject();
                        System.out.println(message);
                        String content = message.getContent();

                        if(content.equals("[MULTICAST REQUEST]")){
                            Message response = new Message(multicastAddress.getHostAddress() + " " + multicastPort, "[SERVER]");

                            byteArrayOutputStream = new ByteArrayOutputStream();
                            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                            objectOutputStream.writeObject(response);
                            byte[] dataSent = byteArrayOutputStream.toByteArray();

                            listeningSocket.send(new DatagramPacket(dataSent, dataSent.length, datagramPacket.getAddress(), datagramPacket.getPort()));
                        }
                        else {
                            writingThread.addMessage(message);
                        }

                        /*
                        if (content.charAt(0) == '/') {
                            Message response;
                            String[] command = content.split(" ");
                            if (command[0].equals("/rename")) {
                                if (command.length != 2) {
                                    response = new Message("Syntax error. Try /rename <name>.", "[SERVER]");
                                } else {
                                    senderName = command[1];
                                    response = new Message("Your name has been changed to " + senderName + ".", "[SERVER]");
                                }
                            } else if (command[0].equals("/help")) {
                                response = new Message("WRITE HELP MESSAGE HERE", "[SERVER]"); //TODO Write help message
                            } else {
                                response = new Message("Syntax error. Try /help to get help.", "[SERVER]");
                            }
                            writingThread.addMessage(response);
                        } else {
                            writingThread.addMessage(message);
                        }
                        */
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    System.err.println("Error in EchoServer:" + e);
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}

  

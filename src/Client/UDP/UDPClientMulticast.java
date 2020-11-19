package Client.UDP;

import util.Message;

import java.io.*;
import java.net.*;
import java.util.Arrays;

import static java.lang.System.exit;

public class UDPClientMulticast {

    private static MulticastSocket receiveSocket;
    private static DatagramSocket sendSocket;
    private static InetAddress groupAddress;
    private static int groupPort;

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage: java UDPClient <Server host> <Server port>");
            exit(1);
        }

        InetAddress host = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);

        try {
            sendSocket = new DatagramSocket();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            Message message = new Message("[MULTICAST REQUEST]", "anonymous");
            objectOutputStream.writeObject(message);

            byte[] dataSent = byteArrayOutputStream.toByteArray();

            DatagramPacket multicastRequest = new DatagramPacket(dataSent, dataSent.length, host, port);

            sendSocket.send(multicastRequest);

            byte[] dataReceived = new byte[1000];
            DatagramPacket multicastResponse = new DatagramPacket(dataReceived, dataReceived.length);

            sendSocket.receive(multicastResponse);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataReceived);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            try {
                message = (Message) objectInputStream.readObject();
                String[] response = message.getContent().split(" ");
                groupAddress = InetAddress.getByName(response[0]);
                groupPort = Integer.parseInt(response[1]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                exit(1);
            }
        } catch (SocketException e) {
            e.printStackTrace();
            exit(1);
        }

        try {
            receiveSocket = new MulticastSocket(groupPort);
            receiveSocket.joinGroup(groupAddress);
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }
        //System.out.println(multicastSocket);

        ClientWritingThread clientWritingThread = new ClientWritingThread(sendSocket, host, port);
        clientWritingThread.start();
        ClientListeningThread clientListeningThread = new ClientListeningThread(receiveSocket);
        clientListeningThread.start();

        //multicastSocket.leaveGroup(groupAddr);
    }
}
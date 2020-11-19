package Client.UDP;

import util.Message;

import java.io.*;
import java.net.*;

public class ClientWritingThread extends Thread {

    private final DatagramSocket datagramSocket;
    private final InetAddress serverHost;
    private final int serverPort;
    private String name = "anonymous";

    public ClientWritingThread(DatagramSocket datagramSocket, InetAddress serverHost, int serverPort) {
        this.datagramSocket = datagramSocket;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        System.out.println("Writer created");
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public void run() {
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            byte[] data;

            while (true) {
                line = stdIn.readLine();
                if (line.equals(".")) break;
                Message message = new Message(line, name);
                objectOutputStream.writeObject(message);
                data = byteArrayOutputStream.toByteArray();
                DatagramPacket datagramPacket = new DatagramPacket(data, data.length, serverHost, serverPort);
                datagramSocket.send(datagramPacket);
                System.out.println(message);
            }
        } catch (Exception e) {
            System.err.println("Error in ClientWritingThread:" + e);
            e.printStackTrace();
        }
    }
}

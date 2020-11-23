package Client.UDP;

import util.Message;

import java.io.*;
import java.net.*;

/**
 * Class for Writing client-side, is threaded.
 * @see java.lang.Thread
 */
public class ClientWritingThread extends Thread {

    private final DatagramSocket datagramSocket;
    private final InetAddress serverHost;
    private final int serverPort;
    private String name = "anonymous";

    /**
     * Constructor for the client-side writing thread.
     * @param datagramSocket DatagramSocket
     * @param serverHost InetAdress for the server host.
     * @param serverPort int for the server port.
     */
    public ClientWritingThread(DatagramSocket datagramSocket, InetAddress serverHost, int serverPort) {
        this.datagramSocket = datagramSocket;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        System.out.println("Writer created");
    }

    /**
     * Runnable aspect of the thread.
     * Recieves a request from client then sends an echo to the client.
     * @see java.lang.Runnable
     */
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

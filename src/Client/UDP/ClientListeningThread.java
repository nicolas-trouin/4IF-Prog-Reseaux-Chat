package Client.UDP;

import util.Message;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.Socket;

public class ClientListeningThread extends Thread {

    private final MulticastSocket multicastSocket;

    ClientListeningThread(MulticastSocket s) {
        this.multicastSocket = s;
        System.out.println("Listener created");
    }

    /**
     * //TODO
     **/
    public void run() {
        try {
            Message message;
            byte[] data = new byte[1000];
            ByteArrayInputStream byteArrayInputStream;
            ObjectInputStream objectInputStream;

            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
                multicastSocket.receive(datagramPacket);

                byteArrayInputStream = new ByteArrayInputStream(data);
                objectInputStream = new ObjectInputStream(byteArrayInputStream);

                try {
                    message = (Message) objectInputStream.readObject();
                    System.out.println(message);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error in ClientListeningThread:" + e);
            e.printStackTrace();
        }
    }
}

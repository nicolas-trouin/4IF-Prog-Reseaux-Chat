package Server.UDP;

import util.Historique;
import util.Message;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class ServerWritingThread
        extends Thread {

    private final MulticastSocket multicastSocket;
    private final InetAddress multicastAddress;
    private final int multicastPort;
    private Historique historique = new Historique();
    private int newMessages = 0;

    public synchronized void addMessage(Message message){
        this.historique.addMessage(message);
        ++newMessages;
        this.notify();
    }

    private synchronized Message getMessage(){
        while(newMessages == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.historique.getLastMessage(newMessages--);
    }

    ServerWritingThread(MulticastSocket multicastSocket, InetAddress multicastAddress, int multicastPort) {
        this.multicastSocket = multicastSocket;
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public synchronized void run() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            byte[] data;
            while (true) {
                Message message = this.getMessage();
                objectOutputStream.writeObject(message);
                data = byteArrayOutputStream.toByteArray();
                multicastSocket.send(new DatagramPacket(data, data.length, multicastAddress, multicastPort));
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}
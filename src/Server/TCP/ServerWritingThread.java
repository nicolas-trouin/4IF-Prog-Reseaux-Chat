package Server.TCP;

import util.Historique;
import util.Message;

import java.io.*;
import java.net.*;

public class ServerWritingThread
        extends Thread {

    private Socket clientSocket;
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

    ServerWritingThread(Socket s) {
        this.clientSocket = s;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public synchronized void run() {
        try {
            ObjectOutputStream socOut = new ObjectOutputStream(clientSocket.getOutputStream());
            while (true) {
                Message message = this.getMessage();
                socOut.writeObject(message);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}
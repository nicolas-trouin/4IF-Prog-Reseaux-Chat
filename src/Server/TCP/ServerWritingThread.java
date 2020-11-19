package Server.TCP;

import util.Historique;
import util.Message;

import java.io.*;
import java.net.*;
import java.util.List;

public class ServerWritingThread extends Thread {

    private Socket clientSocket;
    private Historique historique;
    private int newMessages = 0;
    private ObjectOutputStream socOut;

    public synchronized void addMessage(Message message) {
        System.out.println(this.historique);
        this.historique.addMessage(message);
        ++newMessages;
        this.notify();
    }

    private synchronized Message getMessage() {
        while (newMessages == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.historique.getLastMessage(newMessages--);
    }

    ServerWritingThread(Socket s, Historique historique) {
        this.clientSocket = s;
        this.historique = new Historique(historique);
        init();
    }

    private void init() {
        List<Message> messages = this.historique.getMessages();
        try {
            this.socOut = new ObjectOutputStream(clientSocket.getOutputStream());
            for (Message message : messages) {
                socOut.writeObject(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void run() {
        try {
            while (true) {
                Message message = this.getMessage();
                socOut.writeObject(message);
            }
        } catch (Exception e) {
            System.err.println("Error in ServerWritingThread:" + e);
            e.printStackTrace();
        }
    }

}
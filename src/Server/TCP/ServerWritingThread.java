package Server.TCP;

import util.History;
import util.Message;

import java.io.*;
import java.net.*;
import java.util.List;

public class ServerWritingThread extends Thread {

    private Socket clientSocket;
    private History history;
    private int newMessages = 0;
    private ObjectOutputStream socOut;

    public synchronized void addMessage(Message message) {
        this.history.addMessage(message);
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
        return this.history.getLastMessage(newMessages--);
    }

    ServerWritingThread(Socket s, History history) {
        this.clientSocket = s;
        this.history = new History(history);
        init();
    }

    private void init() {
        List<Message> messages = this.history.getMessages();
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
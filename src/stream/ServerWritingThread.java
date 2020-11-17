package stream;

import java.io.*;
import java.net.*;

public class ServerWritingThread
        extends Thread {

    private Socket clientSocket;

    ServerWritingThread(Socket s) {
        this.clientSocket = s;
    }

    /**
     * receives a request from client then sends an echo to the client
     **/
    public synchronized void run() {
        try {
            PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                wait();
                Message message = EchoServerMultiThreaded.getHistorique().getLastMessage();
                socOut.println(message.getContent());
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}
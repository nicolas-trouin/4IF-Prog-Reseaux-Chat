package Client.TCP;

import util.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListeningThread extends Thread {

    private Socket serverSocket;

    ClientListeningThread(Socket s) {
        this.serverSocket = s;
    }

    /**
     * //TODO
     *
     **/
    public void run() {
        try {
            ObjectInputStream socIn = new ObjectInputStream (serverSocket.getInputStream());
            while (true) {
                Message message = (Message) socIn.readObject();
//                Message message = new Message(line, serverSocket.getInetAddress().toString());
                System.out.println(message);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}

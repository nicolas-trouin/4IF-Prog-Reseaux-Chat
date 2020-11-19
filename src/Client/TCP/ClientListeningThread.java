package Client.TCP;

import GUI.ChatFrame;
import util.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListeningThread extends Thread {

    private Socket serverSocket;
    private ChatFrame chatFrame;

    ClientListeningThread(Socket s, ChatFrame chatFrame) {
        this.serverSocket = s;
        this.chatFrame = chatFrame;
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
                chatFrame.displayText(message);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}

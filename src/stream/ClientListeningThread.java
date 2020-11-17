package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
            BufferedReader socIn = null;
            socIn = new BufferedReader(
                    new InputStreamReader(serverSocket.getInputStream()));
            while (true) {
                String line = socIn.readLine();
//                Message message = new Message(line, serverSocket.getInetAddress().toString());
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }
}

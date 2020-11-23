package Client.TCP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Class for Writing client-side, is threaded.
 * @see java.lang.Thread
 */
public class ClientWritingThread extends Thread {
    private Socket serverSocket;

    /**
     * Constructor for the class, given a socket.
     * @param s Socket.
     */
    ClientWritingThread(Socket s) {
        this.serverSocket = s;
    }

    /**
     * Runnable aspect of the thread.
     * Recieves a request from client then sends an echo to the client.
     * @see java.lang.Runnable
     */
    public void run() {
        try {
            PrintStream socOut = new PrintStream(serverSocket.getOutputStream());
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while (true) {
                    line = stdIn.readLine();
                    if (line.equals(".")) break;
                    socOut.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error in ClientWritingThread:" + e);
            e.printStackTrace();
        }
    }
}

/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package stream;

import java.io.*;
import java.net.*;

public class ServerListeningThread extends Thread {

    private Socket clientSocket;

    ServerListeningThread(Socket s) {
        this.clientSocket = s;
    }

    /**
     * receives a request from client then sends an echo to the client
     *
	 **/
    public synchronized void run() {
        try {
            BufferedReader socIn = null;
            socIn = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            while (true) {
                String line = socIn.readLine();
                Message message = new Message(line, clientSocket.getInetAddress().toString());
                for (ServerWritingThread serverWritingThread : EchoServerMultiThreaded.getServerWritingThreads()) {
                    serverWritingThread.addMessage(message);
                }
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}

  

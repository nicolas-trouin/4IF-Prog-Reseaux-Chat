/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

package Server.TCP;

import util.Message;

import java.io.*;
import java.net.*;

public class ServerListeningThread extends Thread {

    private final Socket clientSocket;
    private String senderName = "anonymous";
    private final ServerWritingThread writingThread;

    ServerListeningThread(Socket s, ServerWritingThread writingThread) {
        this.clientSocket = s;
        this.writingThread = writingThread;
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
                if(line.charAt(0) == '/'){
                    Message response;
                    String[] command = line.split(" ");
                    if(command[0].equals("/rename")){
                        if(command.length != 2){
                            response = new Message("Syntax error. Try /rename <name>.", "[SERVER]");
                        }
                        else {
                            senderName = command[1];
                            response = new Message("Your name has been changed to " + senderName + ".", "[SERVER]");
                        }
                    }
                    else if(command[0].equals("/help")){
                        response = new Message("WRITE HELP MESSAGE HERE", "[SERVER]"); //TODO Write help message
                    }
                    else {
                        response = new Message("Syntax error. Try /help to get help.", "[SERVER]");
                    }
                    writingThread.addMessage(response);
                }
                else {
                    Message message = new Message(line, senderName);
                    for (ServerWritingThread serverWritingThread : TCPServerMultiThreaded.getServerWritingThreads()) {
                        serverWritingThread.addMessage(message);
                    }
                }
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

}

  

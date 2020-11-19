/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package Server.TCP;

import util.Historique;
import util.Message;

import java.net.*;
import java.util.List;
import java.util.Vector;

public class TCPServerMultiThreaded {

	private static List<ServerWritingThread> serverWritingThreadList = new Vector<>();
	private static Historique historique;

    /**
     * main method
     *
     * @param args port
     **/
    public static synchronized void main(String args[]) {
        ServerSocket listenSocket;
        historique = new Historique();

        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }
        try {
            listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
            System.out.println("Server ready...");
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connexion from:" + clientSocket.getInetAddress());
                ServerWritingThread writingThread = new ServerWritingThread(clientSocket, historique);
                writingThread.start();
                serverWritingThreadList.add(writingThread);
                ServerListeningThread listeningThread = new ServerListeningThread(clientSocket, writingThread);
                listeningThread.start();
            }
        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
        }
    }

    public static List<ServerWritingThread> getServerWritingThreads() {
        return serverWritingThreadList;
    }

    public static void addMessageToHistorique(Message message) {
        historique.addMessage(message);
        //System.out.println(historique.getMessages());
    }
}

  

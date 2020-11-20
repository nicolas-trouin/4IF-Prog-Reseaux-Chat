/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package Server.UDP;

import util.History;

import java.io.IOException;
import java.net.*;

public class UDPServerMultiThreaded {

    //    private static List<ServerWritingThread> serverWritingThreadList = new Vector<>();
    private static History history;

    /**
     * main method
     *
     * @param args port
     **/
    public static synchronized void main(String args[]) {
        history = new History();

        if (args.length != 3) {
            System.out.println("Usage: java UDPServerMultiThreaded <Server port> <Multicast address> <Multicast port>");
            System.exit(1);
        }

        try {
            int serverPort = Integer.parseInt(args[0]);
            InetAddress multicastAddress = InetAddress.getByName(args[1]);
            int multicastPort = Integer.parseInt(args[2]);

            MulticastSocket multicastSocket = new MulticastSocket();
            ServerWritingThread serverWritingThread = new ServerWritingThread(multicastSocket, multicastAddress, multicastPort, history);
            serverWritingThread.start();

            DatagramSocket listeningSocket = new DatagramSocket(serverPort);
            ServerListeningThread serverListeningThread = new ServerListeningThread(listeningSocket,serverWritingThread, multicastAddress, multicastPort);
            serverListeningThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static History getHistorique() {
        return history;
    }
}

  

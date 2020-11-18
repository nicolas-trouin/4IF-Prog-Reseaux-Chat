/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package Server.UDP;

import util.Historique;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Vector;

public class UDPServerMultiThreaded {

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
        // Create a datagram socket associated
        // with the server port
        DatagramSocket serverSocket = null;
        try {
            serverSocket = new DatagramSocket(Integer.parseInt(args[0]));
            System.out.println("Server ready...");
            while (true) {
                System.out.println("Waiting for client packet…");
                byte[] buf = new byte[256];
                // Create a datagram packet
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                // Wait for a packet
                serverSocket.receive(packet);
                // Get client IP address and port number
                InetAddress clientAddr = packet.getAddress();
                int clientPort = packet.getPort();
                // Build a response
                initialize buf ...
                // Build a datagram packet for response
                packet = new DatagramPacket(buf, buf.length, clientAddr, clientPort);
                // Send a response
                serverSocket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static List<ServerWritingThread> getServerWritingThreads () {
            return serverWritingThreadList;
        }

        public static Historique getHistorique () {
            return historique;
        }
    }

  

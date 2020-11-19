/***
 * TCPClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */
package Client.UDP;

import Client.TCP.ClientListeningThread;
import Client.TCP.ClientWritingThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.*;


public class UDPClient {
    /**
     * main method
     * accepts a connection, receives a message from client then sends an echo to the client
     **/
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage: java UDPClient <Server host> <Server port>");
            System.exit(1);
        }

        // Create a datagram socket
        DatagramSocket clientSock = new DatagramSocket();
        byte[] buf = new byte[256];
        // Build a request
//        initialize buf ...
        // Create a datagram packet destined for the
        // server
//        DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, serverPort);
        // Send datagram packet to server
//        clientSock.send(packet);
        // Build a datagram packet for response
//        packet = new DatagramPacket(buf, buf.length);
        // Receive response
//        clientSock.receive(packet);
//        String received = new String(packet.getData(), 0, packet.getLength());
//        System.out.println("Response: " + received);
    }
}



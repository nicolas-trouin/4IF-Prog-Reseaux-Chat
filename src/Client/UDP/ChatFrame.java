package Client.UDP;

import util.Message;

import java.awt.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ChatFrame extends Frame {
    Button validationButton = new Button();
    TextField messageField = new TextField();
    TextArea displayArea = new TextArea();
    DatagramSocket serverSocket;
    private InetAddress serverHost;
    private int serverPort;
    private String name = "anonymous";

    public ChatFrame(DatagramSocket serverSocket, InetAddress serverHost, int serverPort) {
        this.serverSocket = serverSocket;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        initialize();
    }

    public void initialize() {
        this.setResizable(false);
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setBounds(50, 50, 420, 600);
        this.setTitle("Chat GUI");

        validationButton.setLabel("Envoyer");
        validationButton.setBounds(350, 550, 50, 30);

        messageField.setBounds(20, 550, 330, 30);
        messageField.setBackground(Color.white);
        // messageField.setText("Votre message");

        displayArea.setBounds(20, 40, 380, 500);
        displayArea.setEditable(false);

        this.add(validationButton, null);
        this.add(messageField, null);
        this.add(displayArea, null);


        validationButton.addActionListener(
                // lambda-function for action on validation button
                e -> {
                    try {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                        byte[] data;

                        String line = messageField.getText();
                        messageField.setText("");
                        Message message = new Message(line, name);

                        objectOutputStream.writeObject(message);
                        data = byteArrayOutputStream.toByteArray();

                        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, serverHost, serverPort);
                        serverSocket.send(datagramPacket);

                    } catch (Exception exception) {
                        System.err.println("Error in ChatFrame:" + exception);
                        exception.printStackTrace();
                    }
                });
    }

    public void displayText(Message message) {
        displayArea.append(message + "\n");
    }
}

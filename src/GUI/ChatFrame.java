package GUI;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatFrame extends Frame {
    Button validationButton = new Button();
    TextField messageField = new TextField();
    TextArea displayArea = new TextArea();

    public ChatFrame() {
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
                    displayText();
                });


    }

    public void displayText() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("[dd/MM/yyyy] HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String message = messageField.getText();
        displayArea.append("<anonymous> @ " + dtf.format(now) + "\n" + message + "\n\n");
    }
}

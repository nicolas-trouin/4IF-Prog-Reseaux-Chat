package GUI;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainGUI {
    public static void main(String[] args) {
        ChatFrame mainFrame = new ChatFrame();
        mainFrame.setVisible(true);

        mainFrame.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    mainFrame.dispose();
                }
            }
        );
    }
}

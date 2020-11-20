package Server.TCP;

import util.History;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ControlThread extends Thread {
    private History history;

    public ControlThread(History history) {
        this.history = history;
    }

    public synchronized void run() {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while(true) {
            try {
                line = stdin.readLine();
                if(line.equals("quit")) {
                    history.saveToFile("history.ser");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

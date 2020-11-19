package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Historique {

    private List<Message> messages;

    public Historique() {
        this.messages = new ArrayList<>();
    }

    public Historique(Historique historique){
        this.messages = new ArrayList<>(historique.getMessages());
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Message getLastMessage(int nbNewMessages) {
        return messages.get(messages.size()-nbNewMessages);
    }

    public void addMessage(Message message){
        //System.out.println("Appel à Historique.addMessage()");
        this.messages.add(message);
    }
}

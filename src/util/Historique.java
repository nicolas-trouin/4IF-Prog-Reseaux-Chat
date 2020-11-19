package util;

import java.util.List;
import java.util.Vector;

public class Historique {

    private List<Message> messages;

    public Historique() {
        this.messages = new Vector<>();
    }

    public Historique(Historique historique){
        this.messages = historique.messages;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Message getLastMessage(int nbNewMessages) {
        return messages.get(messages.size()-nbNewMessages);
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }
}

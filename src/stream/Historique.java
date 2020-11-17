package stream;

import java.util.List;
import java.util.Vector;

public class Historique {

    private List<Message> messages;

    public Historique() {
        this.messages = new Vector<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Message> getLastMessages() {
        return messages.subList(messages.size()-10,messages.size());
    }

    public Message getLastMessage() {
        return messages.get(messages.size()-1);
    }

    public void addMessage(Message message){
        this.messages.add(message);
    }
}

package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an history of all messages.
 * TODO : serialization
 */
public class History {

    private List<Message> messages;

    /**
     * Default constructor.
     */
    public History() {
        this.messages = new ArrayList<>();
    }

    /**
     * Constructor given an History object.
     * @param history History.
     */
    public History(History history){
        this.messages = new ArrayList<>(history.getMessages());
    }

    /**
     * Getter of a list of messages.
     * @return List of messages of the history.
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Function to get the latest message to be read.
     * @param nbNewMessages number of new messages
     * @return Message.
     */
    public Message getLastMessage(int nbNewMessages) {
        try {
            return messages.get(messages.size()-nbNewMessages);
        }
        catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Function to add a message to history.
      * @param message Message to be added.
     */
    public void addMessage(Message message) {
        this.messages.add(message);
    }
}

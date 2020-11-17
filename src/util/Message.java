package util;

import java.util.Date;

public class Message{

    private String content;
    private String sender;
    private Date date;

    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
        this.date = new Date();
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "[" + date + "] " + sender + "> " + content;
    }
}

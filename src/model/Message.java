package model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private long id;
    private Date date;
    private Addressee addressee;
    private Client sender;
    private String body;

    public Message(Date date, Addressee addressee, Client sender, String body) {
        this.date = date;
        this.addressee = addressee;
        this.sender = sender;
        this.body = body;
    }

    public void setAddressee(Addressee addressee) {
        this.addressee = addressee;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Addressee getAddressee() {
        return addressee;
    }

    public Client getSender() {
        return sender;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", date=" + date +
                ", addressee=" + addressee +
                ", sender=" + sender +
                ", body='" + body + '\'' +
                '}';
    }
}

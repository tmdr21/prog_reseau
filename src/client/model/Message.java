package client.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private long id;
    private Date date;
    private Client addressee;
    private Client sender;
    private String body;

    public Message(Date date, Client addressee, Client sender, String body) {
        this.date = date;
        this.addressee = addressee;
        this.sender = sender;
        this.body = body;
    }

    public void setAddressee(Client addressee) {
        this.addressee = addressee;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Client getAddressee() {
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

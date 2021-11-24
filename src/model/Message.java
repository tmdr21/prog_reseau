package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Message")
public class Message implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "addressee_id_addressee")
    private Addressee addressee;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id_addressee")
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

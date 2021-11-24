package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Class that represents all messages exchanged. We sent objects through our output streams
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
@Entity
public class Message implements Serializable {

    /**
     * Id for our database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Date in which the message has been sent
     */
    private Date date;

    /**
     * Addressee of this message
     */
    @ManyToOne
    private Addressee addressee;

    /**
     * Sender of this message
     */
    @ManyToOne(cascade = CascadeType.ALL)
    private Client sender;

    /**
     * Text body of this message
     */
    private String body;

    /**
     * Default constructor
     */
    public Message(){}

    /**
     * Creates a new message
     * @param date Date in which the message has been sent
     * @param addressee Addressee of this message
     * @param sender Sender of this message
     * @param body Text body of this message
     */
    public Message(Date date, Addressee addressee, Client sender, String body) {
        this.date = date;
        this.addressee = addressee;
        this.sender = sender;
        this.body = body;
    }

    /**
     * Sets a new addressee for this message
     * @param addressee new addressee to be set
     */
    public void setAddressee(Addressee addressee) {
        this.addressee = addressee;
    }

    /**
     * Gets the date of this message
     * @return date of the message
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the addressee of this message
     * @return addressee of this message
     */
    public Addressee getAddressee() {
        return addressee;
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

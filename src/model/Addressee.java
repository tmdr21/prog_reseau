package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Abstract class that represents an addressee, which can be a client or a group chat
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Addressee implements Serializable {

    /**
     * Id for the database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAddressee;
    /**
     * Name of the addressee
     */
    private String name;

    /**
     * Default constructor
     */
    public Addressee(){}

    /**
     * Constructs a new instance of an addressee
     * @param name name of the addressee
     */
    public Addressee(String name){
        this.name=name;
    }

    /**
     * Gets the name of the addressee
     * @return the name of the addressee
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a message to the received list of the addressee
     * @param message message to be added
     */
    public abstract void addReceivedMessage(Message message);

    @Override
    public String toString() {
        return "Addressee{" +
                "idAddressee=" + idAddressee +
                ", name='" + name + '\'' +
                '}';
    }
}

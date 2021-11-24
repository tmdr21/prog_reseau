package model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents a group chat, in which there can be several clients
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
@Entity
public class GroupChat extends Addressee{

    /**
     * List of all clients belonging to this group chat
     */
    @ManyToMany
    private List<Client> listOfMemebers;

    /**
     * List of all messages exchanged in this group chat
     */
    @OneToMany(mappedBy = "addressee")
    private List<Message> messagesExchangedList = new LinkedList<>();

    /**
     * Default constructor
     */
    public GroupChat(){}

    /**
     * Creates a new instance of a group chat with a given name
     * @param name name of the new group chat
     */
    public GroupChat(String name) {
        super(name);
    }

    @Override
    public void addReceivedMessage(Message message) {
        this.messagesExchangedList.add(message);
    }

    /**
     * Adds a new client to the group chat
     * @param client client to be added
     */
    public void addClient(Client client){
        listOfMemebers.add(client);
    }

}

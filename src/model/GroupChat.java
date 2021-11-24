package model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Entity
/**
 * Class that represents a group chat, in which there can be several clients
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
public class GroupChat extends Addressee{
    @ManyToMany
    /**
     * List of all clients belonging to this group chat
     */
    private List<Client> listOfMemebers;
    @OneToMany(mappedBy = "addressee")
    /**
     * List of all messages exchanged in this group chat
     */
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

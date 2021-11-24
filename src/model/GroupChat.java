package model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Entity
public class GroupChat extends Addressee{
    @ManyToMany
    private List<Client> listOfMemebers;
    @OneToMany(mappedBy = "addressee")
    private List<Message> messagesExchangedList = new LinkedList<>();

    public GroupChat(long idAddressee, String name) {
        super(idAddressee, name);
    }

    public GroupChat(String name) {
        super(name);
    }

    @Override
    public void addReceivedMessage(Message message) {
        this.messagesExchangedList.add(message);
    }

    public void addClient(Client client){
        listOfMemebers.add(client);
    }

}

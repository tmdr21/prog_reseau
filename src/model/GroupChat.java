package model;

import java.util.LinkedList;
import java.util.List;

public class GroupChat extends Addressee{
    private List<Client> listOfMemebers;
    private List<Message> messagesExchangedList = new LinkedList<>();

    public GroupChat(long idAddressee, String name) {
        super(idAddressee, name);
    }

    public GroupChat(String name) {
        super(name);
    }

    public void addClient(Client client){
        listOfMemebers.add(client);
    }
}

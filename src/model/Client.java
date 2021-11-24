package model;

import javax.persistence.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

@Entity
public class Client extends Addressee{
    @OneToMany(mappedBy = "sender")
    private List<Message> messagesSentList;
    @OneToMany(mappedBy = "addressee")
    private List<Message> messagesReceivedList;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<GroupChat> groups;
    @Transient
    private transient Socket socket;
    @Transient
    private transient ObjectOutputStream objectOutputStream;
    @Transient
    private transient ObjectInputStream objectInputStream;

    public Client(){

    }
    public Client(long idClient, String name) {
        super(idClient, name);
    }

    public Client(String name) {
        super(name);
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public void createInputStream(){
        if(socket != null) {
            try {
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                System.out.println("Error in client: " + e);
            }
        }
    }

    public void createOutputStream(){
        if(socket != null) {
            try {
                this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            } catch (Exception e) {
                System.out.println("Error in client: " + e);
            }
        }
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

}

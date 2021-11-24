package model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

@Entity
/**
 * Class that represents a client
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
public class Client extends Addressee{
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    /**
     * List of all messages sent
     */
    private List<Message> messagesSentList = new LinkedList<>();

    @OneToMany(mappedBy = "addressee", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    /**
     * List of all messages received
     */
    private List<Message> messagesReceivedList = new LinkedList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    /**
     * List of all the group chat to which this client belongs
     */
    private List<GroupChat> groups = new LinkedList<>();

    @Transient
    /**
     * Socket to communicate with this client or to communicate with the server
     */
    private transient Socket socket;
    @Transient
    /**
     * Output stream to send messages to the server or to this client
     */
    private transient ObjectOutputStream objectOutputStream;
    @Transient
    /**
     * Input stream so this client can receive messages from the server
     */
    private transient ObjectInputStream objectInputStream;

    /**
     * Default constructor
     */
    public Client(){

    }

    /**
     * Constructs an instance of a client with a name
     * @param name name of the client
     */
    public Client(String name) {
        super(name);
    }

    /**
     * Sets a socket for the client
     * @param socket new value of socket
     */
    public void setSocket(Socket socket){
        this.socket = socket;
    }

    /**
     * Creates a input stream from it's socket
     */
    public void createInputStream(){
        if(socket != null) {
            try {
                this.objectInputStream = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                System.out.println("Error in client: " + e);
            }
        }
    }

    /**
     * Creates an output stream from it's socket
     */
    public void createOutputStream(){
        if(socket != null) {
            try {
                this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            } catch (Exception e) {
                System.out.println("Error in client: " + e);
            }
        }
    }

    /**
     * Sets the output stream of this client
     * @param objectOutputStream new output stream
     */
    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }
    /**
     * Sets the input stream of this client
     * @param objectInputStream new input stream
     */
    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    /**
     * gets the output stream of this client
     * @return output stream of this client
     */
    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    /**
     * gets the input stream of this client
     * @return input stream of this client
     */
    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }
    @Override
    public void addReceivedMessage(Message message){
        this.messagesReceivedList.add(message);
    }

    /**
     * Adds a new messeage to the sent messages list
     * @param message new message to be added
     */
    public void addSentMessage(Message message){
        this.messagesReceivedList.add(message);
    }

    /**
     * gets the list of all messages sent
     * @return the list of all messages sent
     */
    public List<Message> getMessagesSentList() {
        return messagesSentList;
    }

    /**
     * gets the list of all messages received
     * @return the list of all messages received
     */
    public List<Message> getMessagesReceivedList() {
        return messagesReceivedList;
    }
}

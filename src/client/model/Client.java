package client.model;

import java.beans.Transient;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

public class Client implements Serializable {
    private long idClient;
    private String name;
    private List<Message> messagesSentList;
    private List<Message> messagesReceivedList;
    private transient Socket socket;
    private transient ObjectOutputStream objectOutputStream;
    private transient ObjectInputStream objectInputStream;

    public Client(){

    }
    public Client(long idClient, String name) {
        this.idClient = idClient;
        this.name = name;
    }

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", name='" + name + '\'' +
                '}';
    }
}

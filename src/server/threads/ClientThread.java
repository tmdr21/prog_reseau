package server.threads;

import model.Addressee;
import model.Client;
import model.GroupChat;
import model.Message;
import server.controller.ServerController;
import service.Service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.Date;
import java.util.List;

public class ClientThread extends Thread{
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Client client;
    private ServerController server;
    public ClientThread(Client client, ServerController server) {
        this.server = server;
        this.client = client;
    }

    public void run() {
        try {
            objectInputStream = client.getObjectInputStream();
            objectOutputStream = client.getObjectOutputStream();
            while (true) {
                Message message = (Message) objectInputStream.readObject();
                if(message.getAddressee().getName().equals("broadcast")){
                    List<Client> allClients = server.getConnectedClients();
                    for(Client c : allClients){
                        objectOutputStream = c.getObjectOutputStream();
                        objectOutputStream.writeObject(message);
                    }
                }else {
                    Addressee addressee = Service.findAddressee(message.getAddressee().getName());
                    message.setAddressee(addressee);
                    Service.messageReceived(addressee, message);
                    Service.messageSent(client, message);
                    if(!isOnline(addressee)){
                        objectOutputStream = client.getObjectOutputStream();
                        message = new Message(new Date(), client, null, "This user is not online");
                    }else{
                        if(addressee instanceof Client) {
                            System.out.println(message);
                            objectOutputStream.writeObject(message);
                        }else if(addressee instanceof GroupChat){
                            //Broadcast message to all group members
                        }
                    }
                }
            }
        } catch (SocketException se) {
            System.out.println(client.getName() + " has left the chat");
            server.logOut(client);
            stop();
        } catch (Exception e){
            System.err.println("Error in Client thread:" + e);
        }
    }

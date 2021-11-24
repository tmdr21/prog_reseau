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
import java.util.HashMap;
import java.util.List;

/**
 * Thread that handles all the requests incoming from a given client. To be run by the server
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
public class ClientThread extends Thread {
    /**
     * Input stream in which the server listens to messages sent by a given client
     */
    private ObjectInputStream objectInputStream;
    /**
     * Output stream in which the server needs to write a given message
     */
    private ObjectOutputStream objectOutputStream;
    /**
     * Client handled by this thread, all messages here comme from this client
     */
    private Client client;
    /**
     * Server controller that created and run this thread
     */
    private ServerController server;

    /**
     * Constructor
     * @param client client that needs to be handeled and listened
     * @param server Server controller that created and run this thread
     */
    public ClientThread(Client client, ServerController server) {
        this.server = server;
        this.client = client;
    }
    @Override
    public void run() {
        try {
            objectInputStream = client.getObjectInputStream();
            objectOutputStream = client.getObjectOutputStream();
            while (true) {
                Message message = (Message) objectInputStream.readObject();
                if (message.getAddressee().getName().equals("broadcast")) {
                    HashMap<String, Client> allClients = server.getConnectedClientsMap();
                    for (Client c : allClients.values()) {
                        objectOutputStream = c.getObjectOutputStream();
                        objectOutputStream.writeObject(message);
                    }
                } else {
                    Addressee addressee = server.getConnectedClientByName(message.getAddressee().getName());
                    message.setAddressee(addressee);
                    if (addressee==null) {
                        objectOutputStream = client.getObjectOutputStream();
                        message = new Message(new Date(), client, null, "This user is not online");
                        objectOutputStream.writeObject(message);
                    }else{
                        //Service.createMessage(message);
                        Service.messageReceived(message);
                        //Service.messageSent(client, message);
                        if(addressee instanceof Client) {
                            objectOutputStream = ((Client) addressee).getObjectOutputStream();
                            System.out.println(message);
                            objectOutputStream.writeObject(message);
                        }else if(addressee instanceof GroupChat){
                            //Message to be sent to all group members
                            //Not yet implemented
                        }
                    }
                }
            }
        } catch (SocketException se) {
            System.out.println(client.getName() + " has left the chat");
            server.logOut(client);
            stop();
        } catch (Exception e) {
            System.err.println("Error in Client thread:" + e);
        }
    }

}

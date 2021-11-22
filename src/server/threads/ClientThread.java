package server.threads;

import model.Client;
import model.Message;
import server.controller.ServerController;

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
                    try {
                        findAddressee(message);
                    } catch (Exception e) {
                        objectOutputStream = client.getObjectOutputStream();
                        message = new Message(new Date(), client, null, "This user is not online");
                    }
                    System.out.println(message);
                    objectOutputStream.writeObject(message);
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

    private void findAddressee(Message message) throws Exception{
        Client addressee = null;
        try {
            addressee = server.getClientByName(message.getAddressee().getName());
            message.setAddressee(addressee);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(addressee != null){
            try {
                objectOutputStream = addressee.getObjectOutputStream();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            throw new Exception();
        }
    }
}

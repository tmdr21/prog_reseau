package client.threads;

import model.Client;
import model.Message;

import java.io.ObjectInputStream;

/**
 * Thread in charge of receiving messages for each client
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
public class ClientReceiveThread extends Thread{
    /**
     * Client that is running this thread
     */
    private Client client;
    /**
     * Stream in charge to write the messages
     */
    private ObjectInputStream objectInputStream;

    /**
     * Thread constructor
     * @param client Client that is running this thread
     */
    public ClientReceiveThread(Client client){
        this.client = client;
    }
    @Override
    public void run(){
        try {
            objectInputStream = client.getObjectInputStream();
            while (true) {
                Message message = (Message) objectInputStream.readObject();
                client.addReceivedMessage(message);
                System.out.println("\n" + message);
            }
        } catch (Exception e) {
            System.err.println("Error in Client receive:" + e);
        }
    }
}

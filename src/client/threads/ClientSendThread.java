package client.threads;

import model.Client;
import model.Message;

import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Scanner;

/**
 * Thread used to send messages for the client running this thread
 */
public class ClientSendThread extends Thread{
    /**
     * Client that is running this thread
     */
    private Client client;
    /**
     * Stream used to send the messages
     */
    private ObjectOutputStream objectOutputStream;

    /**
     * Constructor of this thread
     * @param client Client running this thread
     */
    public ClientSendThread(Client client){
        this.client = client;
    }
    @Override
    public void run(){
        Scanner sc=new Scanner(System.in);
        try {
            objectOutputStream = client.getObjectOutputStream();
            while (true) {
                System.out.print("Message to: ");
                String addressee = sc.nextLine();
                System.out.print("message body: ");
                String body = sc.nextLine();
                Message message = new Message(new Date(), new Client(addressee), client, body);
                objectOutputStream.writeObject(message);
            }
        } catch (Exception e) {
            System.err.println("Error in client send:" + e);
        }
    }
}

package client.threads;

import model.Client;
import model.Message;

import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Scanner;

public class ClientSendThread extends Thread{
    private Client client;
    private ObjectOutputStream objectOutputStream;

    public ClientSendThread(Client client){
        this.client = client;
    }

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

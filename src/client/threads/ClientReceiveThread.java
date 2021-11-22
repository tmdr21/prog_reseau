package client.threads;

import model.Client;
import model.Message;

import java.io.ObjectInputStream;

public class ClientReceiveThread extends Thread{
    private Client client;
    private ObjectInputStream objectInputStream;

    public ClientReceiveThread(Client client){
        this.client = client;
    }

    public void run(){
        try {
            objectInputStream = client.getObjectInputStream();
            while (true) {
                Message message = (Message) objectInputStream.readObject();
                System.out.println("\n" + message);
            }
        } catch (Exception e) {
            System.err.println("Error in Client receive:" + e);
        }
    }
}

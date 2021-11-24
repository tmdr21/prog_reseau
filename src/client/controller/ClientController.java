package client.controller;

import model.Client;
import client.threads.ClientReceiveThread;
import client.threads.ClientSendThread;
import server.controller.ServerControllerInterface;

import java.net.Socket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Controller for the client
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
public class ClientController {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        if(args.length != 2){
            System.out.println("Error launching the client. Usage [host] [port]");
            System.exit(1);
        }
        System.out.println("Client launching...");
        System.out.println("Please enter your nickname");
        String name = sc.nextLine();
        String host = args[0];
        String portToSocket = args[1];
        Client client = new Client();
        System.out.println("Welcome " + name);
        //Retrieve the client from the server
        try {
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            ServerControllerInterface stub = (ServerControllerInterface) registry.lookup("loginClientByName");
            client = stub.loginClientByName(name);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

        //Create the connection with the server
        Socket socket;
        try {
            socket = new Socket(host, Integer.parseInt(portToSocket));
            client.setSocket(socket);
            client.createOutputStream();
            client.createInputStream();
            client.getObjectOutputStream().writeObject(client);
        }catch (Exception e){
            e.printStackTrace();
        }
        ClientReceiveThread crt = new ClientReceiveThread(client);
        ClientSendThread cst = new ClientSendThread(client);
        crt.start();
        cst.start();
    }

}

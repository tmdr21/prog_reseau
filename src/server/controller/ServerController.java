package server.controller;

import model.Client;
import server.threads.ClientThread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ServerController implements ServerControllerInterface{
    private static HashMap<String, Client> clientHashMap;
    private static ServerSocket listenSocket;
    private static ServerController server = null;
    private static ServerControllerInterface stub = null;
    private static ObjectInputStream objectInputStream;
    private static List<Client> connectedClients = new LinkedList<>();
    public ServerController(){
        clientHashMap = new HashMap<>();
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }
        server = new ServerController();

        //Get the RMI ready
        try {
            stub = (ServerControllerInterface) UnicastRemoteObject.exportObject(server, 1099);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("loginClientByName", stub);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("hello");

        try {
            listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
            System.out.println("Server ready...");
            while (true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Connexion from:" + clientSocket.getInetAddress());

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                Client client = (Client) objectInputStream.readObject();
                System.out.println(client.getName() + " just joined the chat");
                client.setSocket(clientSocket);
                client.setObjectInputStream(objectInputStream);
                client.setObjectOutputStream(objectOutputStream);

                connectClient(client);
                ClientThread ct = new ClientThread(client, server);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in server controller:" + e);
        }
    }

    @Override
    public Client loginClientByName(String name) throws RemoteException {
        Client client = clientHashMap.get(name);
        if(client == null){
            //Create a new client
            client = new Client(100, name);
        }
        return client;
    }

    public static void connectClient(Client client){
        clientHashMap.put(client.getName(), client);
        connectedClients.add(client);
    }

    public Client getClientByName(String name){
        return clientHashMap.get(name);
    }

    public static List<Client> getConnectedClients() {
        return connectedClients;
    }

    public void logOut(Client client){
        connectedClients.remove(client);
    }
}

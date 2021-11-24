package server.controller;

import dao.DAO;
import dao.JpaUtil;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the server
 */
public class ServerController implements ServerControllerInterface{
    private static HashMap<String, Client> connectedClientsMap;
    private static ServerSocket listenSocket;
    private static ServerController server = null;
    private static ServerControllerInterface stub = null;
    private static ObjectInputStream objectInputStream;
    public ServerController(){
        connectedClientsMap = new HashMap<>();
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }
        server = new ServerController();
        JpaUtil.init();
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

                ClientThread ct = new ClientThread(client, server);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in server controller:" + e);
        }
        JpaUtil.destroy();
    }

    @Override
    public Client loginClientByName(String name) throws RemoteException {
        Client client ;
        JpaUtil.creerContextePersistance();
        try{
            JpaUtil.ouvrirTransaction();
            client = DAO.searchClientByName(name);
            JpaUtil.validerTransaction();
        }catch (Exception ex){
            JpaUtil.annulerTransaction();
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception at find client in service", ex);
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        if(client == null){
            client = new Client(name);
            try {
                JpaUtil.ouvrirTransaction();
                DAO.createClient(client);
                JpaUtil.validerTransaction();
            }catch (Exception e){
                JpaUtil.annulerTransaction();
                Logger.getAnonymousLogger().log(Level.WARNING, "Exception at create client in service", e);
            }finally {
                JpaUtil.fermerContextePersistance();
            }
        }
        connectedClientsMap.put(client.getName(), client);
        return client;
    }

    public Client getConnectedClientByName(String name){
        return connectedClientsMap.get(name);
    }

    public static HashMap<String, Client> getConnectedClientsMap() {
        return connectedClientsMap;
    }

    public void logOut(Client client){
        connectedClientsMap.remove(client.getName());
    }
}

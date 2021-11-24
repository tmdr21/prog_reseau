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
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
public class ServerController implements ServerControllerInterface{
    /**
     * Map with all connected clients, we use a map so the searching in this data structure will be in O(1)
     */
    private static HashMap<String, Client> connectedClientsMap;
    /**
     * Socket in which the server will be listening for new connections
     */
    private static ServerSocket listenSocket;
    /**
     * Instance of this server controller
     */
    private static ServerController server = null;
    /**
     * Stub for setting up the RMI
     */
    private static ServerControllerInterface stub = null;
    /**
     * Stream in which the server will be listening
     */
    private static ObjectInputStream objectInputStream;

    /**
     * Constructor of the server controller
     */
    public ServerController(){
        connectedClientsMap = new HashMap<>();
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
        }
        server = new ServerController();
        try {
            server.loginClientByName("loo");
        }catch (Exception e){}
        JpaUtil.init();
        //Get the RMI ready-----------------------------------------------------------------------------
        try {
            stub = (ServerControllerInterface) UnicastRemoteObject.exportObject(server, 1099);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("loginClientByName", stub);
        }catch (Exception e){
            e.printStackTrace();
        }
        //-----------------------------------------------------------------------------------------------
        //Accepts a new incoming connection--------------------------------------------------------------
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
                addLoggedInClient(client);

                ClientThread ct = new ClientThread(client, server);
                ct.start();
            }
        } catch (Exception e) {
            System.err.println("Error in server controller:" + e);
        }
        //-------------------------------------------------------------------------------------------------
        JpaUtil.destroy();
    }

    @Override
    public Client loginClientByName(String name) throws RemoteException {
        Client client ;
        JpaUtil.creerContextePersistance();
        client = DAO.searchClientByName(name);
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
        return client;
    }

    /**
     * Finds a connected client by it's name
     * @param name name of the client to be searched
     * @return client that matches this name
     */
    public Client getConnectedClientByName(String name){
        return connectedClientsMap.get(name);
    }

    /**
     * Adds a new client to the logged clients map
     * @param client client to be added
     */
    public static void addLoggedInClient(Client client){
        connectedClientsMap.put(client.getName(), client);
    }

    /**
     * Gets a map of all connected clients, used for broadcasting purposes
     * @return
     */
    public static HashMap<String, Client> getConnectedClientsMap() {
        return connectedClientsMap;
    }

    /**
     * Takes out a client from the connected clients map when this one leaves the server
     * @param client client that left the server
     */
    public void logOut(Client client){
        connectedClientsMap.remove(client.getName());
    }
}

package server.controller;

import model.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Controller Interface to send for the RMI
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
public interface ServerControllerInterface extends Remote {
    /**
     * Searches for a client in the database, if such client exists it returns this client
     * else it will create a new client
     * @param name Name of the client to be logged in
     * @return Client in the database or new client
     * @throws RemoteException
     */
    public Client loginClientByName(String name) throws RemoteException;
}

package server.controller;

import model.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerControllerInterface extends Remote {
    public Client loginClientByName(String name) throws RemoteException;
}

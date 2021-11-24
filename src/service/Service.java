package service;

import dao.JpaUtil;
import dao.DAO;
import model.Addressee;
import model.Client;
import model.Message;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Service {

    public static Addressee findAddressee(String name){
        JpaUtil.creerContextePersistance();
        Addressee add ;
        try{
            JpaUtil.ouvrirTransaction();
            add = DAO.searchAddresseeByName(name);
            JpaUtil.validerTransaction();
        }catch (Exception ex){
            JpaUtil.annulerTransaction();
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception at find addressee in service", ex);
            add = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return add;
    }

    public static Client messageSent(Client client, Message message){
        client.addSentMessage(message);
        JpaUtil.creerContextePersistance();
        Client result = client;
        try {
            JpaUtil.ouvrirTransaction();
            DAO.modify(client);

            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception at message received in service", ex);
            result = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return result;
    }

    public static Addressee messageReceived (Addressee addressee, Message message){
        addressee.addReceivedMessage(message);
        JpaUtil.creerContextePersistance();
        Addressee result = addressee;
        try {
            JpaUtil.ouvrirTransaction();
            DAO.modify(addressee);

            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            JpaUtil.annulerTransaction();
            Logger.getAnonymousLogger().log(Level.WARNING, "Exception at message received in service)", ex);
            result = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return result;

    }
}

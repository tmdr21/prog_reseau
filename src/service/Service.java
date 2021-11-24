package service;

import dao.JpaUtil;
import dao.DAO;
import model.Addressee;
import model.Message;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides general services to all classes so they can interact with the database
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
public class Service {
    /**
     * Adds a new received message into the database and links it to this addressee
     * @param message message to be linked to it's addressee
     * @return the modified addressee
     */
    public static Addressee messageReceived (Message message){
        message.getAddressee().addReceivedMessage(message);
        JpaUtil.creerContextePersistance();
        Addressee result = message.getAddressee();
        try {
            JpaUtil.ouvrirTransaction();
            DAO.modify(message.getAddressee());
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

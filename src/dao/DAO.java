package dao;

import model.Addressee;
import model.Client;
import model.Message;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * This class takes care of interacting with JPAUtil, the class that takes care about persisting the objects
 * @author Ithan Velarde, Taha Mdarhri, Aichetou M'Bareck
 */
public class DAO {
    /**
     * Modifies a addressee already present in the database
     * @param client existing client with some field modified and ready to be persisted
     * @return the modified and persisted client
     */
    public static Addressee modify(Addressee client){
        return JpaUtil.obtenirContextePersistance().merge(client);
    }

    /**
     * Persists a message
     * @param message message to be persisted
     */
    public static void createMessage(Message message){
        JpaUtil.obtenirContextePersistance().persist(message);
    }

    /**
     * Persists a client
     * @param client client to be persisted
     */
    public static void createClient(Client client){
        JpaUtil.obtenirContextePersistance().persist(client);
    }

    /**
     * Searches a client by it's name in the database
     * @param name name of the client to be found
     * @return The client that matches this name
     */
    public static Client searchClientByName(String name){
        EntityManager em = JpaUtil.obtenirContextePersistance();
        String jpql="select c from Client c where c.name= :name ";
        TypedQuery query=em.createQuery(jpql, Client.class);
        query.setParameter("name",name);
        List<Client> Users = query.getResultList(); //getsingleresult
        Client result = null;
        if (!Users.isEmpty()) {
            result = Users.get(0); // premier de la liste
        }
        return result;
    }
}

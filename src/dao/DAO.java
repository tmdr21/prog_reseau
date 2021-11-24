package dao;

import model.Addressee;
import model.Client;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DAO {

    public static Addressee modify(Addressee client){
        return JpaUtil.obtenirContextePersistance().merge(client);
    }

    public static void createClient(Client client){
        JpaUtil.obtenirContextePersistance().persist(client);
    }

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

    public static Addressee searchAddresseeByName(String name){

    }
}

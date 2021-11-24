package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JpaManager {
    /*private static final EntityManagerFactory emFactoryObj;
    private static final String PERSISTENCE_UNIT_NAME = "Projet";//"JPAClientsChat";

    static {
        emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
    /*Map<String, String> properties = new HashMap<String, String>();
    properties.put("javax.persistence.jdbc.user", "admin");
    properties.put("javax.persistence.jdbc.password", "admin");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(
            "objectdb://localhost:6136/myDbFile.odb", properties);*



    // This Method Is Used To Retrieve The 'EntityManager' Object
    public static EntityManager getEntityManager() {
        return emFactoryObj.createEntityManager();
    }

    public static void main(String[] args) {

        EntityManager entityMgr = getEntityManager();
        entityMgr.getTransaction().begin();

        Addressee a = new Addressee();
       /* a.setId(101);
        farmObj.setName("Harry Potter");
        farmObj.setVillage("Scottish Highlands");
        entityMgr.persist(farmObj);*

        entityMgr.getTransaction().commit();

        entityMgr.clear();
        System.out.println("Record Successfully Inserted In The Database");
    }*/

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Projet");

        EntityManager em = emf.createEntityManager();

    }

}

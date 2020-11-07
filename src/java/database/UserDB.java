package database;

import models.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import models.Users;

public class UserDB {

    public int insert(Users user) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();  
        EntityTransaction et = em.getTransaction();
        
        try {
            et.begin();
            em.persist(user);
            et.commit();
        }catch(Exception ex){
            et.rollback();
            throw new NotesDBException("Error in inserting user");
        } finally {
            em.close();
            return 1;
        } 
    }

    public int update(Users user) throws NotesDBException {
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(user);
            et.commit();
        } catch (Exception ex) {
            et.rollback();
            throw new NotesDBException("Error in updating user");
        } finally {
            em.close();
        }
        
        return 1;
    }

    public ArrayList<Users> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
         List<Users> list = em.createNamedQuery("Users.findAll").getResultList();
        
         
         ArrayList<Users> array = new ArrayList<>();
         
         for(int i = 0; i < list.size(); i++) {
             array.add(list.get(i));
         }
         
        return array;
      
    }

    /**
     * Get a single user by their username.
     *
     * @param username The unique username.
     * @return A User object if found, null otherwise.
     * @throws NotesDBException
     */
    public Users getUser(String username) throws NotesDBException {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();    
       try {
            Users user = em.find(Users.class, username);
            return user;
        } finally {
           em.close();
        } 
    }

    public int delete(Users user) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            em.remove(em.merge(user));
            et.commit();
        } catch (Exception ex) {
           et.rollback();
        } finally {
           em.close();
           return 1;
        }
    }
}

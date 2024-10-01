package org.example.DevSync1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.DevSync1.entity.User;

import java.util.List;

public class UserRepository {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public void save(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public List<User> findAll() {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            List<User> users = em.createQuery("SELECT c FROM User c ORDER BY c.id DESC", User.class).getResultList();
            em.close();
            return users;
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
        em.close();
    }


    public void update(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
    }

    public User findById(Long id) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, id);
        em.close();
        return user;
    }


}

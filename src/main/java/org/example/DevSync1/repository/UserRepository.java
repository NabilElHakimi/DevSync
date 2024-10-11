package org.example.DevSync1.repository;

import jakarta.persistence.EntityManager;
import org.example.DevSync1.config.Config;
import org.example.DevSync1.entity.User;

import java.util.List;

public class UserRepository {

    public boolean save(User user) {
        EntityManager em = Config.getEntityManager();
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public List<User> findAll() {
        EntityManager em = Config.getEntityManager();
        em.getTransaction().begin();
        List<User> users = em.createQuery("SELECT u FROM User u ORDER BY u.id DESC", User.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return users;
    }

    public boolean delete(Long id) {
        EntityManager em = Config.getEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean update(User user) {
        EntityManager em = Config.getEntityManager();
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public User findById(Long id) {
        EntityManager em = Config.getEntityManager();
        User user = em.find(User.class, id);
        em.close();
        return user;
    }
}

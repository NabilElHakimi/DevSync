package org.example.DevSync1.repository;

import jakarta.persistence.EntityManager;
import org.example.DevSync1.config.Config;
import org.example.DevSync1.entity.Tag;

import java.util.List;

public class TagRepository {

    private EntityManager getEntityManager() {
        return Config.getEntityManager();
    }

    public boolean save(Tag tag) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(tag);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean update(Tag tag) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(tag);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean delete(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Tag tag = em.find(Tag.class, id);
        if (tag != null) {
            em.remove(tag);
        }
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public List<Tag> findAll() {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        List<Tag> tags = em.createQuery("SELECT t FROM Tag t ORDER BY t.id DESC", Tag.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return tags;
    }

    public Tag findById(Long id) {
        EntityManager em = getEntityManager();
        Tag tag = em.find(Tag.class, id);
        em.close();
        return tag;
    }
}

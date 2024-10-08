package org.example.DevSync1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.DevSync1.entity.Tag;
import org.example.DevSync1.entity.Task;

import java.util.List;

public class TaskRepository {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");


    public boolean save(Task task) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(task);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean update(Task task) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(task);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean delete(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Task task = em.find(Task.class, id);
        if (task != null) {
            em.remove(task);
        }
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public List<Task> findAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Task> tasks = em.createQuery("SELECT c FROM Task c ORDER BY c.id DESC", Task.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return tasks;
    }

    public Task findById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Task task = em.find(Task.class, id);
        em.getTransaction().commit();
        em.close();
        return task;
    }


    public Task gitTaskWithTags(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Task task = em.createQuery("SELECT c FROM Task c JOIN FETCH c.tags WHERE c.id = :id", Task.class)
                .setParameter("id", id)
                .getSingleResult();
        em.getTransaction().commit();
        em.close();
        return task;
    }




}
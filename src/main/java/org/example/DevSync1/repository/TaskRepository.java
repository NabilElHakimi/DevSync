package org.example.DevSync1.repository;

import jakarta.persistence.EntityManager;
import org.example.DevSync1.config.Config;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.enums.Status;

import java.util.List;

public class TaskRepository {

    private EntityManager getEntityManager() {
        return Config.getEntityManager();
    }

    public boolean save(Task task) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        if (task.getCreatedBy() != null) {
            task.setCreatedBy(em.merge(task.getCreatedBy()));
        }

        em.persist(task);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean update(Task task) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        if (task.getCreatedBy() != null) {
            task.setCreatedBy(em.merge(task.getCreatedBy()));
        }

        em.merge(task);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean delete(Long id) {
        EntityManager em = getEntityManager();
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
        EntityManager em = getEntityManager();
        List<Task> tasks = em.createQuery("SELECT t FROM Task t ORDER BY t.id DESC", Task.class).getResultList();
        em.close();
        return tasks;
    }

    public Task findById(Long id) {
        EntityManager em = getEntityManager();
        Task task = em.find(Task.class, id);
        em.close();
        return task;
    }

    public Task getTaskWithTags(Long id) {
        EntityManager em = getEntityManager();
        Task task = em.createQuery("SELECT t FROM Task t JOIN FETCH t.tags WHERE t.id = :id", Task.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return task;
    }

    public List<Task> findByTag(Long tagId) {
        EntityManager em = getEntityManager();

        List<Task> tasks = em.createQuery(
                        "SELECT t FROM Task t JOIN t.tags tag WHERE tag.id = :tagId", Task.class)
                .setParameter("tagId", tagId)
                .getResultList();

        em.close();
        return tasks;
    }


}

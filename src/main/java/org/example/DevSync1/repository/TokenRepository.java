package org.example.DevSync1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.DevSync1.config.Config;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.Token;

import java.util.List;
import java.util.Optional;

public class TokenRepository {


    public Token save(Token token) {
        EntityManager em = Config.getEntityManager();
        em.getTransaction().begin();
        em.persist(token);
        em.getTransaction().commit();
        em.close();
        return token;
    }

    public boolean update(Token token) {
        EntityManager em = Config.getEntityManager();

        em.getTransaction().begin();
        em.merge(token);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean delete(Token token) {

        EntityManager em = Config.getEntityManager();
        em.getTransaction().begin();
        Token token1 = em.find(Token.class, token.getId());
        if (token1 != null) {
            em.remove(token1);
        }
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public List<Token> findAll() {

        EntityManager em = Config.getEntityManager();
        em.getTransaction().begin();
        List<Token> tokens = em.createQuery("SELECT c FROM Token c ORDER BY c.id DESC", Token.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return tokens;
    }

    public Token findById(Long id) {

        EntityManager em = Config.getEntityManager();
        em.getTransaction().begin();
        Token token = em.find(Token.class, id);
        em.getTransaction().commit();
        em.close();
        return token;
    }

    public Optional<Token> findByUserId(Long id) {
        EntityManager em = Config.getEntityManager();
        em.getTransaction().begin();

        List<Token> tokens = em.createQuery("SELECT c FROM Token c WHERE c.user.id = :id", Token.class)
                .setParameter("id", id)
                .getResultList();

        em.getTransaction().commit();
        em.close();

        return tokens.isEmpty() ? Optional.empty() : Optional.of(tokens.get(0));
    }




}
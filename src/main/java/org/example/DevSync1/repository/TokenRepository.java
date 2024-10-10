package org.example.DevSync1.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.DevSync1.config.Config;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.Token;

import java.util.List;

public class TokenRepository {

    private final  EntityManager em;

    public TokenRepository() {
        this.em = Config.getEntityManager();
    }
    public boolean save(Token token) {
        em.getTransaction().begin();
        em.persist(token);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean update(Token token) {
        em.getTransaction().begin();
        em.merge(token);
        em.getTransaction().commit();
        em.close();
        return true;
    }

    public boolean delete(Token token) {

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
        em.getTransaction().begin();
        List<Token> tokens = em.createQuery("SELECT c FROM Token c ORDER BY c.id DESC", Token.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return tokens;
    }

    public Token findById(Long id) {
        em.getTransaction().begin();
        Token token = em.find(Token.class, id);
        em.getTransaction().commit();
        em.close();
        return token;
    }

}
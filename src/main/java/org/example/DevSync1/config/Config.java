package org.example.DevSync1.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Config {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    private Config() {

    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}

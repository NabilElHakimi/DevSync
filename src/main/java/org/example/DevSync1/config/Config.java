package org.example.DevSync1.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Config {
    private Config(){

    }

    public static EntityManager getEntityManager(){
        EntityManagerFactory emf = jakarta.persistence.Persistence.createEntityManagerFactory("default");
        return emf.createEntityManager();
    }
}

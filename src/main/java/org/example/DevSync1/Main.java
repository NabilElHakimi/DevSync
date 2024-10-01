package org.example.DevSync1;

import org.example.DevSync1.entity.User;
import org.example.DevSync1.repository.UserRepository;

public class Main {
    public static void main(String[] args) {



        System.out.println(new UserRepository().findAll().size());


    }


}

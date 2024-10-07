package org.example.DevSync1.service;

import org.example.DevSync1.entity.User;
import org.example.DevSync1.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    public boolean save(User user) {
        return userRepository.save(user);
    }

    public boolean update(User user) {
        return userRepository.update(user);
    }

    public boolean delete(Long id) {
        return userRepository.delete(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id);
    }




}

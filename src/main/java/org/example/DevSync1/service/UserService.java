package org.example.DevSync1.service;

import org.example.DevSync1.entity.Tag;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.Token;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.enums.Status;
import org.example.DevSync1.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    private final TokenService tokenService = new TokenService();

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
        return userRepository.findAll().stream()
                .peek(this::getTokenforUser)
                .collect(Collectors.toList());
    }

    public User findById(Long id){

        return getTokenforUser(userRepository.findById(id));
    }

    public List<Task> getTasks(){
        return new TaskService().getAllTasks();
    }

    public List<Tag> getTags(){
        return new TagService().findAll();
    }

    public User getTokenforUser(User user){
        user.setToken(tokenService.findByUserId(user.getId()).orElse(null));

        if(user.getToken() == null && user.getRole().equals(Role.USER)){

            Token token = new Token();
            token.setUser(user);
            token.setDailyTokens(2);
            token.setUpdatedAt(LocalDate.now().atStartOfDay());
            tokenService.save(token);

        }else {
            if(user.getRole().equals(Role.USER) && !user.getToken().getUpdatedAt().equals(LocalDate.now().atStartOfDay())){

                user.getToken().setDailyTokens(2);
                user.getToken().setUpdatedAt(LocalDate.now().atStartOfDay());
                tokenService.update(user.getToken());

            }
        }
        return user;
    }



}

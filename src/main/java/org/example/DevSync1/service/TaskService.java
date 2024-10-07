package org.example.DevSync1.service;

import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.Token;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.repository.TaskRepository;
import org.example.DevSync1.repository.TokenRepository;

import java.util.List;

public class TaskService {

        private final TaskRepository taskRepository = new TaskRepository();

        public List<Task> getAllTasks() {
            return taskRepository.findAll();
        }

        public Task getTaskById(Long id) {
            return taskRepository.findById(id);
        }

        public void save(Task task) {
            taskRepository.save(task);
        }

        public void update(Task task) {
            taskRepository.update(task);
        }

        public void delete(Long id) {
            taskRepository.delete(id);
        }

        public User getAssignedUser(Long id){
            return new UserService().findById(id);
        }

        public List<User> getAllUsers(){
                return new UserService().findAll();
        }


}
package org.example.DevSync1.service;

import org.example.DevSync1.entity.Tag;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Status;
import org.example.DevSync1.repository.TaskRepository;
import org.example.DevSync1.repository.UserRepository; // Import the UserRepository if necessary

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {

    private final TaskRepository taskRepository = new TaskRepository();

    public List<Task> getAllTasks() {
         return  taskRepository.findAll().stream()
                 .peek(task -> {
                        task.setTags(task.getTags().stream().map(tag -> new TagService().findById(tag.getId())).collect(Collectors.toList()));

                        if (LocalDate.now().isAfter(task.getDueDate())) {
                            task.setStatus(Status.Completed);
                            update(task);
                        }
                 }).collect(Collectors.toList());

    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public boolean save(Task task) {
        if (LocalDate.now().plusDays(3).isBefore(task.getDueDate())) {
            taskRepository.save(task);
            return true;
        }
        return false;
    }

    public boolean update(Task task) {
        if (LocalDate.now().plusDays(3).isBefore(task.getDueDate())) {
            taskRepository.update(task);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        return taskRepository.delete(id);
    }

    public User getAssignedUser(Long id) {
        return new UserService().findById(id);
    }

    public List<User> getAllUsers() {
        return new UserService().findAll();
    }

    public boolean addTaskWithTags(Task task, List<Tag> tags) {
        task.setTags(tags);
        return taskRepository.save(task);
    }


    public List<Tag> getAllTags(){
            return new TagService().findAll();
    }

    public Tag getTagById(Long id){
        return new TagService().findById(id);
    }
}

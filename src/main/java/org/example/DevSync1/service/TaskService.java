package org.example.DevSync1.service;

import org.example.DevSync1.entity.Tag;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.repository.TaskRepository;
import org.example.DevSync1.repository.UserRepository; // Import the UserRepository if necessary

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

    public boolean delete(Long id) {
        taskRepository.delete(id);
        return true;
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

    public boolean updateTaskWithNewTags(Long taskId, Task updatedTask , List<Tag> newTags) {

        Task existingTask = taskRepository.findById(taskId);

        if (existingTask == null) {
            return false;
        }

        existingTask.getTags().clear();

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setAssignedTo(updatedTask.getAssignedTo());

        for (Tag tag : newTags) {
            existingTask.getTags().add(tag);
        }

        taskRepository.update(existingTask);

        return true;
    }


    public List<Tag> getAllTags(){
            return new TagService().findAll();
    }

    public Tag getTagById(Long id){
        return new TagService().findById(id);
    }
}

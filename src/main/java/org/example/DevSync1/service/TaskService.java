package org.example.DevSync1.service;

import org.example.DevSync1.entity.Tag;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.Token;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.enums.Status;
import org.example.DevSync1.repository.TaskRepository;
import org.example.DevSync1.repository.TokenRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TaskService {

    private final TaskRepository taskRepository = new TaskRepository();
    private final TokenService tokenService = new TokenService();

    public List<Task> getAllTasks() {
         return  taskRepository.findAll();

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

    public boolean delete(Long id , String role) {

        Task task = taskRepository.findById(id);

        if (task != null) {
            if (task.getAssignedTo() != null && task.getCreatedBy().equals(task.getAssignedTo()) && task.getCreatedBy().getRole().equals(Role.USER)) {
                return taskRepository.delete(id);
            }
            else if (role.equals(Role.MANAGER.toString())) {
                return taskRepository.delete(id);
            } else {
                task.setAccepted(false);
                Optional<Token> user = new TokenService().findByUserId(task.getAssignedTo().getId());
                if (user.isPresent() && user.get().getDailyTokens() > 0 && user.get().getMonthUsed() == 0 ) {
                    user.get().setDailyTokens(user.get().getDailyTokens() - 1);
                    user.get().setMonthUsed(LocalDate.now().getMonthValue());
                    if(new TokenRepository().update(user.get())){
                        task.setAssignedTo(null);
                        task.setChanged(0);
                        return taskRepository.update(task);

                    };
                }
            }
        }

        return false;
    }



    public User getAssignedUser(Long id) {
        return new UserService().findById(id);
    }

    public List<User> getAllUsers() {
        return new UserService().findAll();
    }

    public List<Tag> getAllTags(){
            return new TagService().findAll();
    }

    public Tag getTagById(Long id){
        return new TagService().findById(id);
    }

    public void CheckTask(Task task){
        if (task.getDueDate().isBefore(LocalDate.now())) {
            task.setStatus(Status.Cancelled);
            taskRepository.update(task);
        }
    }

    public boolean changeTask(Task task, int action , String role) {
        Task taskForChange = getTaskById(task.getId());

        if(taskForChange != null){
            if(role.equals(Role.USER.toString()) && task.getChanged() >= 0){
                tokenService.findByUserId(task.getAssignedTo().getId()).ifPresent(token -> {
                    token.setDailyTokens(token.getDailyTokens() -  1);
                    tokenService.update(token);
                    taskForChange.setChanged(action);
                    update(taskForChange);

                });

                }else if(role.equals(Role.MANAGER.toString())){

                taskForChange.setAssignedTo(task.getAssignedTo());
                taskForChange.setChanged(action);
                update(taskForChange);
                return true;
            }

            return true;

        }

        return false;
    }

    public List<Task> filterByTag(Long id) {
        return getAllTasks().stream()
                .filter(task -> task.getTags().stream().anyMatch(tag -> Objects.equals(tag.getId(), id)))
                .collect(Collectors.toList());
    }

    public double taskStatistic(Status status) {
        List<Task> tasks = getAllTasks();

        long nbrCompletedTasks = tasks.stream()
                .filter(task -> task.getStatus().equals(status))
                .count();

        return (double) nbrCompletedTasks * 100 / tasks.size();
    }

}

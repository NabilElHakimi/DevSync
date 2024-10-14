package org.example.DevSync1.util;

import org.example.DevSync1.service.TaskService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CheckTasksScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private final TaskService taskService;

    public CheckTasksScheduler() {
        this.taskService = new TaskService();
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::CheckTasks, 0, 12, TimeUnit.HOURS);
    }

    public void CheckTasks(){
        taskService.getAllTasks().forEach(taskService::CheckTask);
    }

}

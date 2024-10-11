package org.example.DevSync1.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SchedulerContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        CheckTokenScheduler checkTokenScheduler = new CheckTokenScheduler();
        CheckTasksScheduler checkTasksScheduler = new CheckTasksScheduler();
        checkTokenScheduler.start();
        checkTasksScheduler.start();

    }
}
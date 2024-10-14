package org.example.DevSync1.util;

import org.example.DevSync1.service.UserService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CheckTokenScheduler {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    private final UserService user;

    public CheckTokenScheduler() {
        this.user = new UserService();
    }

    public void start() {
        scheduler.scheduleAtFixedRate(this::CheckToken, 0, 12, TimeUnit.HOURS);
    }

    public void CheckToken(){
            user.findAll().forEach(user::getTokenUser);
    }

}

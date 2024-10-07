package org.example.DevSync1.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.service.TaskService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "task", urlPatterns = "/task")
public class TaskServlet extends HttpServlet {

    private final TaskService taskService = new TaskService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> tasks = taskService.getAllTasks();
        request.setAttribute("tasks", tasks);
        RequestDispatcher dispatcher = request.getRequestDispatcher("manager/task/Task.jsp");
        dispatcher.forward(request, response);
    }


  }

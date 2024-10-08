package org.example.DevSync1.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.service.TaskService;

import java.io.IOException;
import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "tasks", urlPatterns = "/tasks")
public class TaskServlet extends HttpServlet {

    private final TaskService taskService = new TaskService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Task> tasks = taskService.getAllTasks();
        request.setAttribute("tasks", tasks);

        List<User> users = taskService.getAllUsers();
        List<User> filteredUsers = users.stream()
                .filter(user -> user.getRole().equals(Role.USER))
                .collect(Collectors.toList());

        request.setAttribute("users", filteredUsers);

        RequestDispatcher dispatcher = request.getRequestDispatcher("manager/task/Task.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        System.out.println("ID iSssssssssssssssssssssssssssssss" + id);

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDateStr = request.getParameter("dueDate");
        String assignedToId = request.getParameter("assignedTo");

        if ("update".equals(action)) {
            Task task = new Task();
            task.setId(Long.valueOf(id));
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(LocalDate.parse(dueDateStr));
            task.setAssignedTo(taskService.getAssignedUser(Long.valueOf(assignedToId)));
            taskService.update(task);

            response.sendRedirect("tasks?action=update&message=Task updated successfully");
        }

        else if ("delete".equals(action)) {

            taskService.delete(Long.valueOf(id));
            System.err.println("waaaaaaaaa3333333333333b " + id);

            response.sendRedirect("tasks?action=delete&message=Task deleted successfully");

        } else {
            Task task = new Task();
            task.setTitle(title);
            task.setDescription(description);
            task.setAssignedTo(taskService.getAssignedUser(Long.valueOf(assignedToId)));

            task.setDueDate(LocalDate.parse(dueDateStr));
            taskService.save(task);
            response.sendRedirect("tasks?action=add&message=Task added successfully");
        }
    }



}

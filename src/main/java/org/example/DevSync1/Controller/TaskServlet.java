package org.example.DevSync1.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DevSync1.entity.Tag;
import org.example.DevSync1.entity.Task;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.enums.Status;
import org.example.DevSync1.service.TaskService;
import org.example.DevSync1.service.UserService;

import java.io.IOException;
import java.time.LocalDate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

        List<Tag> tags = taskService.getAllTags();
        request.setAttribute("tags" , tags);

        RequestDispatcher dispatcher = request.getRequestDispatcher("manager/task/Task.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String action = request.getParameter("action");
        String id = request.getParameter("id");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDateStr = request.getParameter("dueDate");
        String assignedToId = request.getParameter("assignedTo");
        String status = request.getParameter("status");

        String idParam = request.getSession().getAttribute("UserId").toString();


        if ("update".equals(action)) {
            Task task = new Task();

            Optional.ofNullable(request.getParameterValues("tags[]"))
                    .ifPresent(tags -> Arrays.stream(tags).forEach(tagId -> {
                        Tag tag = taskService.getTagById(Long.valueOf(tagId));
                        if (tag != null) {
                            task.getTags().add(tag);
                        }
                    }));


            task.setCreatedBy(taskService.getAssignedUser(Long.valueOf(idParam)));
            task.setStatus(Status.valueOf(status));
            task.setId(Long.valueOf(id));
            task.setTitle(title);
            task.setDescription(description);
            task.setDueDate(LocalDate.parse(dueDateStr));
            task.setAssignedTo(taskService.getAssignedUser(Long.valueOf(assignedToId)));
            taskService.update(task);

            response.sendRedirect("tasks?action=update&message=Task updated successfully");
        }

        else if ("delete".equals(action)) {

            if(taskService.delete(Long.valueOf(id))) {
                response.sendRedirect("tasks?action=delete&message=Task deleted successfully");
            } else {
                response.sendRedirect("tasks?action=delete&messageErr=Task not found");
            }


        } else {
            Task task = new Task();

            Optional.ofNullable(request.getParameterValues("tags[]"))
                    .ifPresent(tags -> Arrays.stream(tags).forEach(tagId -> {
                        Tag tag = taskService.getTagById(Long.valueOf(tagId));
                        if (tag != null) {
                            task.getTags().add(tag);
                        }
                    }));

            task.setCreatedBy(taskService.getAssignedUser(Long.valueOf(idParam)));
            task.setTitle(title);
            task.setDescription(description);
            task.setAssignedTo(taskService.getAssignedUser(Long.valueOf(assignedToId)));
            task.setDueDate(LocalDate.parse(dueDateStr));
            task.setStatus(Status.InProgress);
            if(!taskService.save(task)){
                response.sendRedirect("tasks?action=add&messageErr=Task due date should be greater than today");
            }
            else {
                response.sendRedirect("tasks?action=add&message=Task added successfully");
            }
        }
    }



}

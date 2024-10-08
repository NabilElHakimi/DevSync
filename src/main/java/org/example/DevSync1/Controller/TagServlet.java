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
import org.example.DevSync1.service.TagService;
import org.example.DevSync1.service.TaskService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "tags", urlPatterns = "/tags")
public class TagServlet extends HttpServlet {

    private final TagService tagService = new TagService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tag> tags = tagService.findAll();
        request.setAttribute("tags", tags);

        RequestDispatcher dispatcher = request.getRequestDispatcher("manager/tag/Tag.jsp");
        dispatcher.forward(request, response);
    }

    /*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String dueDateStr = request.getParameter("dueDate");
        String assignedToId = request.getParameter("assignedTo");

        if ("update".equals(action)) {
            Task task = new Task();

            Optional.ofNullable(request.getParameterValues("tags[]"))
                    .ifPresent(tags -> Arrays.stream(tags).forEach(tagId -> {
                        Tag tag = taskService.getTagById(Long.valueOf(tagId));
                        if (tag != null) {
                            task.getTags().add(tag);
                        }
                    }));

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

            Optional.ofNullable(request.getParameterValues("tags[]"))
                    .ifPresent(tags -> Arrays.stream(tags).forEach(tagId -> {
                        Tag tag = taskService.getTagById(Long.valueOf(tagId));
                        if (tag != null) {
                            task.getTags().add(tag);
                        }
                    }));

            task.setTitle(title);
            task.setDescription(description);
            task.setAssignedTo(taskService.getAssignedUser(Long.valueOf(assignedToId)));
            task.setDueDate(LocalDate.parse(dueDateStr));

            taskService.save(task);
            response.sendRedirect("tasks?action=add&message=Task added successfully");


        }
    }*/



}

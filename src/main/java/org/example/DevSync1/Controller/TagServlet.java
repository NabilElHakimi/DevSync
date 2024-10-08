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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        String tagName = request.getParameter("tagName");
        String description = request.getParameter("description");

        if ("update".equals(action)) {
            Tag tag = new Tag();
            tag.setId(Long.valueOf(id));
            tag.setName(tagName);
            tag.setDescription(description);

            tagService.update(tag);

            response.sendRedirect("tags?action=update&message=Task updated successfully");
        }

        else if ("delete".equals(action)) {

            tagService.delete(Long.valueOf(id));

            response.sendRedirect("tags?action=delete&message=Task deleted successfully");

        } else {
            Tag tag = new Tag();


            tag.setName(tagName);
            tag.setDescription(description);
            tagService.save(tag);
            response.sendRedirect("tags?action=add&message=Task added successfully");


        }
    }



}

package org.example.DevSync1.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.repository.UserRepository;
import org.example.DevSync1.service.UserService;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.findAll();
        request.setAttribute("users", users);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String fname = request.getParameter("firstName");
        String lname = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if ("update".equals(action) && id != null) {
            User user = new User();
            user.setId(Long.parseLong(id));
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(Role.valueOf(role));

            userService.update(user);
            response.sendRedirect("users?action=update&message=User updated successfully");
        } else if ("delete".equals(action) && id != null) {
            userService.delete(Long.parseLong(id));
            response.sendRedirect("users?action=delete&message=User deleted successfully");
        } else {
            User user = new User();
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(Role.valueOf(role));
            userService.save(user);
            response.sendRedirect("users?action=add&message=User added successfully");
        }
    }
}

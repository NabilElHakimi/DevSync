package org.example.DevSync1.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.repository.UserRepository;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private final UserRepository userRepository = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userRepository.findAll();
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

        if ("update".equals(action) && id != null) {
            User carToUpdate = new User();
            carToUpdate.setId(Long.parseLong(id));
            carToUpdate.setFirstName(fname);
            carToUpdate.setLastName(lname);
            carToUpdate.setEmail(email);
            carToUpdate.setPassword(password);
            userRepository.update(carToUpdate);
            response.sendRedirect("users?action=update&message=User updated successfully");
        } else if ("delete".equals(action) && id != null) {
            userRepository.delete(Long.parseLong(id));
            response.sendRedirect("users?action=delete&message=User deleted successfully");
        } else {
            User user = new User();
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setEmail(email);
            user.setPassword(password);

            userRepository.save(user);
            response.sendRedirect("users?action=add&message=User added successfully");
        }
    }
}

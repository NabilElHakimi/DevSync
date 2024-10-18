package org.example.DevSync1.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.repository.UserRepository;
import org.example.DevSync1.service.TokenService;
import org.example.DevSync1.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "manager" , urlPatterns = "/manager")
public class ManagerServlet extends HttpServlet {

    private final UserService userService = new UserService(new UserRepository(),new TokenService());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<User> users = userService.findAll();

        List<User> filteredUsers = users.stream()
                .filter(user -> user.getRole().equals(Role.USER))
                .collect(Collectors.toList());

        request.setAttribute("users", filteredUsers);

        RequestDispatcher dispatcher = request.getRequestDispatcher("manager/manager.jsp");
        dispatcher.forward(request, response);
    }


}

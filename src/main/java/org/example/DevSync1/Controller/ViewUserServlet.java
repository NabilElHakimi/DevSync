package org.example.DevSync1.Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "user" , urlPatterns = "/user")
public class ViewUserServlet extends HttpServlet {

    private final UserRepository userRepository = new UserRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && idParam.matches("\\d+")) {
            User user = userRepository.findById(Long.valueOf(idParam));

            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("viewUser.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("users?action=error&message=Invalid or missing User ID");
    }


}

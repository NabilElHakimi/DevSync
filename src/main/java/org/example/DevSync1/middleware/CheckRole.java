package org.example.DevSync1.middleware;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.DevSync1.entity.User;
import org.example.DevSync1.enums.Role;
import org.example.DevSync1.repository.UserRepository;

import java.io.IOException;

@WebServlet(name = "checkRole" , urlPatterns = "/checkRole")
public class CheckRole extends HttpServlet {

    private final UserRepository userRepository = new UserRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        User user = userRepository.findById(Long.valueOf(idParam));
        if(user != null) {
            request.getSession().setAttribute("UserId" , idParam);
            if(user.getRole() == Role.MANAGER){
                response.sendRedirect("manager");
            }
            else if(user.getRole() == Role.USER){
                request.setAttribute("user", user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("user");
                dispatcher.forward(request, response);
            }
        }

    }


}

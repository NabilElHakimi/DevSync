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
import org.example.DevSync1.service.UserService;

import java.io.IOException;
import java.util.List;


@WebServlet(name = "user" , urlPatterns = "user")
public class UserServlet extends HttpServlet {
    private final UserService userService   = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getSession().getAttribute("UserId").toString();
        User user = userService.findById(Long.valueOf(idParam));

        request.setAttribute("user", user);

        List<Task> tasks = userService.getTasks();
        request.setAttribute("tasks", tasks);

        List<Tag> tags= userService.getTags();
        request.setAttribute("tags", tags);



        RequestDispatcher dispatcher = request.getRequestDispatcher("user/user.jsp");
        dispatcher.forward(request, response);

    }


}

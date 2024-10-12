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
    import org.example.DevSync1.enums.Status;
    import org.example.DevSync1.service.TaskService;
    import org.example.DevSync1.service.UserService;

    import java.io.IOException;
    import java.time.LocalDate;
    import java.util.Arrays;
    import java.util.List;
    import java.util.Optional;

    @WebServlet(name = "user" , urlPatterns = "user")
    public class UserServlet extends HttpServlet {

        private final UserService userService   = new UserService();
        private final TaskService taskService = new TaskService();

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String idParam = request.getSession().getAttribute("UserId").toString();
            User user = userService.findById(Long.valueOf(idParam));

            request.setAttribute("user", user);

            List<Task> tasks = userService.getTasks();
            request.setAttribute("tasks", tasks);

            List<Tag> tags= userService.getTags();
            request.setAttribute("tags", tags);

            if(request.getParameter("dislike") != null){
                if(taskService.changeTask(Long.valueOf(request.getParameter("dislike")))){
                    response.sendRedirect("user?message=Task Disliked");
                }else{
                    response.sendRedirect("user?message=You can't dislike this task");
                }
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("user/user.jsp");
            dispatcher.forward(request, response);

        }


        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

            Long idParam = Long.parseLong(request.getSession().getAttribute("UserId").toString());

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
                task.setAssignedTo(userService.findById(idParam));
                taskService.update(task);

                response.sendRedirect("user?action=update&message=Task updated successfully");
            }

            else if ("delete".equals(action)) {

                taskService.delete(Long.valueOf(id) , "USER");

                response.sendRedirect("user?action=delete&message=Task deleted successfully");


            } else {
                Task task = new Task();

                Optional.ofNullable(request.getParameterValues("tags[]"))
                        .ifPresent(tags -> Arrays.stream(tags).forEach(tagId -> {
                            Tag tag = taskService.getTagById(Long.valueOf(tagId));
                            if (tag != null) {
                                task.getTags().add(tag);
                            }
                        }));

                task.setCreatedBy(userService.findById(idParam));
                task.setTitle(title);
                task.setDescription(description);
                task.setDueDate(LocalDate.parse(dueDateStr));
                task.setAssignedTo(userService.findById(idParam));
                task.setStatus(Status.InProgress);
                taskService.save(task);

                response.sendRedirect("user?action=add&message=Task added successfully");


            }
        }


    }

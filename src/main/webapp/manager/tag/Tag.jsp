<%@ page import="org.example.DevSync1.entity.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.DevSync1.entity.User" %>
<%@ page import="org.example.DevSync1.entity.Tag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Task List</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
    <style>
        .sidebar {
            height: 100vh;
            background-color: #f8f9fa;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
        }
        .sidebar a {
            display: block;
            padding: 10px;
            color: #333;
            text-decoration: none;
            margin-bottom: 10px;
            border-radius: 5px;
        }
        .sidebar a:hover {
            background-color: #e2e6ea;
        }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../component/sidebar.jsp" />
        <main class="col-md-9">
            <div class="container mt-5">
                <h1 class="text-primary text-center mb-4">Tasks List</h1>

                <%
                    String message = request.getParameter("message");
                    if (message != null && !message.isEmpty()) {
                %>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Success!</strong> <%= message %>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <%
                    }
                %>

                <button class="btn btn-success mb-2" data-toggle="modal" data-target="#addTaskModal">Add Task</button>

                <table class="table table-bordered table-hover">
                    <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>Tag Name</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Tag> tags = (List<Tag>) request.getAttribute("tags");
                        if (tags != null && !tags.isEmpty()) {
                            for (Tag tag : tags) {
                    %>
                    <tr>
                        <td><%= tag.getId() %></td>
                        <td><%= tag.getName() != null ? tag.getName() : "N/A" %></td>
                        <td><%= tag.getDescription() != null ? tag.getDescription() : "N/A" %></td>
                        <td style="width: 10%">
                            <button class="btn btn-primary btn-sm" onclick="openUpdateModal(<%= tag.getId() %>, '<%= tag.getName() %>', '<%= tag.getDescription() %>')">
                                <i class="fas fa-edit"></i>
                            </button>

                            <button class="btn btn-danger btn-sm" onclick="confirmDelete(<%= tag.getId() %>, '<%= tag.getName() %>')">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </td>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="4" class="text-center text-danger">No tasks found.</td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>

                <!-- Add Task Modal -->
                <div class="modal fade" id="addTaskModal" tabindex="-1" role="dialog" aria-labelledby="addTaskModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addTaskModalLabel">Add New Task</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form action="tags" method="post">
                                    <input type="hidden" name="action" value="create">
                                    <div class="form-group">
                                        <label for="taskName">Task Name:</label>
                                        <input type="text" class="form-control" name="tagName" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="description">Description:</label>
                                        <textarea class="form-control" name="description" required></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-success">Add Task</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Update Task Modal -->
                <div class="modal fade" id="updateTaskModal" tabindex="-1" role="dialog" aria-labelledby="updateTaskModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="updateTaskModalLabel">Update Task</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="updateTaskForm" action="tags" method="post">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" id="updateTaskId" name="id" value="">
                                    <div class="form-group">
                                        <label for="updateTaskName">Task Name:</label>
                                        <input type="text" class="form-control" id="updateTaskName" name="tagName" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateDescription">Description:</label>
                                        <textarea class="form-control" id="updateDescription" name="description" required></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Update Task</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="modal fade" id="confirmDeleteModal" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="confirmDeleteModalLabel">Confirm Delete</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                Are you sure you want to delete <span id="deleteTaskName"></span>?
                            </div>
                            <div class="modal-footer">
                                <form id="deleteTaskForm" action="tags" method="post">
                                    <input type="hidden" name="id" id="deleteTaskId">
                                    <input type="hidden" value="delete" name="action">
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>



                <script>
                    function openUpdateModal(id, name, description) {
                        document.getElementById("updateTaskId").value = id;
                        document.getElementById("updateTaskName").value = name;
                        document.getElementById("updateDescription").value = description;
                        $('#updateTaskModal').modal('show');
                    }

                    function confirmDelete(id, name) {
                        document.getElementById('deleteTaskId').value = id;
                        document.getElementById('deleteTaskName').innerText = name;
                        $('#confirmDeleteModal').modal('show');
                    }

                </script>
            </div>
        </main>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

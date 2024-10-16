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
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <jsp:include page="../component/sidebar.jsp" />
        <!-- Main Content -->
        <main class="col-md-12">



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

                <%
                    String messageErr = request.getParameter("messageErr");
                    if (messageErr != null && !messageErr.isEmpty()) {
                %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <strong>Field!</strong> <%= messageErr %>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <%
                    }
                %>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <h5>Pending Tasks: <%= request.getAttribute("PendingStatic") %> %</h5>
                        <div class="progress">
                            <div class="progress-bar bg-warning" role="progressbar" style="width: <%= request.getAttribute("PendingStatic") %>%;" aria-valuenow="<%= request.getAttribute("PendingStatic") %>" aria-valuemin="0" aria-valuemax="100">
                                <%= request.getAttribute("PendingStatic") %>%
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <h5>In Progress Tasks: <%= request.getAttribute("InProgressStatic") %> %</h5>
                        <div class="progress">
                            <div class="progress-bar bg-info" role="progressbar" style="width: <%= request.getAttribute("InProgressStatic") %>%;" aria-valuenow="<%= request.getAttribute("InProgressStatic") %>" aria-valuemin="0" aria-valuemax="100">
                                <%= request.getAttribute("InProgressStatic") %>%
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <h5>Completed Tasks: <%= request.getAttribute("CompletedStatic") %> %</h5>
                        <div class="progress">
                            <div class="progress-bar bg-success" role="progressbar" style="width: <%= request.getAttribute("CompletedStatic") %>%;" aria-valuenow="<%= request.getAttribute("CompletedStatic") %>" aria-valuemin="0" aria-valuemax="100">
                                <%= request.getAttribute("CompletedStatic") %>%
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <h5>Canceled Tasks: <%= request.getAttribute("CanceledStatic") %> %</h5>
                        <div class="progress">
                            <div class="progress-bar bg-danger" role="progressbar" style="width: <%= request.getAttribute("CanceledStatic") %>%;" aria-valuenow="<%= request.getAttribute("CanceledStatic") %>" aria-valuemin="0" aria-valuemax="100">
                                <%= request.getAttribute("CanceledStatic") %>%
                            </div>
                        </div>
                    </div>
                </div>

                <br>
                <br>
                <br>

                <button class="btn btn-success" style="margin-bottom: 2%" data-toggle="modal" data-target="#addTaskModal">Add Task</button>

                <form action="tasks" method="get" class="form-group">
                    <label for="tagSelect" class="form-label">Select a Tag:</label>
                    <select name="filter" id="tagSelect" class="form-control">
                        <option value="0" disabled selected>Filter By Tags :</option>
                        <%
                            Long selectedTagId = request.getParameter("filter") != null ? Long.parseLong(request.getParameter("filter")) : 0L;
                            List<Tag> tags = (List<Tag>) request.getAttribute("tags");

                            if (tags != null && !tags.isEmpty()) {
                                for (Tag tag : tags) {
                                    boolean isSelected = tag.getId() == selectedTagId;
                        %>
                        <option value="<%= tag.getId() %>" <%= isSelected ? "selected" : "" %>><%= tag.getName() %></option>
                        <%
                                }
                            }
                        %>
                    </select>
                    <button type="submit" class="btn btn-primary mt-3">Submit</button>
                    <a href="tasks" class="btn btn-outline-secondary mt-3">Remove Filter</a>

                </form>


                <table class="table table-bordered table-hover">
                    <thead class="thead-dark">
                    <tr>
                        <th>Created By</th>
                        <th>Task Name</th>
                        <th>Description</th>
                        <th>Assigned To</th>
                        <th>Due Date</th>
                        <th>Tags</th>
                        <th>Status</th>
                        <th>Actions</th>

                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                        if (tasks != null && !tasks.isEmpty()) {
                            for (Task task : tasks) {
                    %>

                    <tr>
                        <td><%= task.getCreatedBy() != null ? task.getCreatedBy().getFirstName()  + " " + task.getCreatedBy().getLastName() : "N/A" %></td>
                        <td><%= task.getTitle() != null ? task.getTitle() : "N/A" %></td>
                        <td><%= task.getDescription() != null ? task.getDescription() : "N/A" %></td>
                        <td class="w-25">
                            <form action="tasks" method="post" class="d-flex align-items-center">
                                <input type="hidden" name="id" value="<%= task.getId() %>">
                                <input type="hidden" name="action" value="changeIt">
<%--                                <input ty > --%>
                                 <select class="form-control" name="assignedTo" required style="flex-grow: 1;">
                                    <option value="">Select a User</option>
                                    <%
                                        List<User> users = (List<User>) request.getAttribute("users");
                                        if (users != null) {
                                            for (User user : users) {
                                                boolean isSelected = task.getAssignedTo() != null && task.getAssignedTo().getId().equals(user.getId());
                                    %>
                                    <option value="<%= user.getId() %>" <%= isSelected ? "selected" : "" %>><%= user.getFirstName() + " " + user.getLastName() %></option>
                                    <%
                                        }
                                    } else {
                                    %>
                                    <option value="">No users available</option>
                                    <%
                                        }
                                    %>
                                </select>

                                <% if (task.getChanged() == -1 || task.getAssignedTo() == null ){ %>
                                    <button  type="submit" class="btn btn-sm btn-primary ml-2">
                                        <i class="fas fa-save"></i>
                                    </button>
                                <% } %>
                            </form>
                        </td>
                        <td style="width: 10%"><%= task.getDueDate() != null ? task.getDueDate() : "N/A" %></td>
                        <td class="w-25">
                            <%
                                if (task.getTags() != null && !task.getTags().isEmpty()) {
                                    for (Tag tag : task.getTags()) {
                            %>
                            <span class="badge badge-primary"><%= tag.getName() %></span>
                            <%
                                }
                            } else {
                            %>
                            <span class="badge badge-warning">No Tags</span>
                            <%
                                }
                            %>

                        </td>
                        <td><span class="badge badge-secondary"><%= task.getStatus() %></span></td>
                        <td style="width: 10%" class="flex w-25">
                            <button class="btn btn-primary btn-sm" onclick="openUpdateModal(<%= task.getId() %>, '<%= task.getTitle() %>', '<%= task.getDescription() %>', '<%= task.getDueDate() %>', '<%= task.getAssignedTo() != null ? task.getAssignedTo().getId() : "" %>')">
                                <i class="fas fa-edit"></i>
                            </button>

                            <button class="btn btn-danger btn-sm" onclick="confirmDelete(<%= task.getId() %>, '<%= task.getTitle() %>')">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </td>
                    </tr>


                    <% } } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-danger">No tasks found.</td>
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
                            <form action="tasks" method="post">
                                <input type="hidden" value="create" name="action">

                                <div class="form-group">
                                    <label for="taskName">Task Name:</label>
                                    <input type="text" class="form-control" name="title" required>
                                </div>

                                <div class="form-group">
                                    <label for="description">Description:</label>
                                    <textarea class="form-control" name="description" required></textarea>
                                </div>

                                <div class="form-group">
                                    <label for="dueDate">Due Date:</label>
                                    <input type="date" class="form-control" name="dueDate" required>
                                </div>

                                <div class="form-group">
                                    <label for="assignedTo">Assigned To:</label>
                                    <select class="form-control" id="assignedTo" name="assignedTo" required>
                                        <option value="">Select a User</option>
                                        <%
                                            List<User> availableUsers = (List<User>) request.getAttribute("users");
                                            if (availableUsers != null) {
                                                for (User user : availableUsers) {
                                        %>
                                        <option value="<%= user.getId() %>"><%= user.getFirstName() + " " + user.getLastName() %></option>
                                        <%
                                            }
                                        } else {
                                        %>
                                        <option value="">No users available</option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="tags">Tags:</label>
                                    <select class="form-control" id="tags" name="tags[]" multiple>
                                        <%
                                            List<Tag> availableTags = (List<Tag>) request.getAttribute("tags");
                                            if (availableTags != null && !availableTags.isEmpty()) {
                                                for (Tag tag : availableTags) {
                                        %>x
                                        <option value="<%= tag.getId() %>"><%= tag.getName() %></option>
                                        <%
                                            }
                                        } else {
                                        %>
                                        <option value="">No tags available</option>
                                        <%
                                            }
                                        %>
                                    </select>
                                    <small class="form-text text-muted">Hold down the Ctrl (Windows) or Command (Mac) button to select multiple tags.</small>
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
                                <form id="updateTaskForm" action="tasks" method="post">
                                    <input type="hidden" value="update" name="action">
                                    <input type="hidden" id="updateTaskId" name="id" value="">
                                    <div class="form-group">
                                        <label for="updateTaskName">Task Name:</label>
                                        <input type="text" class="form-control" id="updateTaskName" name="title" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateDescription">Description:</label>
                                        <textarea class="form-control" id="updateDescription" name="description" required></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateDueDate">Due Date:</label>
                                        <input type="date" class="form-control" id="updateDueDate" name="dueDate" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateAssignedTo">Assigned To:</label>
                                        <select class="form-control" id="updateAssignedTo" name="assignedTo" required>
                                            <option value="">Select a User</option>
                                            <%
                                                List<User> users = (List<User>) request.getAttribute("users");
                                                if (users != null) {
                                                    for (User user : users) {
                                            %>
                                            <option value="<%= user.getId() %>"><%= user.getFirstName() + " " + user.getLastName() %></option>
                                            <%
                                                }
                                            } else {
                                            %>
                                            <option value="">No users available</option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>


                                    <div class="form-group">
                                        <label for="status">Status  :</label>
                                        <select class="form-control" id="status" name="status" required>
                                            <option value="">Select a User</option>
                                            <option value="InProgress" selected>InProgress</option>
                                            <option value="Pending">Pending</option>
                                            <option value="Completed">Completed</option>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label for="tags">Tags:</label>
                                        <select class="form-control" name="tags[]" multiple>
                                            <%
                                                List<Tag> newavailableTags = (List<Tag>) request.getAttribute("tags");
                                                List<Integer> selectedTagIds = (List<Integer>) request.getAttribute("selectedTagIds"); // Get the selected tag IDs

                                                if (newavailableTags != null && !newavailableTags.isEmpty()) {
                                                    for (Tag tag : newavailableTags) {
                                                        boolean isSelected = selectedTagIds != null && selectedTagIds.contains(tag.getId());
                                            %>
                                            <option value="<%= tag.getId() %>" <%= isSelected ? "selected" : "" %>><%= tag.getName() %></option>
                                            <%
                                                }
                                            } else {
                                            %>
                                            <option value="">No tags available</option>
                                            <%
                                                }
                                            %>
                                        </select>
                                        <small class="form-text text-muted">Hold down the Ctrl (Windows) or Command (Mac) button to select multiple tags.</small>
                                    </div>

                                    <button type="submit" class="btn btn-success">Update Task</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>


                <!-- Confirm Delete Modal -->
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
                                <form id="deleteTaskForm" action="tasks" method="post">
                                    <input type="hidden" name="id" id="deleteTaskId">
                                    <input type="hidden" value="delete" name="action">
                                    <button type="submit" class="btn btn-danger">Delete</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>



                <div class="modal fade" id="confirmAccepteModal" tabindex="-1" role="dialog" aria-labelledby="confirmDeleteModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="confirmAccepteModal">Confirm Accept Change</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                Are you sure you want to Accept Change To <span id="AcceptTaskName"></span>?
                            </div>
                            <div class="modal-footer">
                                <form id="AcceptTaskForm" action="tasks" method="post">
                                    <input type="hidden" name="id" id="AcceptTaskId">
                                    <input type="hidden" value="changeIt" name="action">
                                    <button type="submit" class="btn btn-danger">Accept</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<!-- jQuery and Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    function openUpdateModal(id, title, description, dueDate, assignedTo) {
        document.getElementById('updateTaskId').value = id;
        document.getElementById('updateTaskName').value = title;
        document.getElementById('updateDescription').value = description;
        document.getElementById('updateDueDate').value = dueDate.split('T')[0]; // Extract date portion
        document.getElementById('updateAssignedTo').value = assignedTo; // Set assigned user ID
        $('#updateTaskModal').modal('show');
    }

    function confirmDelete(id, name) {
        document.getElementById('deleteTaskId').value = id;
        document.getElementById('deleteTaskName').innerText = name;
        $('#confirmDeleteModal').modal('show');
    }

    function confirmAccept(id, name) {
        document.getElementById('AcceptTaskId').value = id;
        document.getElementById('AcceptTaskName').innerText = name;
        $('#confirmAccepteModal').modal('show');
    }
</script>
</body>
</html>
```
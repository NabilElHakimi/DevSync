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

<a href="users" class="bg-danger p-2 text-white">Log out</a>

<div class="container-fluid">
  <div class="row">
    <!-- Sidebar -->

    <%-- <jsp:include page="../component/sidebar.jsp" /> --%>

    <% User userExist = (User) request.getAttribute("user"); %>

    <!-- Main Content -->
    <main class="col-md-12">
      <div class="container mt-5">
        <h1 class="text-primary text-center mb-4">Tasks List</h1>

        <h2 class="text-black text-center mb-4">Name: <%= userExist.getLastName() + " " + userExist.getFirstName() %></h2>
        <h3 class="text-danger text-center mb-4">Tokens Available Today: <%= userExist.getToken().getDailyTokens() %></h3>

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

        <button class="btn btn-success" style="margin-bottom: 2%" data-toggle="modal" data-target="#addTaskModal">Add Task</button>

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
            <th style="width: 13%">Actions</th>
          </tr>
          </thead>
          <tbody>
          <%
            List<Task> tasks = (List<Task>) request.getAttribute("tasks");
            if (tasks != null && !tasks.isEmpty()) {
              for (Task task : tasks) {
          %>

          <tr>
            <td><%= task.getAssignedTo() != null ? task.getCreatedBy().getFirstName() + " " + task.getCreatedBy().getLastName() : "N/A" %></td>
            <td><%= task.getTitle() != null ? task.getTitle() : "N/A" %></td>
            <td><%= task.getDescription() != null ? task.getDescription() : "N/A" %></td>
            <td><%= task.getAssignedTo() != null ? task.getAssignedTo().getFirstName() + " " + task.getAssignedTo().getLastName() : "N/A" %></td>
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
            <td style="width: 10%">
              <!-- Trigger for Update Modal -->

              <% if (userExist.getId() == task.getAssignedTo().getId()) { %>
              <button class="btn btn-primary btn-sm" onclick="openUpdateModal(<%= task.getId() %>, '<%= task.getTitle() %>', '<%= task.getDescription() %>', '<%= task.getDueDate() %>')">
                <i class="fas fa-edit"></i>
              </button>

              <button class="btn btn-danger btn-sm" onclick="confirmDelete(<%= task.getId() %>, '<%= task.getTitle() %>')">
                <i class="fas fa-trash-alt"></i>
              </button>

              <% if (userExist.getToken().getDailyTokens() > 0) { %>
              <button class="btn btn-danger btn-sm" onclick="dislikeTask(<%= task.getId() %>)">
                <i class="fas fa-thumbs-down"></i>
              </button>
              <% } %>

              <% } %>
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
                <form action="user" method="post">
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
                    <label for="tags">Tags:</label>
                    <select class="form-control" id="tags" name="tags[]" multiple>
                      <%
                        List<Tag> availableTags = (List<Tag>) request.getAttribute("tags");
                        if (availableTags != null && !availableTags.isEmpty()) {
                          for (Tag tag : availableTags) {
                      %>
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
                <form action="user" method="post">
                  <input type="hidden" id="updateTaskId" name="id">
                  <input type="hidden" value="update" name="action">

                  <div class="form-group">
                    <label for="taskName">Task Name:</label>
                    <input type="text" class="form-control" id="updateTaskName" name="title" required>
                  </div>

                  <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea class="form-control" id="updateTaskDescription" name="description" required></textarea>
                  </div>

                  <div class="form-group">
                    <label for="dueDate">Due Date:</label>
                    <input type="date" class="form-control" id="updateTaskDueDate" name="dueDate" required>
                  </div>

                  <button type="submit" class="btn btn-primary">Update Task</button>
                </form>
              </div>
            </div>
          </div>
        </div>

      </div>
    </main>
  </div>
</div>

<!-- jQuery and Bootstrap Bundle (includes Popper) -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
  function openUpdateModal(id, title, description, dueDate) {
    document.getElementById('updateTaskId').value = id;
    document.getElementById('updateTaskName').value = title;
    document.getElementById('updateTaskDescription').value = description;
    document.getElementById('updateTaskDueDate').value = dueDate;
    $('#updateTaskModal').modal('show');
  }

  function confirmDelete(taskId, taskName) {
    const confirmed = confirm(`Are you sure you want to delete the task: ${taskName}?`);
    if (confirmed) {
      window.location.href = `user?action=delete&id=${taskId}`;
    }
  }

  function dislikeTask(taskId) {
    const confirmed = confirm(`Are you sure you want to dislike this task? This will consume a token.`);
    if (confirmed) {
      window.location.href = `user?dislike=` + taskId;
    }
  }
</script>

</body>
</html>

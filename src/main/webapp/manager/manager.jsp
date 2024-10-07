<%@ page import="org.example.DevSync1.entity.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User List</title>
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
        .password {
            cursor: pointer;
        }
        .password:hover::after {
            content: attr(title);
            position: absolute;
            background-color: #f9f9f9;
            padding: 5px;
            border-radius: 5px;
            box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>

<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <nav class="col-md-3 sidebar">
            <h4>Navigation</h4>
            <a href="users" class="active">Users</a>
            <a href="#tasks">Tasks</a>
            <a href="#tags">Tags</a>
        </nav>

        <!-- Main Content -->
        <main class="col-md-9">
            <div class="container mt-5">
                <h1 class="text-primary text-center mb-4">Users List</h1>

                <!-- Success Message Alert -->
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

                <button class="btn btn-success" style="margin-bottom: 2%" data-toggle="modal" data-target="#addUserModal">Add User</button>

                <table class="table table-bordered table-hover">
                    <thead class="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Password</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<User> users = (List<User>) request.getAttribute("users");
                        if (users != null && !users.isEmpty()) {
                            for (User user : users) {
                    %>
                    <tr>
                        <td><%= user.getId() %></td>
                        <td><%= (user.getFirstName() != null) ? user.getFirstName() : "N/A" %></td>
                        <td><%= (user.getLastName() != null) ? user.getLastName() : "N/A" %></td>
                        <td><%= (user.getEmail() != null) ? user.getEmail() : "N/A" %></td>
                        <td>
                            <span class="password" title="<%= user.getPassword() %>">
                                <%= (user.getPassword() != null) ? "••••••••••••" : "N/A" %>
                            </span>
                        </td>
                        <td><%= (user.getRole() != null) ? user.getRole() : "N/A" %></td>

                        <td>

                            <!-- Trigger for Update Modal -->
                            <button class="btn btn-primary btn-sm" onclick="openUpdateModal(<%= user.getId() %>, '<%= user.getFirstName() %>', '<%= user.getLastName() %>', '<%= user.getEmail() %>', '<%= user.getPassword() %>' , '<%= user.getRole() %>')">
                                <i class="fas fa-edit"></i>
                            </button>   

                            <button class="btn btn-danger btn-sm" onclick="confirmDelete(<%= user.getId() %>, '<%= user.getFirstName() + " " + user.getLastName() %>')">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </td>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="6" class="text-center text-danger">No users found.</td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>

                <!-- Add User Modal -->
                <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addUserModalLabel">Add New User</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form action="users" method="post">
                                    <input type="hidden" value="create" name="add">
                                    <div class="form-group">
                                        <label for="firstName">First Name:</label>
                                        <input type="text" class="form-control" name="firstName" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="lastName">Last Name:</label>
                                        <input type="text" class="form-control" name="lastName" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="email">Email:</label>
                                        <input type="email" class="form-control" name="email" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Password:</label>
                                        <input type="text" class="form-control" name="password" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="role">Role:</label>
                                        <select class="form-control" name="role" required>
                                            <option value="USER">User</option>
                                            <option value="MANAGER">Manager</option>
                                        </select>
                                    </div>
                                    <button type="submit" class="btn btn-success">Add User</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Update User Modal -->
                <div class="modal fade" id="updateUserModal" tabindex="-1" role="dialog" aria-labelledby="updateUserModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="updateUserModalLabel">Update User</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form id="updateUserForm" action="users" method="post">
                                    <input type="hidden" value="update" name="action">
                                    <input type="hidden" id="updateUserId" name="id">
                                    <div class="form-group">
                                        <label for="updateFirstName">First Name:</label>
                                        <input type="text" class="form-control" id="updateFirstName" name="firstName" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateLastName">Last Name:</label>
                                        <input type="text" class="form-control" id="updateLastName" name="lastName" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateEmail">Email:</label>
                                        <input type="email" class="form-control" id="updateEmail" name="email" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="updatePassword">Password:</label>
                                        <input type="text" class="form-control" id="updatePassword" name="password" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateRole">Role:</label>
                                        <select class="form-control" id="updateRole" name="role" required>
                                            <option value="USER">User</option>
                                            <option value="MANAGER">Manager</option>
                                        </select>
                                    </div>
                                    <button type="submit" class="btn btn-primary">Update User</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Delete Confirmation Modal -->
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
                                <p>Are you sure you want to delete <span id="deleteUserName"></span>?</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <form id="deleteForm" action="users" method="post">
                                    <input type="hidden" name="id" id="deleteUserId">
                                    <input type="hidden" value="delete" name="action">
                                    <button type="submit" class="btn btn-danger">Delete</button>
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
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    function confirmDelete(userId, userName) {
        document.getElementById('deleteUserId').value = userId;
        document.getElementById('deleteUserName').textContent = userName;
        $('#confirmDeleteModal').modal('show');
    }

    function openUpdateModal(id, firstName, lastName, email, password, role) {
        document.getElementById('updateUserId').value = id;
        document.getElementById('updateFirstName').value = firstName;
        document.getElementById('updateLastName').value = lastName;
        document.getElementById('updateEmail').value = email;
        document.getElementById('updatePassword').value = password;
        document.getElementById('updateRole').value = role;
        $('#updateUserModal').modal('show');
    }
</script>

</body>
</html>

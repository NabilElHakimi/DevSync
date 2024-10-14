<style>
    .sidebar {
        height: 100vh;
        background-color: #f8f9fa; /* Light background color */
        padding: 20px; /* Padding inside the sidebar */
        box-shadow: 2px 0 5px rgba(0,0,0,0.1); /* Subtle shadow effect */
        position: fixed; /* Keep the sidebar fixed */
        top: 0; /* Align it to the top */
        left: 0; /* Align it to the left */
        width: 250px; /* Fixed width */
        overflow-y: auto; /* Allow scrolling if needed */
    }

    .sidebar a {
        display: block; /* Block display for links */
        padding: 10px; /* Padding for links */
        color: #333; /* Text color */
        text-decoration: none; /* Remove underline from links */
        margin-bottom: 10px; /* Space between links */
        border-radius: 5px; /* Rounded corners */
        transition: background-color 0.3s; /* Smooth transition for hover */
    }

    .sidebar a:hover {
        background-color: #e2e6ea; /* Background color on hover */
    }

    /* Body padding to prevent content overlap */
    body {
        padding-left: 250px; /* Same as sidebar width */
    }

    /* Main content styling */
    main {
        padding: 20px; /* Padding for main content */
    }
</style>



<nav class="col-md-2 sidebar bg-primary text-white p-3 fixed-top h-100">
<h4 class="mb-4">Navigation</h4>
    <a href="manager" class="d-block py-2 text-white rounded transition duration-200 border-b border-transparent hover:bg-black hover:text-black hover:border-b hover:border-white">Users</a>
    <a href="tasks" class="d-block py-2 text-white rounded transition duration-200 border-b border-transparent hover:bg-black hover:text-black hover:border-b hover:border-white">Tasks</a>
    <a href="tags" class="d-block py-2 text-white rounded transition duration-200 border-b border-transparent hover:bg-black hover:text-black hover:border-b hover:border-white">Tags</a>
    <a href="users" class="d-block py-2 text-white rounded transition duration-200 border-b border-transparent hover:bg-black hover:text-black hover:border-b hover:border-white">Log out</a>
</nav>

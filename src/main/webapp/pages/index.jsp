<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Management Test</title>
</head>
<body>
<h2>User Management Test</h2>


<h3>Find All Users</h3>
<button onclick="findAllUsers()">Find All Users</button>

<h3>Save User</h3>
<form action="${pageContext.request.contextPath}/admin/save" method="post">
    Username: <input type="text" name="userName"><br>
    Password: <input type="password" name="password"><br>
    <input type="submit" value="Save User">
</form>

<h3>Delete User</h3>
<form action="${pageContext.request.contextPath}/admin/deleteUser" method="post">
    User ID to delete: <input type="text" name="userId"><br>
    <input type="submit" value="Delete User">
</form>

<h3>Update User</h3>
<form action="${pageContext.request.contextPath}/admin/updateUser" method="post">
    User ID to update: <input type="text" name="userId"><br>
    New Username: <input type="text" name="userName"><br>
    New Password: <input type="password" name="password"><br>
    <input type="submit" value="Update User">
</form>

<h3>User List</h3>
<div id="adminList"></div>


<script>
    function findAllUsers() {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "${pageContext.request.contextPath}/admin/findAll", true);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log(xhr.responseText)
                var users = JSON.parse(xhr.responseText);
                var adminList = document.getElementById("adminList");
                adminList.innerHTML = "";
                for (var i = 0; i < users.length; i++) {
                    adminList.innerHTML += "User ID: " + users[i].userId + ", Username: " + users[i].userName + "<br>";
                }
            }
        };
        xhr.send();
    }
</script>

</body>
</html>

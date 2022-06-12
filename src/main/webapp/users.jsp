<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<form method="post" action="users">
    Пользователь: <select name="userId">
    <option value="1" selected>User_ONE</option>
    <option value="2" selected>User_TWO</option>
    <option value="3">Admin</option>
</select>
    <br>
    <br>
    <button type="submit">Apply user</button>
</form>
<h2>Users</h2>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
</head>
<body>
    <h2>Login</h2>
    <form method="post" action="login">
        <label>Username: <input name="username" required /></label><br/>
        <label>Password: <input name="password" type="password" required /></label><br/>
        <button type="submit">Login</button>
    </form>
    <p style="color:red;"><%= request.getAttribute("error") == null ? "" : request.getAttribute("error") %></p>
</body>
</html>

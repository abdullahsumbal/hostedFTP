<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hostedftp.model.entity.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
    <meta charset="UTF-8">
</head>
<body>
    <h2>Welcome, <%= user.getFullName() %>!</h2>
    <p>Your username is: <%= user.getUsername() %></p>
    <p>(Data pulled from DB)</p>
    <form action="login" method="post">
        <input type="hidden" name="logout" value="true">
        <button type="submit">Logout</button>
    </form>
</body>
</html>

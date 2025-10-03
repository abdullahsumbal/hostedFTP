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
<html lang="en">
<head>
    <title>Welcome</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f4f6f8;
            margin: 0;
            padding: 0;
        }
        .navbar {
            background: #1976d2;
            color: #fff;
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .navbar h1 {
            margin: 0;
            font-size: 1.5rem;
        }
        .container {
            max-width: 400px;
            margin: 3rem auto;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            padding: 2rem;
        }
        .user-info {
            text-align: center;
        }
        .user-info h2 {
            margin-bottom: 0.5rem;
            color: #1976d2;
        }
        .user-info p {
            margin: 0.5rem 0;
            color: #333;
        }
        .logout-btn {
            display: block;
            width: 100%;
            margin-top: 2rem;
            padding: 0.75rem;
            background: #d32f2f;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            cursor: pointer;
            transition: background 0.2s;
        }
        .logout-btn:hover {
            background: #b71c1c;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>HostedFTP Portal</h1>
        <form action="login" method="post" style="margin:0;">
            <input type="hidden" name="logout" value="true">
            <button type="submit" class="logout-btn" style="width:auto; padding:0.5rem 1rem; margin:0;">Logout</button>
        </form>
    </div>
    <div class="container">
        <div class="user-info">
            <h2>Welcome, <%= user.getFullName() %>!</h2>
            <p><strong>Username:</strong> <%= user.getUsername() %></p>
            <p style="color: #888;">(Data pulled from DB)</p>
        </div>
    </div>
</body>
</html>

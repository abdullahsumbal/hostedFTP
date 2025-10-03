<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <style>
        body {
            background: #f5f6fa;
            font-family: Arial, sans-serif;
        }
        .login-container {
            max-width: 350px;
            margin: 60px auto;
            background: #fff;
            padding: 32px 24px 24px 24px;
            border-radius: 8px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.08);
        }
        h2 {
            text-align: center;
            margin-bottom: 24px;
            color: #333;
        }
        label {
            display: block;
            margin-bottom: 14px;
            color: #444;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 8px 10px;
            margin-top: 4px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            width: 100%;
            padding: 10px;
            background: #3498db;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 8px;
        }
        button:hover {
            background: #217dbb;
        }
        .error-msg {
            color: #e74c3c;
            text-align: center;
            margin-bottom: 12px;
            min-height: 18px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>
        <form method="post" action="login">
            <label>Username:
                <input name="username" type="text" required />
            </label>
            <label>Password:
                <input name="password" type="password" required />
            </label>
            <button type="submit">Login</button>
        </form>
        <div class="error-msg">
            <%= request.getAttribute("error") == null ? "" : request.getAttribute("error") %>
        </div>
    </div>
</body>
</html>

<!DOCTYPE html>
<html>
<head><title>Login</title></head>
<body>
  <h2>Login</h2>
  <form method="post" action="login">
    <label>Username: <input name="username" /></label><br/>
    <label>Password: <input name="password" type="password" /></label><br/>
    <button type="submit">Login</button>
  </form>
  <p style="color:red;"><%= request.getAttribute("error") == null ? "" : request.getAttribute("error") %></p>
</body>
</html>

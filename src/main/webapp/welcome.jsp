<%@ page import="com.hostedftp.model.entity.User" %>
<%
  User user = (User) session.getAttribute("user");
  if (user == null) { response.sendRedirect("login.jsp"); return; }
%>
<!DOCTYPE html>
<html>
<head><title>Welcome</title></head>
<body>
  <h2>Welcome, <%= user.getFullName() %>!</h2>
  <p>Your Full name is : <%= user.getFullName() %></p>
  <p>(Data pulled from DB)</p>
</body>
</html>

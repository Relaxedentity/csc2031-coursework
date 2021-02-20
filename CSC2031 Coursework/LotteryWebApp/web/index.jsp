<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Home</title>
      <style>
      </style>
      <link rel= "stylesheet" href ="style.css"/>
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/additional-methods.js"></script>
      <script src="indexValidation.js"></script>
  </head>
  <body>

  <h1>Home Page</h1>

  <form action="CreateAccount" name="CreateUser" method="post">
      <label for="firstname">First name:</label><br>
      <input type="text" id="firstname" name="firstname"><br>
      <label for="lastname">Last name:</label><br>
      <input type="text" id="lastname" name="lastname"><br>
      <label for="username">Username:</label><br>
      <input type="text" id="username" name="username"><br>
      <label for="phone">Phone No.:</label><br>
      <input type="text" id="phone" name="phone"><br>
      <label for="email">Email Address:</label><br>
      <input type="email" id="email" name="email" ><br>
      <label for="password">Password:</label><br>
      <input type="password" id="password" name="password"><br><br>
      <input type="submit" value="Submit">
  </form>

  <a href="LoginForm.jsp">Login Page</a>
  <a href="admin/admin_home.jsp">admin Page</a>
  </body>
</html>

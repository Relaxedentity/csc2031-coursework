<%--
  Created by IntelliJ IDEA.
  User: laksh
  Date: 11/6/2020
  Time: 8:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Login page</h1>

<p>


</p>



<form id="UserLoginForm" action="UserLogin" method="get">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username"><br>
    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password"><br><br>
    <label>Select Type</label>
    <select name="role">
        <option value="" selected="selected"> - select role - </option>
        <option value="admin">Admin</option>
        <option value="public">Public</option>
    </select>
    <input id="Submit" type="submit" value="Submit">

</form>


<a href="index.jsp">Home Page</a>
</body>
<script type="javascript">

    function LoginAttemptsChecker(){
        var LoginAttempts = '<%= session.getAttribute("LoginAttempts")%>';
        if(LoginAttempts!==''){
            if(LoginAttempts==='0'){
                document.getElementById("UserLoginForm").disabled = true;
                return false;
            }
            return true;
        }
        return true;
    }

</script>
</html>

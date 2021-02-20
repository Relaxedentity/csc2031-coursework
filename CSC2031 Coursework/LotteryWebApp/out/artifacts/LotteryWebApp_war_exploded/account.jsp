<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
    <link rel=" stylesheet" href="style.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.validate.min.js"></script>
    <script src="https://cdjs.cloudflare.come/ajax/libs/jquery-validate/1.19.1/jquery.min.js"></script>
    <script src="lottoValidation.js"></script>
</head>
<body>
<h1>User Account</h1>

<p><%= request.getAttribute("message") %></p>
<p><%= session.getAttribute("firstname") %></p>
<p><%= session.getAttribute("lastname") %></p>
<p><%= session.getAttribute("email") %></p>
<p><%= session.getAttribute("phone") %></p>
<p><%= session.getAttribute("username") %></p>
<p>
    <%= request.getAttribute("draws") %>
</p>

<form action="AddUserNumbers" method="post">
<h2> Input 6 integers between 0 and 60 inclusive </h2>
    <label for="firstInt">First Integer:</label><br>
    <input type="number" id="firstInt" name="firstInt" max =60><br>
    <label for="secondInt">Second Integer:</label><br>
    <input type="number" id="secondInt" name="secondInt" max =60><br>
    <label for="thirdInt">Third Integer:</label><br>
    <input type="number" id="thirdInt" name="thirdInt" max =60><br>
    <label for="fourthInt">Fourth Integer:</label><br>
    <input type="number" id="fourthInt" name="fourthInt" max =60><br>
    <label for="fifthInt">Fifth Integer:</label><br>
    <input type="number" id="fifthInt" name="fifthInt" max =60><br>
    <label for="sixthInt">Sixth Integer:</label><br>
    <input type="number" id="sixthInt" name="sixthInt" max =60><br><br>

    <input type="button" value="Generate" onclick="generateRandomNumber(this.form)"/><br><br>

    <input type="reset" value="Reset"><br><br>
    <input type="submit" value="Submit"><br><br>

</form>


<form action="GetUserNumbers" method="post">
    <input type="submit" value="Get Draws">
</form>
<form action="CompareUserNumbers" method="post">
    <input type="submit" value="Check">
</form>

<a href="index.jsp">Home Page</a>
    <script>
        var usedNum = new Array();
        function getRandomNumber(factor) {
                var num, index;
                var looking = true;
                do {
                    num = Math.floor(Math.random() * factor)
                    for (index = 0; index < usedNum.length; index++) {
                        if (usedNum[index] == num) {
                            break;
                        }
                    }
                    if (index == usedNum.length) {
                        usedNum[usedNum.length] = num;
                        looking = false;
                    }
                }while (looking) ;
                return num;
            }

            function generateRandomNumber(lotto){
                usedNum.length = 0;
                lotto.firstInt.value = getRandomNumber(59)+1;
                lotto.secondInt.value = getRandomNumber(59)+1;
                lotto.thirdInt.value = getRandomNumber(59)+1;
                lotto.fourthInt.value = getRandomNumber(59)+1;
                lotto.fifthInt.value = getRandomNumber(59)+1;
                lotto.sixthInt.value = getRandomNumber(59)+1;
        }

    </script>

</body>
</html>

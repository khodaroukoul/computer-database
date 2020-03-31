<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page isELIgnored="false" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Computer database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap -->
    <spring:url value="resources/css/login.css" var="loginCss"/>
    <link href="${loginCss}" rel="stylesheet" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/fontawesome.min.css" rel="stylesheet"/>
</head>
<body>
<!--a class="navbar-brand" href="dashboard">Login to Computer Database</a-->
<div class="main">
    <p class="sign" align="center">Sign in</p>

    <form class="form1" action="<spring:url value="/j_spring_security_check"/>" method="POST">

        <label class="label" for="username" id="username" ><b>Username</b></label>
        <input class="un " type="text" align="center" placeholder="Username" name="username"/>
        <label class="label" for="password" id="password"><b>Password</b></label>
        <input class="pass" type="password" align="center" placeholder="Password" name="password"/>

        <button class="submit" type="submit" align="center">Sign in</button>

        <div class="label" align="center" style="margin: 20px">
            <input type="checkbox" name="rememberMe"><b>Remember me</b></input>
        </div>

        <c:if test="${param.error == 'true'}">
            <div  align="center" style="color: #ff4a45; margin: 30px;">
                <b>Login Failed !!! ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</b>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>

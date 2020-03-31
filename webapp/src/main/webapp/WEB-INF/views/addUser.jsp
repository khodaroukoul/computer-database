<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page isELIgnored="false" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Computer database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8" content="">

    <!-- Bootstrap -->
    <spring:url value="resources/css/bootstrap.min.css" var="bootstrapStyle"/>
    <spring:url value="resources/css/font-awesome.css" var="fontAweSomeStyle"/>
    <spring:url value="resources/css/main.css" var="mainCss"/>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">
    <link rel="stylesheet" href="${bootstrapStyle}" media="screen"/>
    <link rel="stylesheet" href="${fontAweSomeStyle}" media="screen"/>
    <link rel="stylesheet" href="${mainCss}" media="screen"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
            <form class="form" action="addUser" method="POST">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" id="username" name="username"
                           placeholder="username" required="required">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password"
                           required="required">
                </div>
                <div class="form-group">
                    <label for="role">User Role</label>
                    <select class="form-control" id="role" name="role" required="required">
                        <option value="" selected> Select user's role</option>
                        <option value="ROLE_ADMIN">ADMIN</option>
                        <option value="ROLE_USER">USER</option>
                    </select>
                </div>
                <button class="btn btn-primary" type="submit">Create new user</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
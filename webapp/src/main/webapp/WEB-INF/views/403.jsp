<%@page contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap -->
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapStyle"/>
    <spring:url value="/resources/css/font-awesome.css" var="fontAweSomeStyle"/>
    <spring:url value="/resources/css/main.css" var="mainCss"/>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link href="${bootstrapStyle}" rel="stylesheet" media="screen"/>
    <link href="${fontAweSomeStyle}" rel="stylesheet" media="screen"/>
    <link href="${mainCss}" rel="stylesheet" media="screen"/>
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard"> Application - Computer Database </a>
    </div>

    <div>
        <a href="${pageContext.request.contextPath}/logout" style="color:white">
            <i class="fa fa-sign-out" style="font-size:24px;color:#de4e4e" aria-hidden="true"
               title="<spring:message code="label.logout"/>">
            </i>
        </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="alert alert-danger">
            <h3 style="text-align: center;">${message}</h3>
        </div>
    </div>
</section>

</body>
</html>
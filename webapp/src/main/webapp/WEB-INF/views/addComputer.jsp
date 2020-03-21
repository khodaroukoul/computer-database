<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>

<%@ page isELIgnored="false" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:message code=" ">
    <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

    <html>
    <head>
        <title>Computer database</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap -->
        <spring:url value="resources/css/bootstrap.min.css"
                    var="bootstrapStyle"/>
        <spring:url value="resources/css/font-awesome.css"
                    var="fontAweSomeStyle"/>
        <spring:url value="resources/css/main.css" var="mainCss"/>

        <link rel="stylesheet"
              href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet"
              href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>
    </head>
    <link href="${bootstrapStyle}" rel="stylesheet" media="screen"/>
    <link href="${fontAweSomeStyle}" rel="stylesheet" media="screen"/>
    <link href="${mainCss}" rel="stylesheet" media="screen"/>
    </head>
    <body>

    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer
                Database </a>
            <div class="dropdown ">
                <button class="btn btn-danger dropdown-toggle" type="button"
                        id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
                        aria-expanded="false">
                    <spring:message code="label.title"/>
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="?lang=en"><spring:message
                            code="label.english"/></a>
                    <a class="dropdown-item" href="?lang=fr"><spring:message
                            code="label.french"/></a>
                </div>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <c:if test="${not empty errorMsg}">
                        <div class="alert alert-danger" role="alert">
                            <i class="fa fa-exclamation-triangle fa-2x"></i>&nbsp;&nbsp;&nbsp;&nbsp;
                            <strong><spring:message code="label.error"/>:</strong>
                            <c:out value="${errorMsg}"/>
                        </div>
                    </c:if>
                    <h1><spring:message code="label.addComputer"/></h1>
                    <form action="addComputer" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="label.computerName"/></label> <input
                                    type="text" class="form-control" id="computerName"
                                    name="computerName" placeholder="<spring:message code="label.computerName" />"
                                    required="required">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="label.dateIntro"/></label> <input
                                    type="text" class="form-control" id="introduced"
                                    name="introduced" placeholder="<spring:message code="label.dateIntro" />">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="label.dateDisc"/></label> <input
                                    type="text" class="form-control" id="discontinued"
                                    name="discontinued" placeholder="<spring:message code="label.dateDisc" />">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="label.company"/></label> <select
                                    class="form-control" id="companyId" name="company"
                                    style="height: calc(2.25rem + 10px);">
                                <option value=" " selected><spring:message code="label.noCompany"/></option>
                                <c:forEach items="${companies}" var="company">
                                    <option value="${company.id},${company.name}">${company.name}</option>
                                </c:forEach>
                            </select>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="label.add" />" class="btn btn-primary">

                            <a href="dashboard" class="btn btn-default"><spring:message code="label.cancel"/></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>

    <spring:url value="resources/js/jquery.min.js" var="jqueryMinJs"/>
    <spring:url value="resources/js/bootstrap.min.js" var="bootsrapJs"/>
    <spring:url value="resources/js/dashboard.js" var="dashboardJs"/>

    <script src="${dashboardJs }"></script>
    <script src="${jqueryMinJs }"></script>
    <script src="${bootsrapJs }"></script>

    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="resources/js/frontValidation.js"></script>

    </body>
    </html>
</spring:message>
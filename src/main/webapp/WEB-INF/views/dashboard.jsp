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
        <meta charset="utf-8">

        <!-- Bootstrap -->
        <spring:url value="resources/css/bootstrap.min.css"
                    var="bootstrapStyle"/>
        <spring:url value="resources/css/font-awesome.css"
                    var="fontAweSomeStyle"/>
        <spring:url value="resources/css/main.css" var="mainCss"/>

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
            <c:if test="${not empty successMsg}">
                <div class="alert alert-success" role="alert">
                    <i class="fa fa-check fa-2x"></i> &nbsp;&nbsp;&nbsp;&nbsp;<strong><spring:message
                        code="label.success"/>:</strong>
                    <c:out value="${successMsg}"/>
                </div>
            </c:if>
            <c:if test="${not empty errorMsg}">
                <div class="alert alert-danger" role="alert">
                    <i class="fa fa-exclamation-triangle fa-2x"></i>
                    &nbsp;&nbsp;&nbsp;&nbsp;<strong><spring:message
                        code="label.error"/>:</strong>
                    <c:out value="${errorMsg}"/>
                </div>
            </c:if>
            <h1 id="homeTitle">${noOfComputers}&nbsp;<spring:message
                    code="label.computerNumber"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search"
                               class="form-control"
                               placeholder="<spring:message code="label.searchName" />"/> <input
                            type="submit" id="searchsubmit"
                            value="<spring:message code="label.filterByName" />"
                            class="btn btn-primary"/>
                    </form>
                </div>

                <div class="pull-left" style="margin-left: 30px;">
                    <form id="searchFormCompany" action="#" method="GET" class="form-inline">
                        <input type="search" id="searchboxCompany" name="searchCompany"
                               class="form-control"
                               placeholder="<spring:message code="label.searchCompanyId" />"/>
                        <input type="submit" id="searchsubmitCompany"
                               value="<spring:message code="label.deleteCompnay" />"
                               class="btn btn-primary"/>
                    </form>
                </div>

                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer"><spring:message
                            code="label.addComputer"/></a> <a class="btn btn-default"
                                                              id="editComputer" href="#"
                                                              onclick="$.fn.toggleEditMode();"><spring:message
                        code="label.edit"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="deleteComputer" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                <tr>
                    <!-- Variable declarations for passing labels as parameters -->
                    <!-- Table header for Computer Name -->

                    <th class="editMode" style="width: 60px; height: 22px;"><input
                            type="checkbox" id="selectall"/> <span
                            style="vertical-align: top;"> - <a href="#"
                                                               id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
                            class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>

                    <th><a
                            href="dashboard?order=computer&search=${search}&currentPage=${previousPage}&computersPerPage=${computersPerPage}"
                            onclick=""><spring:message code="label.computerName"/></a></th>
                    <th><a
                            href="dashboard?order=introduced&search=${search}&currentPage=${previousPage}&computersPerPage=${computersPerPage}"
                            onclick=""><spring:message code="label.dateIntro"/></a></th>
                    <th><a
                            href="dashboard?order=discontinued&search=${search}&currentPage=${previousPage}&computersPerPage=${computersPerPage}"
                            onclick=""><spring:message code="label.dateDisc"/></a></th>
                    <th><a
                            href="dashboard?order=company&search=${search}&currentPage=${previousPage}&computersPerPage=${computersPerPage}"
                            onclick=""><spring:message code="label.company"/></a></th>
                </tr>
                </thead>
                <!-- Browse attribute computers -->
                <c:forEach items="${computers}" var="computer">
                    <tbody id="results">
                    <tr>
                        <td class="editMode"><input type="checkbox" name="cb"
                                                    class="cb" value="${computer.id}"></td>
                        <td><a href="editComputer?computerId=${computer.id}"
                               onclick="">${computer.name}</a></td>
                        <td>${computer.introduced}</td>
                        <td>${computer.discontinued}</td>
                        <td>${computer.company.name}</td>
                    </tr>
                    </tbody>
                </c:forEach>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li><a
                        href="dashboard?currentPage=${previousPage}&computersPerPage=${computersPerPage}&search=${search}&order=${order}"
                        aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
                </a></li>

                <c:forEach begin="${pageBegin}" end="${pageEnd}" var="i">
                    <li><a
                            href="dashboard?currentPage=${i}&computersPerPage=${computersPerPage}&search=${search}&order=${order}">${i}</a>
                    </li>
                </c:forEach>
                <li><a
                        href="dashboard?currentPage=${nextPage}&computersPerPage=${computersPerPage}&search=${search}&order=${order}"
                        aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                </a></li>
            </ul>

            <div class="btn-group btn-group-sm pull-right" role="group">
                <button type="button" class="btn btn-default"
                        onclick="location.href='dashboard?computersPerPage=10&search=${search}&order=${order}'">
                    10
                </button>
                <button type="button" class="btn btn-default"
                        onclick="location.href='dashboard?computersPerPage=50&search=${search}&order=${order}'">
                    50
                </button>
                <button type="button" class="btn btn-default"
                        onclick="location.href='dashboard?computersPerPage=100&search=${search}&order=${order}'">
                    100
                </button>
            </div>
        </div>
    </footer>

    <spring:url value="resources/js/jquery.min.js" var="jqueryMinJs"/>
    <spring:url value="resources/js/bootstrap.min.js" var="bootsrapJs"/>
    <spring:url value="resources/js/dashboard.js" var="dashboardJs"/>

    <script src="${jqueryMinJs}"></script>
    <script src="${bootsrapJs}"></script>
    <script src="${dashboardJs}"></script>
    </body>
    </html>
</spring:message>
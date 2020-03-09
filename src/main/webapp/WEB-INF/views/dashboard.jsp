<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboardCli"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<c:if test="${not empty successMsg}">
				<div class="alert alert-success" role="alert">
					<i class="fa fa-check fa-2x"></i> &nbsp;&nbsp;&nbsp;&nbsp;<strong>Success:
					</strong>
					<c:out value="${successMsg}" />
				</div>
			</c:if>
			<c:if test="${not empty errorMsg}">
				<div class="alert alert-danger" role="alert">
					<i class="fa fa-exclamation-triangle fa-2x"></i> &nbsp;&nbsp;&nbsp;&nbsp;<strong>Success:
					</strong>
					<c:out value="${errorMsg}" />
				</div>
			</c:if>
			<h1 id="homeTitle">${noOfPcs}&nbsp;Computers&nbsp;found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>

				<div class="pull-left" style="margin-left: 30px;">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchboxCompany" name="searchCompany"
							class="form-control" placeholder="Search id" /> <input
							type="submit" id="searchsubmitCompany"
							value="Delete company by id" class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>

						<th><a href="dashboardCli?order=computer&search=${search}&currentPage=${previousPage}&pcsPerPage=${pcsPerPage}"
							onclick="">Computer Name</a></th>
						<th><a href="dashboardCli?order=introduced&search=${search}&currentPage=${previousPage}&pcsPerPage=${pcsPerPage}"
							onclick="">Introduced Date</a></th>
						<th><a
							href="dashboardCli?order=discontinued&search=${search}&currentPage=${previousPage}&pcsPerPage=${pcsPerPage}"
							onclick="">Discontinued date</a></th>
						<th><a href="dashboardCli?order=company&search=${search}&currentPage=${previousPage}&pcsPerPage=${pcsPerPage}"
							onclick="">Company</a></th>
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
					href="dashboardCli?currentPage=${previousPage}&pcsPerPage=${pcsPerPage}&search=${search}&order=${order}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>

				<c:forEach begin="${pageBegin}" end="${pageEnd}" var="i">
					<li><a
						href="dashboardCli?currentPage=${i}&pcsPerPage=${pcsPerPage}&search=${search}&order=${order}">${i}</a></li>
				</c:forEach>
				<li><a
					href="dashboardCli?currentPage=${nextPage}&pcsPerPage=${pcsPerPage}&search=${search}&order=${order}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" class="btn btn-default"
					onclick="location.href='dashboardCli?pcsPerPage=10&search=${search}&order=${order}'">
					10</button>
				<button type="button" class="btn btn-default"
					onclick="location.href='dashboardCli?pcsPerPage=50&search=${search}&order=${order}'">
					50</button>
				<button type="button" class="btn btn-default"
					onclick="location.href='dashboardCli?pcsPerPage=100&search=${search}&order=${order}'">
					100</button>
			</div>
		</div>
	</footer>

	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>
</body>
</html>
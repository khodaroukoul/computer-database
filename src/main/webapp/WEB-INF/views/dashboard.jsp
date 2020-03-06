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
			<h1 id="homeTitle">${noOfRecords}&nbsp;Computers&nbsp;found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				
				<div class="pull-left" style="margin-left:30px;">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchboxCompany" name="searchCompany"
							class="form-control" placeholder="Search id" /> <input
							type="submit" id="searchsubmitCompany" value="Delete company by id"
							class="btn btn-primary" />
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

						<th><a href="dashboardCli?order=computer&search=${search}"
							onclick="">Computer Name</a></th>
						<th><a href="dashboardCli?order=introduced&search=${search}"
							onclick="">Introduced Date</a></th>
						<th><a
							href="dashboardCli?order=discontinued&search=${search}"
							onclick="">Discontinued date</a></th>
						<th><a href="dashboardCli?order=company&search=${search}"
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
				<c:if test="${currentPage != 1}">
					<li><a
						href="dashboardCli?page=${currentPage-1}&recordsPerPage=${recordsPerPage}&search=${search}&order=${order}"
						aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
					</a></li>
				</c:if>

				<%-- current page (1-based) --%>
				<c:set var="p" value="${currentPage}" />

				<%-- amount of page links to be displayed --%>
				<c:set var="l" value="6" />

				<%-- minimum link range ahead/behind --%>
				<c:set var="r" value="${l / 2}" />

				<%-- total amount of pages --%>
				<c:set var="t" value="${noOfPages}" />

				<c:set var="begin"
					value="${((p - r) > 0 ? ((p - r) < (t - l + 1) ? (p - r) : (t - l)) : 0) + 1}" />

				<c:set var="end"
					value="${(p + r) < t ? ((p + r) > l ? (p + r) : l) : t}" />

				<c:forEach begin="${begin}" end="${end}" var="i">
					<li><a
						href="dashboardCli?page=${i}&recordsPerPage=${recordsPerPage}&search=${search}&order=${order}">${i}</a></li>
				</c:forEach>

				<c:if test="${currentPage lt noOfPages}">
					<li><a
						href="dashboardCli?page=${currentPage+1}&recordsPerPage=${recordsPerPage}&search=${search}&order=${order}"
						aria-label="Next"> <span aria-hidden="true">&raquo;</span>
					</a></li>
				</c:if>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" class="btn btn-default"
					onclick="location.href='dashboardCli?recordsPerPage=10&search=${search}&order=${order}'">
					10</button>
				<button type="button" class="btn btn-default"
					onclick="location.href='dashboardCli?recordsPerPage=50&search=${search}&order=${order}'">
					50</button>
				<button type="button" class="btn btn-default"
					onclick="location.href='dashboardCli?recordsPerPage=100&search=${search}&order=${order}'">
					100</button>
			</div>
		</div>
	</footer>

	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>
</body>
</html>
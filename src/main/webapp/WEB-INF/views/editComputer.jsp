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
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application -
				Computer Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<c:if test="${not empty errorMsg}">
						<div class="alert alert-danger" role="alert">
							<i class="fa fa-exclamation-triangle fa-2x"></i>&nbsp;&nbsp;&nbsp;&nbsp;<strong>Error:
							</strong>
							<c:out value="${errorMsg}" />
						</div>
					</c:if>
					<div class="label label-default pull-right">id: ${computer.id}</div>
					<h1>Edit Computer</h1>

					<form action="editComputer" method="POST">
						<input type="hidden" value="${computer.id}" id="id"
							name="computerId" />
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" value="${computer.name}"
									required="required">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="text" class="form-control" id="introduced"
									name="introduced" value="${computer.introduced}">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="text" class="form-control" id="discontinued"
									name="discontinued" value="${computer.discontinued}">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<c:choose>
										<c:when test="${empty computer.company.name}">
											<option value="" selected>No Company</option>
										</c:when>
										<c:otherwise>
											<option value="${computer.company.id}" selected>${computer.company.name}
										</c:otherwise>
									</c:choose>
									<c:if test="${not empty computer.company.name}">
										<option value="">No Company</option>
									</c:if>
									<c:forEach items="${companies}" var="company">
										<c:if test="${company.name != computer.company.name}">
											<option value="${company.id}">${company.name}</option>
										</c:if>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							<a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="resources/js/frontValidation.js"></script>

</body>
</html>
<%@page import="in.co.rays.project_3.controller.EmployeeCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee View</title>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;
}
</style>

</head>

<body class="hm">

<div class="header">
	<%@include file="Header.jsp"%>
</div>

<main>
<form action="<%=ORSView.EMPLOYEE_CTL%>" method="post">

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.EmployeeDTO"
	scope="request"></jsp:useBean>

<div class="row pt-3">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto.getEmployeeCode() != null && id > 0) {
%>
	<h3 class="text-center text-primary">Update Employee</h3>
<%
	} else {
%>
	<h3 class="text-center text-primary">Add Employee</h3>
<%
	}
%>

<!-- Success Message -->
<h4 align="center">
<%
	if (!ServletUtility.getSuccessMessage(request).equals("")) {
%>
<div class="alert alert-success">
	<%=ServletUtility.getSuccessMessage(request)%>
</div>
<%
	}
%>
</h4>

<!-- Error Message -->
<h4 align="center">
<%
	if (!ServletUtility.getErrorMessage(request).equals("")) {
%>
<div class="alert alert-danger">
	<%=ServletUtility.getErrorMessage(request)%>
</div>
<%
	}
%>
</h4>

<!-- Hidden Fields -->
<input type="hidden" name="id" value="<%=dto.getId()%>">
<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">
<input type="hidden" name="createdDatetime"
	value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
<input type="hidden" name="modifiedDatetime"
	value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<!-- Employee Code -->
<div class="form-group">
<label><b>Employee Code *</b></label>
<input type="text" name="employeeCode"
	class="form-control"
	placeholder="Employee Code"
	value="<%=DataUtility.getStringData(dto.getEmployeeCode())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("employeeCode", request)%>
</font>
</div>

<!-- Employee Name -->
<div class="form-group">
<label><b>Employee Name *</b></label>
<input type="text" name="employeeName"
	class="form-control"
	placeholder="Employee Name"
	value="<%=DataUtility.getStringData(dto.getEmployeeName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("employeeName", request)%>
</font>
</div>

<!-- Department -->
<div class="form-group">
<label><b>Department *</b></label>
<input type="text" name="department"
	class="form-control"
	placeholder="Department"
	value="<%=DataUtility.getStringData(dto.getDepartment())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("department", request)%>
</font>
</div>

<!-- Email -->
<div class="form-group">
<label><b>Email *</b></label>
<input type="text" name="email"
	class="form-control"
	placeholder="Email"
	value="<%=DataUtility.getStringData(dto.getEmail())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("email", request)%>
</font>
</div>

<!-- Designation -->
<div class="form-group">
<label><b>Designation *</b></label>
<input type="text" name="designation"
	class="form-control"
	placeholder="Designation"
	value="<%=DataUtility.getStringData(dto.getDesignation())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("designation", request)%>
</font>
</div>

<!-- Buttons -->
<div class="text-center">

<%
	if (dto.getEmployeeCode() != null && id > 0) {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=EmployeeCtl.OP_UPDATE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=EmployeeCtl.OP_CANCEL%>">

<%
	} else {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=EmployeeCtl.OP_SAVE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=EmployeeCtl.OP_RESET%>">

<%
	}
%>

</div>

</div>
</div>
</div>

<div class="col-md-4"></div>
</div>

</form>
</main>

<%@include file="FooterView.jsp"%>

</body>
</html>
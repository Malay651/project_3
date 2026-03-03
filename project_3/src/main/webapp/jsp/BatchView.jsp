<%@page import="in.co.rays.project_3.controller.BatchCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Batch View</title>

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
<form action="<%=ORSView.BATCH_CTL%>" method="post">

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.BatchDTO"
	scope="request"></jsp:useBean>

<div class="row pt-3">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto.getBatchCode() != null && id > 0) {
%>
	<h3 class="text-center text-primary">Update Batch</h3>
<%
	} else {
%>
	<h3 class="text-center text-primary">Add Batch</h3>
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

<!-- Batch Code -->
<div class="form-group">
<label><b>Batch Code *</b></label>
<input type="text" name="batchCode"
	class="form-control"
	placeholder="Batch Code"
	value="<%=DataUtility.getStringData(dto.getBatchCode())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("batchCode", request)%>
</font>
</div>

<!-- Batch Name -->
<div class="form-group">
<label><b>Batch Name *</b></label>
<input type="text" name="batchName"
	class="form-control"
	placeholder="Batch Name"
	value="<%=DataUtility.getStringData(dto.getBatchName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("batchName", request)%>
</font>
</div>

<!-- Trainer Name -->
<div class="form-group">
<label><b>Trainer Name *</b></label>
<input type="text" name="trainerName"
	class="form-control"
	placeholder="Trainer Name"
	value="<%=DataUtility.getStringData(dto.getTrainerName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("trainerName", request)%>
</font>
</div>

<!-- Batch Timing -->
<div class="form-group">
<label><b>Batch Timing *</b></label>
<input type="text" name="batchTiming"
	class="form-control"
	placeholder="Batch Timing"
	value="<%=DataUtility.getStringData(dto.getBatchTiming())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("batchTiming", request)%>
</font>
</div>

<!-- Buttons -->
<div class="text-center">

<%
	if (dto.getBatchCode() != null && id > 0) {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=BatchCtl.OP_UPDATE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=BatchCtl.OP_CANCEL%>">

<%
	} else {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=BatchCtl.OP_SAVE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=BatchCtl.OP_RESET%>">

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
<%@page import="in.co.rays.project_3.controller.JobApplicationCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Job Application View</title>

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
<form action="<%=ORSView.JOB_APPLICATION_CTL%>" method="post">

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.JobApplicationDTO"
	scope="request"></jsp:useBean>

<div class="row pt-3">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto.getApplicationName() != null && id > 0) {
%>
	<h3 class="text-center text-primary">Update Job Application</h3>
<%
	} else {
%>
	<h3 class="text-center text-primary">Add Job Application</h3>
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

<!-- Application Name -->
<div class="form-group">
<label><b>Application Name *</b></label>
<input type="text" name="applicationName"
	class="form-control"
	placeholder="Application Name"
	value="<%=DataUtility.getStringData(dto.getApplicationName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("applicationName", request)%>
</font>
</div>

<!-- Job Title -->
<div class="form-group">
<label><b>Job Title *</b></label>
<input type="text" name="jobTitle"
	class="form-control"
	placeholder="Job Title"
	value="<%=DataUtility.getStringData(dto.getJobTitle())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("jobTitle", request)%>
</font>
</div>

<!-- Resume Link -->
<div class="form-group">
<label><b>Resume Link *</b></label>
<input type="text" name="resumeLink"
	class="form-control"
	placeholder="Resume Link"
	value="<%=DataUtility.getStringData(dto.getResumeLink())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("resumeLink", request)%>
</font>
</div>

<!-- Application Date -->
<div class="form-group">
<label><b>Application Date *</b></label>
<input type="date" name="applicationDate"
	class="form-control"
	value="<%=DataUtility.getDateString(dto.getApplicationDate())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("applicationDate", request)%>
</font>
</div>

<!-- Interview Date -->
<div class="form-group">
<label><b>Interview Date</b></label>
<input type="date" name="interviewDate"
	class="form-control"
	value="<%=DataUtility.getDateString(dto.getInterviewDate())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("interviewDate", request)%>
</font>
</div>

<!-- Application Status -->
<div class="form-group">
<label><b>Application Status *</b></label>
<select name="applicationStatus" class="form-control">
	<option value="">--Select--</option>
	<option value="Pending"
		<%= "Pending".equals(dto.getApplicationStatus()) ? "selected" : "" %>>
		Pending
	</option>
	<option value="Interview Scheduled"
		<%= "Interview Scheduled".equals(dto.getApplicationStatus()) ? "selected" : "" %>>
		Interview Scheduled
	</option>
	<option value="Selected"
		<%= "Selected".equals(dto.getApplicationStatus()) ? "selected" : "" %>>
		Selected
	</option>
	<option value="Rejected"
		<%= "Rejected".equals(dto.getApplicationStatus()) ? "selected" : "" %>>
		Rejected
	</option>
</select>
<font color="red">
<%=ServletUtility.getErrorMessage("applicationStatus", request)%>
</font>
</div>

<!-- Buttons -->
<div class="text-center">

<%
	if (dto.getApplicationName() != null && id > 0) {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=JobApplicationCtl.OP_UPDATE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=JobApplicationCtl.OP_CANCEL%>">

<%
	} else {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=JobApplicationCtl.OP_SAVE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=JobApplicationCtl.OP_RESET%>">

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
<%@page import="in.co.rays.project_3.controller.DonationCampCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Donation Camp View</title>

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
<form action="<%=ORSView.DONATIONCAMP_CTL%>" method="post">

<jsp:useBean id="dto"
class="in.co.rays.project_3.dto.DonationCampDTO"
scope="request"></jsp:useBean>

<div class="row pt-3">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card">
<div class="card-body">

<%
long id = DataUtility.getLong(request.getParameter("id"));
if (dto.getCampName() != null && id > 0) {
%> <h3 class="text-center text-primary">Update Donation Camp</h3>
<%
} else {
%> <h3 class="text-center text-primary">Add Donation Camp</h3>
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

<!-- Camp Name -->

<div class="form-group">
<label><b>Camp Name *</b></label>
<input type="text" name="campName"
	class="form-control"
	placeholder="Camp Name"
	value="<%=DataUtility.getStringData(dto.getCampName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("campName", request)%>
</font>
</div>

<!-- Camp Date -->

<div class="form-group">
<label><b>Camp Date *</b></label>
<input type="date" name="campDate"
	class="form-control"
	value="<%=DataUtility.getDateString(dto.getCampDate())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("campDate", request)%>
</font>
</div>

<!-- Organizer -->

<div class="form-group">
<label><b>Organizer *</b></label>
<input type="text" name="organizer"
	class="form-control"
	placeholder="Organizer"
	value="<%=DataUtility.getStringData(dto.getOrganizer())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("organizer", request)%>
</font>
</div>

<!-- Buttons -->

<div class="text-center">

<%
if (dto.getCampName() != null && id > 0) {
%>

<input type="submit" name="operation"
class="btn btn-success"
value="<%=DonationCampCtl.OP_UPDATE%>">

<input type="submit" name="operation"
class="btn btn-warning"
value="<%=DonationCampCtl.OP_CANCEL%>">

<%
} else {
%>

<input type="submit" name="operation"
class="btn btn-success"
value="<%=DonationCampCtl.OP_SAVE%>">

<input type="submit" name="operation"
class="btn btn-warning"
value="<%=DonationCampCtl.OP_RESET%>">

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

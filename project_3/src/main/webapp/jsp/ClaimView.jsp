<%@page import="in.co.rays.project_3.controller.ClaimCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Claim View</title>

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
<form action="<%=ORSView.CLAIM_CTL%>" method="post">

<jsp:useBean id="dto"
class="in.co.rays.project_3.dto.ClaimDTO"
scope="request"></jsp:useBean>

<div class="row pt-3">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card">
<div class="card-body">

<%
long id = DataUtility.getLong(request.getParameter("id"));
if (dto.getClaimNumber() != null && id > 0) {
%> <h3 class="text-center text-primary">Update Claim</h3>
<%
} else {
%> <h3 class="text-center text-primary">Add Claim</h3>
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

<!-- Claim Number -->

<div class="form-group">
<label><b>Claim Number *</b></label>
<input type="text" name="claimNumber"
	class="form-control"
	placeholder="Claim Number"
	value="<%=DataUtility.getStringData(dto.getClaimNumber())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("claimNumber", request)%>
</font>
</div>

<!-- Claim Amount -->

<div class="form-group">
<label><b>Claim Amount *</b></label>
<input type="text" name="claimAmount"
	class="form-control"
	placeholder="Claim Amount"
	value="<%=DataUtility.getStringData(dto.getClaimAmount())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("claimAmount", request)%>
</font>
</div>

<!-- Status -->

<div class="form-group">
<label><b>Status *</b></label>
<input type="text" name="status"
	class="form-control"
	placeholder="Status"
	value="<%=DataUtility.getStringData(dto.getStatus())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("status", request)%>
</font>
</div>

<!-- Buttons -->

<div class="text-center">

<%
if (dto.getClaimNumber() != null && id > 0) {
%>

<input type="submit" name="operation"
 class="btn btn-success"
 value="<%=ClaimCtl.OP_UPDATE%>">

<input type="submit" name="operation"
 class="btn btn-warning"
 value="<%=ClaimCtl.OP_CANCEL%>">

<%
} else {
%>

<input type="submit" name="operation"
 class="btn btn-success"
 value="<%=ClaimCtl.OP_SAVE%>">

<input type="submit" name="operation"
 class="btn btn-warning"
 value="<%=ClaimCtl.OP_RESET%>">

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

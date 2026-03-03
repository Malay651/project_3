<%@page import="in.co.rays.project_3.controller.LibraryCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Library View</title>

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
<form action="<%=ORSView.LIBRARY_CTL%>" method="post">

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.LibraryDTO"
	scope="request"></jsp:useBean>

<div class="row pt-3">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto.getBookCode() != null && id > 0) {
%>
	<h3 class="text-center text-primary">Update Book</h3>
<%
	} else {
%>
	<h3 class="text-center text-primary">Add Book</h3>
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

<!-- Book Code -->
<div class="form-group">
<label><b>Book Code *</b></label>
<input type="text" name="bookCode"
	class="form-control"
	placeholder="Book Code"
	value="<%=DataUtility.getStringData(dto.getBookCode())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("bookCode", request)%>
</font>
</div>

<!-- Book Name -->
<div class="form-group">
<label><b>Book Name *</b></label>
<input type="text" name="bookName"
	class="form-control"
	placeholder="Book Name"
	value="<%=DataUtility.getStringData(dto.getBookName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("bookName", request)%>
</font>
</div>

<!-- Author Name -->
<div class="form-group">
<label><b>Author Name *</b></label>
<input type="text" name="authorName"
	class="form-control"
	placeholder="Author Name"
	value="<%=DataUtility.getStringData(dto.getAuthorName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("authorName", request)%>
</font>
</div>

<!-- Availability -->
<div class="form-group">
<td>Availability Status</td>

<select name="availabilityStatus" class="form-control">
    <option value="Available"
        <%= "Available".equals(dto.getAvailabilityStatus()) ? "selected" : "" %>>
        Available
    </option>

    <option value="Issued"
        <%= "Issued".equals(dto.getAvailabilityStatus()) ? "selected" : "" %>>
        Issued
    </option>

    <option value="Reserved"
        <%= "Reserved".equals(dto.getAvailabilityStatus()) ? "selected" : "" %>>
        Reserved
    </option>
</select>
</td>
<!-- Buttons -->
<div class="text-center">

<%
	if (dto.getBookCode() != null && id > 0) {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=LibraryCtl.OP_UPDATE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=LibraryCtl.OP_CANCEL%>">

<%
	} else {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=LibraryCtl.OP_SAVE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=LibraryCtl.OP_RESET%>">

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
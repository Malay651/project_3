<%@page import="in.co.rays.project_3.controller.StockCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Stock View</title>

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
<form action="<%=ORSView.STOCK_CTL%>" method="post">

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.StockDTO"
	scope="request"></jsp:useBean>

<div class="row pt-3">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto.getStockName() != null && id > 0) {
%>
	<h3 class="text-center text-primary">Update Stock</h3>
<%
	} else {
%>
	<h3 class="text-center text-primary">Add Stock</h3>
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

<!-- Stock Name -->
<div class="form-group">
<label><b>Stock Name *</b></label>
<input type="text" name="stockName"
	class="form-control"
	placeholder="Stock Name"
	value="<%=DataUtility.getStringData(dto.getStockName())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("stockName", request)%>
</font>
</div>

<!-- Price -->
<div class="form-group">
<label><b>Price *</b></label>
<input type="text" name="price"
	class="form-control"
	placeholder="Price"
	value="<%=DataUtility.getStringData(dto.getPrice())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("price", request)%>
</font>
</div>

<!-- Quantity -->
<div class="form-group">
<label><b>Quantity *</b></label>
<input type="number" name="quantity"
	class="form-control"
	placeholder="Quantity"
	value="<%=DataUtility.getStringData(dto.getQuantity())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("quantity", request)%>
</font>
</div>

<!-- Buttons -->
<div class="text-center">

<%
	if (dto.getStockName() != null && id > 0) {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=StockCtl.OP_UPDATE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=StockCtl.OP_CANCEL%>">

<%
	} else {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=StockCtl.OP_SAVE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=StockCtl.OP_RESET%>">

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
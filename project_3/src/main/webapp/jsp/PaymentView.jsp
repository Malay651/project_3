<%@page import="in.co.rays.project_3.controller.PaymentCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment View</title>

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
<form action="<%=ORSView.PAYMENT_CTL%>" method="post">

<jsp:useBean id="dto"
	class="in.co.rays.project_3.dto.PaymentDTO"
	scope="request"></jsp:useBean>

<div class="row pt-3">
<div class="col-md-4"></div>

<div class="col-md-4">
<div class="card">
<div class="card-body">

<%
	long id = DataUtility.getLong(request.getParameter("id"));
	if (dto.getPaymentCode() != null && id > 0) {
%>
	<h3 class="text-center text-primary">Update Payment</h3>
<%
	} else {
%>
	<h3 class="text-center text-primary">Add Payment</h3>
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

<!-- Payment Code -->
<div class="form-group">
<label><b>Payment Code *</b></label>
<input type="text" name="paymentCode"
	class="form-control"
	placeholder="Payment Code"
	value="<%=DataUtility.getStringData(dto.getPaymentCode())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("paymentCode", request)%>
</font>
</div>

<!-- Amount -->
<div class="form-group">
<label><b>Amount *</b></label>
<input type="number" name="amount"
	class="form-control"
	placeholder="Amount"
	value="<%=DataUtility.getStringData(dto.getAmount())%>">
<font color="red">
<%=ServletUtility.getErrorMessage("amount", request)%>
</font>
</div>

<!-- Payment Mode -->
<div class="form-group">
<label><b>Payment Mode *</b></label>
<select name="paymentMode" class="form-control">

	<option value="">--Select--</option>

	<option value="Cash"
		<%= "Cash".equals(dto.getPaymentMode()) ? "selected" : "" %>>
		Cash
	</option>

	<option value="Card"
		<%= "Card".equals(dto.getPaymentMode()) ? "selected" : "" %>>
		Card
	</option>

	<option value="UPI"
		<%= "UPI".equals(dto.getPaymentMode()) ? "selected" : "" %>>
		UPI
	</option>

	<option value="Net Banking"
		<%= "Net Banking".equals(dto.getPaymentMode()) ? "selected" : "" %>>
		Net Banking
	</option>

</select>

<font color="red">
<%=ServletUtility.getErrorMessage("paymentMode", request)%>
</font>
</div>

<!-- Payment Date -->
<div class="form-group">
<label><b>Payment Date *</b></label>
<input type="date" name="paymentDate"
	class="form-control"
	value="<%=DataUtility.getDateString(dto.getPaymentDate())%>">

<font color="red">
<%=ServletUtility.getErrorMessage("paymentDate", request)%>
</font>
</div>

<!-- Buttons -->
<div class="text-center mt-3">

<%
	if (dto.getPaymentCode() != null && id > 0) {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=PaymentCtl.OP_UPDATE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=PaymentCtl.OP_CANCEL%>">

<%
	} else {
%>

<input type="submit" name="operation"
	class="btn btn-success"
	value="<%=PaymentCtl.OP_SAVE%>">

<input type="submit" name="operation"
	class="btn btn-warning"
	value="<%=PaymentCtl.OP_RESET%>">

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
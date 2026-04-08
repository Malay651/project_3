<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.CurrencyListCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Currency List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
	background-size: cover;
	padding-top: 85px;
}

.text {
	text-align: center;
}
</style>
</head>

<%@include file="Header.jsp"%>

<body class="hm">
<div>
<form action="<%=ORSView.CURRENCY_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.CurrencyDTO" scope="request"></jsp:useBean>

<%
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

	List list = ServletUtility.getList(request);
	Iterator<in.co.rays.project_3.dto.CurrencyDTO> it = list.iterator();
%>

<center>
	<h1><u>Currency List</u></h1>
</center>

<!-- Success Message -->
<% if (!ServletUtility.getSuccessMessage(request).equals("")) { %>
<div class="alert alert-success">
	<%=ServletUtility.getSuccessMessage(request)%>
</div>
<% } %>

<!-- Error Message -->
<% if (!ServletUtility.getErrorMessage(request).equals("")) { %>
<div class="alert alert-danger">
	<%=ServletUtility.getErrorMessage(request)%>
</div>
<% } %>

<!-- Search Fields -->
<div class="row">
	<div class="col-sm-2"></div>

	<div class="col-sm-3">
		<input type="text" name="currencyCode" placeholder="Currency Code"
			class="form-control"
			value="<%=ServletUtility.getParameter("currencyCode", request)%>">
	</div>

	<div class="col-sm-3">
		<input type="text" name="currencyName" placeholder="Currency Name"
			class="form-control"
			value="<%=ServletUtility.getParameter("currencyName", request)%>">
	</div>

	<div class="col-sm-3">
		<input type="submit" name="operation" class="btn btn-primary"
			value="<%=CurrencyListCtl.OP_SEARCH%>">
		<input type="submit" name="operation" class="btn btn-dark"
			value="<%=CurrencyListCtl.OP_RESET%>">
	</div>
</div>

<br>

<!--  Table -->
<div class="table-responsive">
<table class="table table-bordered table-dark table-hover">

<thead>
<tr>
	<th><input type="checkbox" id="select_all"> Select All</th>
	<th class="text">S.No</th>
	<th class="text">Currency Code</th>
	<th class="text">Currency Name</th>
	<th class="text">Symbol</th>
	<th class="text">Status</th>
	<th class="text">Edit</th>
</tr>
</thead>

<tbody>
<%
while (it.hasNext()) {
	dto = it.next();
%>
<tr>
	<td align="center">
		<input type="checkbox" name="ids" value="<%=dto.getId()%>">
	</td>
	<td class="text"><%=index++%></td>
	<td class="text"><%=dto.getCurrencyCode()%></td>
	<td class="text"><%=dto.getCurrencyName()%></td>
	<td class="text"><%=dto.getSymbol()%></td>
	<td class="text"><%=dto.getStatus()%></td>
	<td class="text">
		<a href="CurrencyCtl?id=<%=dto.getId()%>">Edit</a>
	</td>
</tr>
<%
}
%>
</tbody>

</table>
</div>

<!--  Pagination -->
<table width="100%">
<tr>
	<td>
		<input type="submit" name="operation" class="btn btn-warning"
			value="<%=CurrencyListCtl.OP_PREVIOUS%>"
			<%=pageNo > 1 ? "" : "disabled"%>>
	</td>

	<td>
		<input type="submit" name="operation" class="btn btn-primary"
			value="<%=CurrencyListCtl.OP_NEW%>">
	</td>

	<td>
		<input type="submit" name="operation" class="btn btn-danger"
			value="<%=CurrencyListCtl.OP_DELETE%>">
	</td>

	<td align="right">
		<input type="submit" name="operation" class="btn btn-warning"
			value="<%=CurrencyListCtl.OP_NEXT%>"
			<%=(nextPageSize != 0) ? "" : "disabled"%>>
	</td>
</tr>
</table>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>
</div>

</body>

<%@include file="FooterView.jsp"%>
</html>
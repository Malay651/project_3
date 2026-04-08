<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.CurrencyCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.CurrencyDTO" scope="request"></jsp:useBean>

<html>
<head>
<title>Currency View</title>
</head>

<body>
<%@include file="Header.jsp"%>

<form action="<%=ORSView.CURRENCY_CTL%>" method="post">

<center>

<h2>
<% if (dto.getId() > 0) { %>
    Update Currency
<% } else { %>
    Add Currency
<% } %>
</h2>

<!-- Success Message -->
<font color="green">
<%=ServletUtility.getSuccessMessage(request)%>
</font>

<!-- Error Message -->
<font color="red">
<%=ServletUtility.getErrorMessage(request)%>
</font>

<input type="hidden" name="id" value="<%=dto.getId()%>">

<table>

<tr>
<td>Currency Code*</td>
<td>
<input type="text" name="currencyCode"
value="<%=DataUtility.getStringData(dto.getCurrencyCode())%>">
</td>
<td style="color:red">
<%=ServletUtility.getErrorMessage("currencyCode", request)%>
</td>
</tr>

<tr>
<td>Currency Name*</td>
<td>
<input type="text" name="currencyName"
value="<%=DataUtility.getStringData(dto.getCurrencyName())%>">
</td>
<td style="color:red">
<%=ServletUtility.getErrorMessage("currencyName", request)%>
</td>
</tr>

<tr>
<td>Symbol*</td>
<td>
<input type="text" name="symbol"
value="<%=DataUtility.getStringData(dto.getSymbol())%>">
</td>
<td style="color:red">
<%=ServletUtility.getErrorMessage("symbol", request)%>
</td>
</tr>

<tr>
<td>Status*</td>
<td>
<select name="status">
<option value="">--Select--</option>

<%
List statusList = (List) request.getAttribute("statusList");
if (statusList != null) {
    for (Object s : statusList) {
        String status = (String) s;
%>
<option value="<%=status%>" 
<%=status.equals(dto.getStatus()) ? "selected" : ""%>>
<%=status%>
</option>
<%
    }
}
%>

</select>
</td>
<td style="color:red">
<%=ServletUtility.getErrorMessage("status", request)%>
</td>
</tr>

<tr>
<td colspan="2" align="center">

<% if (dto.getId() > 0) { %>

<input type="submit" name="operation" value="<%=CurrencyCtl.OP_UPDATE%>">
<input type="submit" name="operation" value="<%=CurrencyCtl.OP_CANCEL%>">

<% } else { %>

<input type="submit" name="operation" value="<%=CurrencyCtl.OP_SAVE%>">
<input type="submit" name="operation" value="<%=CurrencyCtl.OP_RESET%>">

<% } %>

</td>
</tr>

</table>

</center>

</form>

<%@include file="FooterView.jsp"%>

</body>
</html>
<%@page import="in.co.rays.project_3.controller.TourGuideBookingCtl"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Tour Guide Booking</title>

<style>
.hm{
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top:75px;
}
</style>

</head>

<body class="hm">

<div class="header">
<%@include file="Header.jsp"%>
</div>

<main>

<form action="<%=ORSView.TOUR_GUIDE_BOOKING_CTL%>" method="post">

<jsp:useBean id="dto"
class="in.co.rays.project_3.dto.TourGuideBookingDTO"
scope="request"></jsp:useBean>

<div class="row pt-3">

<div class="col-md-4"></div>

<div class="col-md-4">

<div class="card">
<div class="card-body">

<%
long id = DataUtility.getLong(request.getParameter("id"));
if(dto.getTouristName()!=null && id>0){
%>

<h3 class="text-center text-primary">Update Tour Guide Booking</h3>
<%
}else{
%>
<h3 class="text-center text-primary">Add Tour Guide Booking</h3>
<%
}
%>

<!-- Success Message -->

<h4 align="center">
<%
if(!ServletUtility.getSuccessMessage(request).equals("")){
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
if(!ServletUtility.getErrorMessage(request).equals("")){
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

<!-- Tourist Name -->

<div class="form-group">

<label><b>Tourist Name *</b></label>

<input type="text"
name="touristName"
class="form-control"
placeholder="Enter Tourist Name"
value="<%=DataUtility.getStringData(dto.getTouristName())%>">

<font color="red">
<%=ServletUtility.getErrorMessage("touristName",request)%>
</font>

</div>

<!-- Guide Name -->

<div class="form-group">

<label><b>Guide Name *</b></label>

<input type="text"
name="guideName"
class="form-control"
placeholder="Enter Guide Name"
value="<%=DataUtility.getStringData(dto.getGuideName())%>">

<font color="red">
<%=ServletUtility.getErrorMessage("guideName",request)%>
</font>

</div>

<!-- Location -->

<div class="form-group">

<label><b>Location *</b></label>

<input type="text"
name="location"
class="form-control"
placeholder="Enter Location"
value="<%=DataUtility.getStringData(dto.getLocation())%>">

<font color="red">
<%=ServletUtility.getErrorMessage("location",request)%>
</font>

</div>

<!-- Booking Date -->

<div class="form-group">

<label><b>Booking Date *</b></label>

<input type="date"
name="bookingdate"
class="form-control"
value="<%=DataUtility.getDateString(dto.getBookingdate())%>">

<font color="red">
<%=ServletUtility.getErrorMessage("bookingdate",request)%>
</font>

</div>

<!-- Buttons -->

<div class="text-center">

<%
if(dto.getTouristName()!=null && id>0){
%>

<input type="submit"
name="operation"
class="btn btn-success"
value="<%=TourGuideBookingCtl.OP_UPDATE%>">

<input type="submit"
name="operation"
class="btn btn-warning"
value="<%=TourGuideBookingCtl.OP_CANCEL%>">

<%
}else{
%>

<input type="submit"
name="operation"
class="btn btn-success"
value="<%=TourGuideBookingCtl.OP_SAVE%>">

<input type="submit"
name="operation"
class="btn btn-warning"
value="<%=TourGuideBookingCtl.OP_RESET%>">

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

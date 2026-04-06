<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.TourGuideBookingDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.TourGuideBookingListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tour Guide Booking List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>

<script type="text/javascript"
src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<style>

.hm{
background-image: url('<%=ORSView.APP_CONTEXT%>/img/list2.jpg');
background-repeat: no-repeat;
background-attachment: fixed;
background-size: cover;
padding-top: 85px;
}

.text{
text-align:center;
}

</style>

</head>

<%@include file="Header.jsp"%>

<body class="hm">

<form action="<%=ORSView.TOUR_GUIDE_BOOKING_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.TourGuideBookingDTO"
scope="request"></jsp:useBean>

<%

int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;

int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List list = ServletUtility.getList(request);

Iterator<TourGuideBookingDTO> it = list.iterator();

if(list.size()!=0){

%>

<center>

<h1 class="text-dark font-weight-bold pt-3">

<u>Tour Guide Booking List</u>

</h1>

</center>

<!-- Success Message -->

<div class="row">

<div class="col-md-4"></div>

<%

if(!ServletUtility.getSuccessMessage(request).equals("")){

%>

<div class="col-md-4 alert alert-success alert-dismissible">

<button type="button" class="close" data-dismiss="alert">&times;</button>

<h4>

<font color="green">

<%=ServletUtility.getSuccessMessage(request)%>

</font>

</h4>

</div>

<% } %>

<div class="col-md-4"></div>

</div>


<!-- Error Message -->

<div class="row">

<div class="col-md-4"></div>

<%

if(!ServletUtility.getErrorMessage(request).equals("")){

%>

<div class="col-md-4 alert alert-danger alert-dismissible">

<button type="button" class="close" data-dismiss="alert">&times;</button>

<h4>

<font color="red">

<%=ServletUtility.getErrorMessage(request)%>

</font>

</h4>

</div>

<% } %>

<div class="col-md-4"></div>

</div>


<!-- Search Section -->

<div class="row">

<div class="col-sm-2"></div>

<div class="col-sm-2">

<input type="text"
name="touristName"
placeholder="Tourist Name"
class="form-control"
value="<%=ServletUtility.getParameter("touristName",request)%>">

</div>

<div class="col-sm-2">

<input type="text"
name="guideName"
placeholder="Guide Name"
class="form-control"
value="<%=ServletUtility.getParameter("guideName",request)%>">

</div>

<div class="col-sm-2">

<input type="text"
name="location"
placeholder="Location"
class="form-control"
value="<%=ServletUtility.getParameter("location",request)%>">

</div>

<div class="col-sm-2">

<input type="submit"
class="btn btn-primary"
name="operation"
value="<%=TourGuideBookingListCtl.OP_SEARCH%>">

<input type="submit"
class="btn btn-dark"
name="operation"
value="<%=TourGuideBookingListCtl.OP_RESET%>">

</div>

</div>

<br>

<div class="table-responsive">

<table class="table table-bordered table-dark table-hover">

<thead>

<tr style="background-color:#8C8C8C">

<th>

<input type="checkbox" id="select_all" name="Select"> Select All

</th>

<th class="text">S.NO</th>
<th class="text">Tourist Name</th>
<th class="text">Guide Name</th>
<th class="text">Location</th>
<th class="text">Booking Date</th>
<th class="text">Edit</th>

</tr>

</thead>

<%

while(it.hasNext()){

dto = it.next();

%>

<tbody>

<tr>

<td align="center">

<input type="checkbox"
class="checkbox"
name="ids"
value="<%=dto.getId()%>">

</td>

<td class="text"><%=index++%></td>

<td class="text"><%=dto.getTouristName()%></td>

<td class="text"><%=dto.getGuideName()%></td>

<td class="text"><%=dto.getLocation()%></td>

<td class="text"><%=DataUtility.getDateString(dto.getBookingdate())%></td>

<td class="text">

<a href="TourGuideBookingCtl?id=<%=dto.getId()%>">

Edit

</a>

</td>

</tr>

</tbody>

<%

}

%>

</table>

</div>


<!-- Pagination -->

<table width="100%">

<tr>

<td>

<input type="submit"
name="operation"
class="btn btn-warning"
value="<%=TourGuideBookingListCtl.OP_PREVIOUS%>"
<%=pageNo>1?"":"disabled"%>>

</td>

<td>

<input type="submit"
name="operation"
class="btn btn-primary"
value="<%=TourGuideBookingListCtl.OP_NEW%>">

</td>

<td>

<input type="submit"
name="operation"
class="btn btn-danger"
value="<%=TourGuideBookingListCtl.OP_DELETE%>">

</td>

<td align="right">

<input type="submit"
name="operation"
class="btn btn-warning"
value="<%=TourGuideBookingListCtl.OP_NEXT%>"
<%=(nextPageSize!=0)?"":"disabled"%>>

</td>

</tr>

</table>

<%

}

if(list.size()==0){

%>

<center>

<h2>No Record Found</h2>

</center>

<%

}

%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">

<input type="hidden" name="pageSize" value="<%=pageSize%>">

</form>

</body>

<%@include file="FooterView.jsp"%>

</html>
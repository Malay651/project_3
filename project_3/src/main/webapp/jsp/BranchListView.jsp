<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.BranchDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.BranchListCtl"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Branch List</title>

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

<form action="<%=ORSView.BRANCH_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BranchDTO"
scope="request"></jsp:useBean>

<%

int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;

int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

List list = ServletUtility.getList(request);

Iterator<BranchDTO> it = list.iterator();

if(list.size()!=0){

%>

<center>

<h1 class="text-dark font-weight-bold pt-3">

<u>Branch List</u>

</h1>

</center>

<!-- Success Message -->

<div class="row">

<div class="col-md-4"></div>

<%

if(!ServletUtility.getSuccessMessage(request).equals("")){

%>

<div class="col-md-4 alert alert-success alert-dismissible">

<button type="button" class="close" data-dismiss="alert">×</button>

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

<button type="button" class="close" data-dismiss="alert">×</button>

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

<div class="col-sm-3"></div>

<div class="col-sm-2">

<input type="text"

name="branchCode"

placeholder="Branch Code"

class="form-control"

value="<%=ServletUtility.getParameter("branchCode",request)%>">

</div>

<div class="col-sm-2">

<input type="text"

name="branchName"

placeholder="Branch Name"

class="form-control"

value="<%=ServletUtility.getParameter("branchName",request)%>">

</div>

<div class="col-sm-2">

<input type="text"

name="branchLocation"

placeholder="Branch Location"

class="form-control"

value="<%=ServletUtility.getParameter("branchLocation",request)%>">

</div>

<div class="col-sm-2">

<input type="text"

name="branchStatus"

placeholder="Branch Status"

class="form-control"

value="<%=ServletUtility.getParameter("branchStatus",request)%>">

</div>

<div class="col-sm-3">

<input type="submit"

class="btn btn-primary"

name="operation"

value="<%=BranchListCtl.OP_SEARCH%>">

<input type="submit"

class="btn btn-dark"

name="operation"

value="<%=BranchListCtl.OP_RESET%>">

</div>

</div>

<br>

<div class="table-responsive">

<table class="table table-bordered table-dark table-hover">

<thead>

<tr style="background-color:#8C8C8C">

<th>

<input type="checkbox" id="select_all" name="Select" > Select All

</th>

<th class="text">S.NO</th>

<th class="text">Branch Code</th>

<th class="text">Branch Name</th>

<th class="text">Branch Location</th>

<th class="text">Branch Status</th>

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

<td class="text"><%=dto.getBranchCode()%></td>

<td class="text"><%=dto.getBranchName()%></td>

<td class="text"><%=dto.getBranchLocation()%></td>

<td class="text"><%=dto.getBranchStatus()%></td>

<td class="text">

<a href="BranchCtl?id=<%=dto.getId()%>">

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

value="<%=BranchListCtl.OP_PREVIOUS%>"

<%=pageNo>1?"":"disabled"%>>

</td>

<td>

<input type="submit"

name="operation"

class="btn btn-primary"

value="<%=BranchListCtl.OP_NEW%>">

</td>

<td>

<input type="submit"

name="operation"

class="btn btn-danger"

value="<%=BranchListCtl.OP_DELETE%>">

</td>

<td align="right">

<input type="submit"

name="operation"

class="btn btn-warning"

value="<%=BranchListCtl.OP_NEXT%>"

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

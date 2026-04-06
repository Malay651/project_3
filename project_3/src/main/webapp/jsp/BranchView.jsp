<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.controller.BranchCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Branch View</title>

<style type="text/css">

.hm{
background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
background-repeat: no-repeat;
background-attachment: fixed;
background-size: cover;
padding-top:75px;
}

.input-group-addon{
box-shadow: 9px 8px 7px #001a33;
}

</style>

</head>

<body class="hm">

<div class="header">
<%@include file="Header.jsp"%>
</div>

<main>

<form action="<%=ORSView.BRANCH_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BranchDTO"
scope="request"></jsp:useBean>

<div class="row pt-3">

<div class="col-md-4"></div>

<div class="col-md-4">

<div class="card input-group-addon">

<div class="card-body">

<%

long id = DataUtility.getLong(request.getParameter("id"));

if(dto.getBranchCode()!=null && id>0){

%>

<h3 class="text-center text-primary">Update Branch</h3>

<%

}else{

%>

<h3 class="text-center text-primary">Add Branch</h3>

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

<input type="hidden" name="id" value="<%=dto.getId()%>">

<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">

<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>">

<input type="hidden" name="createdDatetime"
value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">

<input type="hidden" name="modifiedDatetime"
value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">

<!-- Branch Code -->

<span><b>Branch Code *</b></span>

<input type="text"
name="branchCode"
class="form-control"
placeholder="Branch Code"
value="<%=DataUtility.getStringData(dto.getBranchCode())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("branchCode",request)%>

</font>

<br>

<!-- Branch Name -->

<span><b>Branch Name *</b></span>

<input type="text"
name="branchName"
class="form-control"
placeholder="Branch Name"
value="<%=DataUtility.getStringData(dto.getBranchName())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("branchName",request)%>

</font>

<br>

<!-- Branch Location -->

<span><b>Branch Location *</b></span>

<input type="text"
name="branchLocation"
class="form-control"
placeholder="Branch Location"
value="<%=DataUtility.getStringData(dto.getBranchLocation())%>">

<font color="red">

<%=ServletUtility.getErrorMessage("branchLocation",request)%>

</font>

<br>

<!-- Branch Status Preload -->

<span><b>Branch Status *</b></span>

<%

HashMap map = new HashMap();

map.put("Active","Active");
map.put("Inactive","Inactive");

String htmlList = HTMLUtility.getList("branchStatus",dto.getBranchStatus(),map);

%>

<%=htmlList%>

<font color="red">

<%=ServletUtility.getErrorMessage("branchStatus",request)%>

</font>

<br>

<!-- Buttons -->

<div class="text-center">

<%

if(dto.getBranchCode()!=null && id>0){

%>

<input type="submit"
name="operation"
class="btn btn-success"
value="<%=BranchCtl.OP_UPDATE%>">

<input type="submit"
name="operation"
class="btn btn-warning"
value="<%=BranchCtl.OP_CANCEL%>">

<%

}else{

%>

<input type="submit"
name="operation"
class="btn btn-success"
value="<%=BranchCtl.OP_SAVE%>">

<input type="submit"
name="operation"
class="btn btn-warning"
value="<%=BranchCtl.OP_RESET%>">

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

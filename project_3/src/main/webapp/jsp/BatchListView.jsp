<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.BatchDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.BatchListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ include file="Header.jsp"%>

<body class="container-fluid mt-4">

<form action="<%=ORSView.BATCH_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BatchDTO"
	scope="request" />

<%
	List list = ServletUtility.getList(request);
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize") + "");
%>

<div class="container">

	<div class="text-center mb-4">
		<h2 class="text-primary"><u>Batch List</u></h2>
	</div>

	<!-- Search Section -->

	<div class="card mb-3">
		<div class="card-body row g-2">

			<div class="col-md-3">
				<input type="text" name="batchCode" class="form-control"
					placeholder="Batch Code"
					value="<%=ServletUtility.getParameter("batchCode", request)%>">
			</div>

			<div class="col-md-3">
				<input type="text" name="batchName" class="form-control"
					placeholder="Batch Name"
					value="<%=ServletUtility.getParameter("batchName", request)%>">
			</div>

			<div class="col-md-3">
				<input type="text" name="trainerName" class="form-control"
					placeholder="Trainer Name"
					value="<%=ServletUtility.getParameter("trainerName", request)%>">
			</div>

			<div class="col-md-3">
				<button type="submit" name="operation"
					value="<%=BatchListCtl.OP_SEARCH%>"
					class="btn btn-primary">Search</button>

				<button type="submit" name="operation"
					value="<%=BatchListCtl.OP_RESET%>"
					class="btn btn-secondary">Reset</button>
			</div>

		</div>
	</div>

	<!-- Table Section -->

	<div class="table-responsive">
		<table class="table table-bordered table-striped table-hover">

			<thead class="table-dark">
				<tr>
					<th>Select</th>
					<th>S.No</th>
					<th>Batch Code</th>
					<th>Batch Name</th>
					<th>Trainer Name</th>
					<th>Batch Timing</th>
					<th>Edit</th>
				</tr>
			</thead>

			<tbody>

			<%
				if (list != null && list.size() > 0) {
					Iterator<BatchDTO> it = list.iterator();
					while (it.hasNext()) {
						dto = it.next();
			%>

				<tr>
					<td><input type="checkbox" name="ids"
						value="<%=dto.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=dto.getBatchCode()%></td>
					<td><%=dto.getBatchName()%></td>
					<td><%=dto.getTrainerName()%></td>
					<td><%=dto.getBatchTiming()%></td>
					<td>
						<a class="btn btn-sm btn-warning"
						href="<%=ORSView.BATCH_CTL%>?id=<%=dto.getId()%>">
						Edit</a>
					</td>
				</tr>

			<%
					}
				} else {
			%>

				<tr>
					<td colspan="7" class="text-center text-danger">
						No Record Found
					</td>
				</tr>

			<%
				}
			%>

			</tbody>
		</table>
	</div>

	<!--  Pagination Section -->

	<div class="d-flex justify-content-between mt-3">

		<button type="submit" name="operation"
			value="<%=BatchListCtl.OP_PREVIOUS%>"
			class="btn btn-outline-primary"
			<%=pageNo > 1 ? "" : "disabled"%>>Previous</button>

		<div>
			<button type="submit" name="operation"
				value="<%=BatchListCtl.OP_NEW%>"
				class="btn btn-success">New</button>

			<button type="submit" name="operation"
				value="<%=BatchListCtl.OP_DELETE%>"
				class="btn btn-danger">Delete</button>
		</div>

		<button type="submit" name="operation"
			value="<%=BatchListCtl.OP_NEXT%>"
			class="btn btn-outline-primary"
			<%=nextPageSize > 0 ? "" : "disabled"%>>Next</button>

	</div>

	<input type="hidden" name="pageNo" value="<%=pageNo%>">
	<input type="hidden" name="pageSize" value="<%=pageSize%>">

</div>

</form>

</body>

<%@ include file="FooterView.jsp"%>
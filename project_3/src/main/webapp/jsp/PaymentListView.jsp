<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.PaymentDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.PaymentListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ include file="Header.jsp"%>

<body class="container-fluid mt-4">

<form action="<%=ORSView.PAYMENT_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.PaymentDTO"
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
		<h2 class="text-primary"><u>Payment List</u></h2>
	</div>

	<!-- Success Message -->
	<div class="row">
		<div class="col-md-4"></div>
		<%
			if (!ServletUtility.getSuccessMessage(request).equals("")) {
		%>
		<div class="col-md-4 alert alert-success alert-dismissible">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<h4><%=ServletUtility.getSuccessMessage(request)%></h4>
		</div>
		<%
			}
		%>
		<div class="col-md-4"></div>
	</div>

	<!-- Error Message -->
	<div class="row">
		<div class="col-md-4"></div>
		<%
			if (!ServletUtility.getErrorMessage(request).equals("")) {
		%>
		<div class="col-md-4 alert alert-danger alert-dismissible">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<h4><%=ServletUtility.getErrorMessage(request)%></h4>
		</div>
		<%
			}
		%>
		<div class="col-md-4"></div>
	</div>

	<!-- Search Section -->
	<div class="card mb-3">
		<div class="card-body row g-2">

			<div class="col-md-3">
				<input type="text" name="paymentCode" class="form-control"
					placeholder="Payment Code"
					value="<%=ServletUtility.getParameter("paymentCode", request)%>">
			</div>

			<div class="col-md-3">
				<input type="text" name="paymentMode" class="form-control"
					placeholder="Payment Mode"
					value="<%=ServletUtility.getParameter("paymentMode", request)%>">
			</div>

			<div class="col-md-3">
				<input type="text" name="amount" class="form-control"
					placeholder="Amount"
					value="<%=ServletUtility.getParameter("amount", request)%>">
			</div>

			<div class="col-md-3">
				<button type="submit" name="operation"
					value="<%=PaymentListCtl.OP_SEARCH%>"
					class="btn btn-primary">Search</button>

				<button type="submit" name="operation"
					value="<%=PaymentListCtl.OP_RESET%>"
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
					<th>Payment Code</th>
					<th>Amount</th>
					<th>Payment Mode</th>
					<th>Payment Date</th>
					<th>Edit</th>
				</tr>
			</thead>

			<tbody>

			<%
				if (list != null && list.size() > 0) {
					Iterator<PaymentDTO> it = list.iterator();
					while (it.hasNext()) {
						dto = it.next();
			%>

				<tr>
					<td><input type="checkbox" name="ids"
						value="<%=dto.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=dto.getPaymentCode()%></td>
					<td><%=dto.getAmount()%></td>
					<td><%=dto.getPaymentMode()%></td>
					<td><%=DataUtility.getDateString(dto.getPaymentDate())%></td>
					<td>
						<a class="btn btn-sm btn-warning"
						href="<%=ORSView.PAYMENT_CTL%>?id=<%=dto.getId()%>">
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

	<!-- Pagination Section -->
	<div class="d-flex justify-content-between mt-3">

		<button type="submit" name="operation"
			value="<%=PaymentListCtl.OP_PREVIOUS%>"
			class="btn btn-outline-primary"
			<%=pageNo > 1 ? "" : "disabled"%>>Previous</button>

		<div>
			<button type="submit" name="operation"
				value="<%=PaymentListCtl.OP_NEW%>"
				class="btn btn-success">New</button>

			<button type="submit" name="operation"
				value="<%=PaymentListCtl.OP_DELETE%>"
				class="btn btn-danger">Delete</button>
		</div>

		<button type="submit" name="operation"
			value="<%=PaymentListCtl.OP_NEXT%>"
			class="btn btn-outline-primary"
			<%=nextPageSize > 0 ? "" : "disabled"%>>Next</button>

	</div>

	<input type="hidden" name="pageNo" value="<%=pageNo%>">
	<input type="hidden" name="pageSize" value="<%=pageSize%>">

</div>

</form>

</body>

<%@ include file="FooterView.jsp"%>
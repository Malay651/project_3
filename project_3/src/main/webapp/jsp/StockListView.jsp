<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.StockDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.StockListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>

<%@ include file="Header.jsp"%>
<%@ include file="calendar.jsp"%>
<body class="container-fluid mt-4">

<form action="<%=ORSView.STOCK_LIST_CTL%>" method="post">

<jsp:useBean id="dto" class="in.co.rays.project_3.dto.StockDTO"
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
		<h2 class="text-primary"><u>Stock List</u></h2>
	</div>

	<!-- Success Message -->

	<div class="row">
		<div class="col-md-4"></div>
		<%
			if (!ServletUtility.getSuccessMessage(request).equals("")) {
		%>
		<div class="col-md-4 alert alert-success alert-dismissible">
			<button type="button" class="close" data-dismiss="alert">&times;</button>
			<h4>
				<font color="green">
					<%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h4>
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
			<h4>
				<font color="red">
					<%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h4>
		</div>
		<%
			}
		%>
		<div class="col-md-4"></div>
	</div>

	<!-- Search Section -->

	<div class="card mb-3">
		<div class="card-body row g-2">

			<div class="col-md-4">
				<input type="text" name="stockName" class="form-control"
					placeholder="Stock Name"
					value="<%=ServletUtility.getParameter("stockName", request)%>">
			</div>

			<div class="col-md-4">
				<input type="text" name="price" class="form-control"
					placeholder="Price"
					value="<%=ServletUtility.getParameter("price", request)%>">
			</div>

			<div class="col-md-4">
				<button type="submit" name="operation"
					value="<%=StockListCtl.OP_SEARCH%>"
					class="btn btn-primary">Search</button>

				<button type="submit" name="operation"
					value="<%=StockListCtl.OP_RESET%>"
					class="btn btn-secondary">Reset</button>
			</div>

		</div>
	</div>

	<!-- Table Section -->

	<div class="table-responsive">
		<table class="table table-bordered table-striped table-hover">

			<thead class="table-dark">
				<tr><th><input type="checkbox" id="select_all" name="Select" class="text"> Select All</th>
					<th>S.No</th>
					<th>Stock Name</th>
					<th>Price</th>
					<th>Quantity</th>
					<th>Edit</th>
				</tr>
			</thead>

			

			<%
				if (list != null && list.size() > 0) {
					Iterator<StockDTO> it = list.iterator();
					while (it.hasNext()) {
						dto = it.next();
			%>

				<tr>
					<td>
						<input type="checkbox" name="ids" class="checkbox"
							value="<%=dto.getId()%>">
					</td>
					<td><%=index++%></td>
					<td><%=dto.getStockName()%></td>
					<td><%=dto.getPrice()%></td>
					<td><%=dto.getQuantity()%></td>
					<td>
						<a class="btn btn-sm btn-warning"
						href="<%=ORSView.STOCK_CTL%>?id=<%=dto.getId()%>">
						Edit</a>
					</td>
				</tr>

			<%
					}
				} else {
			%>

				<tr>
					<td colspan="6" class="text-center text-danger">
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
			value="<%=StockListCtl.OP_PREVIOUS%>"
			class="btn btn-outline-primary"
			<%=pageNo > 1 ? "" : "disabled"%>>
			Previous
		</button>

		<div>
			<button type="submit" name="operation"
				value="<%=StockListCtl.OP_NEW%>"
				class="btn btn-success">New</button>

			<button type="submit" name="operation"
				value="<%=StockListCtl.OP_DELETE%>"
				class="btn btn-danger">Delete</button>
		</div>

		<button type="submit" name="operation"
			value="<%=StockListCtl.OP_NEXT%>"
			class="btn btn-outline-primary"
			<%=nextPageSize > 0 ? "" : "disabled"%>>
			Next
		</button>

	</div>

	<input type="hidden" name="pageNo" value="<%=pageNo%>">
	<input type="hidden" name="pageSize" value="<%=pageSize%>">

</div>

</form>

</body>

<%@ include file="FooterView.jsp"%>
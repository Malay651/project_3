<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.dto.EmployeeDTO"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.EmployeeListCtl"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>

<%@ include file="Header.jsp"%>

<body class="hm">

	<form action="<%=ORSView.EMPLOYEE_LIST_CTL%>" method="post">

		<jsp:useBean id="dto" class="in.co.rays.project_3.dto.EmployeeDTO"
			scope="request" />

		<%
			List list = ServletUtility.getList(request);
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize") + "");
		%>

		<center>
			<h2>
				<u>Employee List</u>
			</h2>
		</center>

		<!--  Search Section -->

		<div>
			<input type="text" name="employeeCode" placeholder="Employee Code"
				value="<%=ServletUtility.getParameter("employeeCode", request)%>">

			<input type="text" name="employeeName" placeholder="Employee Name"
				value="<%=ServletUtility.getParameter("employeeName", request)%>">

			<input type="text" name="department" placeholder="Department"
				value="<%=ServletUtility.getParameter("department", request)%>">

			<input type="submit" name="operation"
				value="<%=EmployeeListCtl.OP_SEARCH%>"> <input type="submit"
				name="operation" value="<%=EmployeeListCtl.OP_RESET%>">
		</div>

		<br>

		<!--  Table Section -->

		<table border="1" width="100%">
			<tr>
				<th>Select</th>
				<th>S.No</th>
				<th>Employee Code</th>
				<th>Employee Name</th>
				<th>Department</th>
				<th>Email</th>
				<th>Designation</th>
				<th>Edit</th>
			</tr>

			<%
				if (list != null) {
					Iterator<EmployeeDTO> it = list.iterator();
					while (it.hasNext()) {
						dto = it.next();
			%>
			<tr>
				<td align="center"><input type="checkbox" name="ids"
					value="<%=dto.getId()%>"></td>
				<td><%=index++%></td>
				<td><%=dto.getEmployeeCode()%></td>
				<td><%=dto.getEmployeeName()%></td>
				<td><%=dto.getDepartment()%></td>
				<td><%=dto.getEmail()%></td>
				<td><%=dto.getDesignation()%></td>
				<td><a href="<%=ORSView.EMPLOYEE_CTL%>?id=<%=dto.getId()%>">Edit</a>
				</td>
			</tr>
			<%
				}
				}
			%>

		</table>

		<br>

		<!-- Pagination -->

		<input type="submit" name="operation"
			value="<%=EmployeeListCtl.OP_PREVIOUS%>"
			<%=pageNo > 1 ? "" : "disabled"%>> <input type="submit"
			name="operation" value="<%=EmployeeListCtl.OP_NEW%>"> <input
			type="submit" name="operation" value="<%=EmployeeListCtl.OP_DELETE%>">

		<input type="submit" name="operation"
			value="<%=EmployeeListCtl.OP_NEXT%>"
			<%=nextPageSize > 0 ? "" : "disabled"%>> <input type="hidden"
			name="pageNo" value="<%=pageNo%>"> <input type="hidden"
			name="pageSize" value="<%=pageSize%>">

	</form>

</body>

<%@ include file="FooterView.jsp"%>
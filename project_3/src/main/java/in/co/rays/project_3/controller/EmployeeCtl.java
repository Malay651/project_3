package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.EmployeeDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.EmployeeModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Employee Controller (Add, Update, Delete)
 * 
 * @author malay
 */

@WebServlet(urlPatterns = { "/ctl/EmployeeCtl" })
public class EmployeeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(EmployeeCtl.class);

	// ---------------- VALIDATION ----------------
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("employeeCode"))) {
			request.setAttribute("employeeCode", PropertyReader.getValue("error.require", "Employee Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("employeeName"))) {
			request.setAttribute("employeeName", PropertyReader.getValue("error.require", "Employee Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("employeeName"))) {
			request.setAttribute("employeeName", "Please enter valid Employee Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("department"))) {
			request.setAttribute("department", PropertyReader.getValue("error.require", "Department"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("designation"))) {
			request.setAttribute("designation", PropertyReader.getValue("error.require", "Designation"));
			pass = false;
		}

		return pass;
	}

	// ---------------- POPULATE DTO ----------------
	protected BaseDTO populateDTO(HttpServletRequest request) {

		EmployeeDTO dto = new EmployeeDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setEmployeeCode(DataUtility.getString(request.getParameter("employeeCode")));
		dto.setEmployeeName(DataUtility.getString(request.getParameter("employeeName")));
		dto.setDepartment(DataUtility.getString(request.getParameter("department")));
		dto.setEmail(DataUtility.getString(request.getParameter("email")));
		dto.setDesignation(DataUtility.getString(request.getParameter("designation")));

		populateBean(dto, request);

		return dto;
	}

	// ---------------- DO GET ----------------
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		EmployeeModelInt model = ModelFactory.getInstance().getEmployeeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				EmployeeDTO dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	// ---------------- DO POST ----------------
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));
		EmployeeModelInt model = ModelFactory.getInstance().getEmployeeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			EmployeeDTO dto = (EmployeeDTO) populateDTO(request);

			try {

				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Employee Updated Successfully", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Employee Added Successfully", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Employee Code already exists", request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			EmployeeDTO dto = (EmployeeDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EMPLOYEE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.EMPLOYEE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.EMPLOYEE_VIEW;
	}
}
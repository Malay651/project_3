package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BranchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BranchModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**

* Branch Controller (Add, Update, Delete)
* @author malay
  */

@WebServlet(urlPatterns = { "/ctl/BranchCtl" })
public class BranchCtl extends BaseCtl {


private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(BranchCtl.class);

// ---------------- VALIDATION ----------------
protected boolean validate(HttpServletRequest request) {

	boolean pass = true;

	if (DataValidator.isNull(request.getParameter("branchCode"))) {
		request.setAttribute("branchCode",
				PropertyReader.getValue("error.require", "Branch Code"));
		pass = false;
	}

	if (DataValidator.isNull(request.getParameter("branchName"))) {
		request.setAttribute("branchName",
				PropertyReader.getValue("error.require", "Branch Name"));
		
	}  else if (!DataValidator.isName(request.getParameter("branchName"))) {
			request.setAttribute("branchName", "please enter correct branch Name");
			pass = false;
       }

	if (DataValidator.isNull(request.getParameter("branchLocation"))) {
		request.setAttribute("branchLocation",
				PropertyReader.getValue("error.require", "Branch Location"));
		pass = false;
	}

	if (DataValidator.isNull(request.getParameter("branchStatus"))) {
		request.setAttribute("branchStatus",
				PropertyReader.getValue("error.require", "Branch Status"));
		pass = false;
	}

	return pass;
}

// ---------------- POPULATE DTO ----------------
protected BaseDTO populateDTO(HttpServletRequest request) {

	BranchDTO dto = new BranchDTO();

	dto.setId(DataUtility.getLong(request.getParameter("id")));
	dto.setBranchCode(DataUtility.getString(request.getParameter("branchCode")));
	dto.setBranchName(DataUtility.getString(request.getParameter("branchName")));
	dto.setBranchLocation(DataUtility.getString(request.getParameter("branchLocation")));
	dto.setBranchStatus(DataUtility.getString(request.getParameter("branchStatus")));

	populateBean(dto, request);

	return dto;
}

// ---------------- DO GET ----------------
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

	BranchModelInt model = ModelFactory.getInstance().getBranchModel();

	long id = DataUtility.getLong(request.getParameter("id"));

	if (id > 0) {
		try {
			BranchDTO dto = model.findByPK(id);
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
	BranchModelInt model = ModelFactory.getInstance().getBranchModel();

	long id = DataUtility.getLong(request.getParameter("id"));

	if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

		BranchDTO dto = (BranchDTO) populateDTO(request);

		try {
			if (id > 0) {
				model.update(dto);
				ServletUtility.setSuccessMessage(
						"Data is successfully Updated", request);
			} else {
				model.add(dto);
				ServletUtility.setSuccessMessage(
						"Data is successfully Saved", request);
			}

			ServletUtility.setDto(dto, request);

		} catch (DuplicateRecordException e) {
			ServletUtility.setDto(dto, request);
			ServletUtility.setErrorMessage("Branch Code already exists", request);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

	} else if (OP_DELETE.equalsIgnoreCase(op)) {

		BranchDTO dto = (BranchDTO) populateDTO(request);

		try {
			model.delete(dto);
			ServletUtility.redirect(ORSView.BRANCH_LIST_CTL, request, response);
			return;
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

	} else if (OP_CANCEL.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.BRANCH_LIST_CTL, request, response);
		return;

	} else if (OP_RESET.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.BRANCH_CTL, request, response);
		return;
	}

	ServletUtility.forward(getView(), request, response);
}

@Override
protected String getView() {
	return ORSView.BRANCH_VIEW;
}


}

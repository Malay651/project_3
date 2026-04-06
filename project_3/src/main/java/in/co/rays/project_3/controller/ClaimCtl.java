package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ClaimDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ClaimModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**

* Claim Controller (Add, Update, Delete)
* @author malay
  */

@WebServlet(urlPatterns = { "/ctl/ClaimCtl" })
public class ClaimCtl extends BaseCtl {

private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(ClaimCtl.class);

// ---------------- VALIDATION ----------------
protected boolean validate(HttpServletRequest request) {

	boolean pass = true;

	if (DataValidator.isNull(request.getParameter("claimNumber"))) {
		request.setAttribute("claimNumber",
				PropertyReader.getValue("error.require", "Claim Number"));
		pass = false;
	}

	if (DataValidator.isNull(request.getParameter("claimAmount"))) {
		request.setAttribute("claimAmount",
				PropertyReader.getValue("error.require", "Claim Amount"));
		pass = false;
	}

	if (DataValidator.isNull(request.getParameter("status"))) {
		request.setAttribute("status",
				PropertyReader.getValue("error.require", "Status"));
		pass = false;
	}

	return pass;
}

// ---------------- POPULATE DTO ----------------
protected BaseDTO populateDTO(HttpServletRequest request) {

	ClaimDTO dto = new ClaimDTO();

	dto.setId(DataUtility.getLong(request.getParameter("id")));
	dto.setClaimNumber(DataUtility.getString(request.getParameter("claimNumber")));
	dto.setClaimAmount(DataUtility.getLong(request.getParameter("claimAmount")));
	dto.setStatus(DataUtility.getString(request.getParameter("status")));

	populateBean(dto, request);

	return dto;
}

// ---------------- DO GET ----------------
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

	ClaimModelInt model = ModelFactory.getInstance().getClaimModel();

	long id = DataUtility.getLong(request.getParameter("id"));

	if (id > 0) {
		try {
			ClaimDTO dto = model.findByPK(id);
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
	ClaimModelInt model = ModelFactory.getInstance().getClaimModel();

	long id = DataUtility.getLong(request.getParameter("id"));

	if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

		ClaimDTO dto = (ClaimDTO) populateDTO(request);

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
			ServletUtility.setErrorMessage("Claim Number already exists", request);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

	} else if (OP_DELETE.equalsIgnoreCase(op)) {

		ClaimDTO dto = (ClaimDTO) populateDTO(request);

		try {
			model.delete(dto);
			ServletUtility.redirect(ORSView.CLAIM_LIST_CTL, request, response);
			return;
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

	} else if (OP_CANCEL.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.CLAIM_LIST_CTL, request, response);
		return;

	} else if (OP_RESET.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.CLAIM_CTL, request, response);
		return;
	}

	ServletUtility.forward(getView(), request, response);
}

@Override
protected String getView() {
	return ORSView.CLAIM_VIEW;
}


}

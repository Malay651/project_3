package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.DonationCampDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.DonationCampModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**

* Donation Camp Controller (Add, Update, Delete)
* @author malay
  */

@WebServlet(urlPatterns = { "/ctl/DonationCampCtl" })
public class DonationCampCtl extends BaseCtl {

private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(DonationCampCtl.class);

// ---------------- VALIDATION ----------------
protected boolean validate(HttpServletRequest request) {

	boolean pass = true;

	if (DataValidator.isNull(request.getParameter("campName"))) {
		request.setAttribute("campName",
				PropertyReader.getValue("error.require", "Camp Name"));
		pass = false;
	}

	if (DataValidator.isNull(request.getParameter("campDate"))) {
		request.setAttribute("campDate",
				PropertyReader.getValue("error.require", "Camp Date"));
		pass = false;
	}

	if (DataValidator.isNull(request.getParameter("organizer"))) {
		request.setAttribute("organizer",
				PropertyReader.getValue("error.require", "Organizer"));
		pass = false;
	}

	return pass;
}

// ---------------- POPULATE DTO ----------------
protected BaseDTO populateDTO(HttpServletRequest request) {

	DonationCampDTO dto = new DonationCampDTO();

	dto.setId(DataUtility.getLong(request.getParameter("id")));
	dto.setCampName(DataUtility.getString(request.getParameter("campName")));
	dto.setCampDate(DataUtility.getDate(request.getParameter("campDate")));
	dto.setOrganizer(DataUtility.getString(request.getParameter("organizer")));

	populateBean(dto, request);

	return dto;
}

// ---------------- DO GET ----------------
protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

	DonationCampModelInt model = ModelFactory.getInstance().getDonationCampModel();

	long id = DataUtility.getLong(request.getParameter("id"));

	if (id > 0) {
		try {
			DonationCampDTO dto = model.findByPK(id);
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
	DonationCampModelInt model = ModelFactory.getInstance().getDonationCampModel();

	long id = DataUtility.getLong(request.getParameter("id"));

	if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

		DonationCampDTO dto = (DonationCampDTO) populateDTO(request);

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
			ServletUtility.setErrorMessage("Camp Name already exists", request);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

	} else if (OP_DELETE.equalsIgnoreCase(op)) {

		DonationCampDTO dto = (DonationCampDTO) populateDTO(request);

		try {
			model.delete(dto);
			ServletUtility.redirect(ORSView.DONATIONCAMP_LIST_CTL, request, response);
			return;
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

	} else if (OP_CANCEL.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.DONATIONCAMP_LIST_CTL, request, response);
		return;

	} else if (OP_RESET.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.DONATIONCAMP_CTL, request, response);
		return;
	}

	ServletUtility.forward(getView(), request, response);
}

@Override
protected String getView() {
	return ORSView.DONATIONCAMP_VIEW;
}


}

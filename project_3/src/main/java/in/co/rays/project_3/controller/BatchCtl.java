package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BatchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.BatchModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Batch Controller (Add, Update, Delete)
 * 
 * @author malay
 */

@WebServlet(urlPatterns = { "/ctl/BatchCtl" })
public class BatchCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(BatchCtl.class);

	// ---------------- VALIDATION ----------------
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("batchCode"))) {
			request.setAttribute("batchCode",
					PropertyReader.getValue("error.require", "Batch Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("batchName"))) {
			request.setAttribute("batchName",
					PropertyReader.getValue("error.require", "Batch Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("batchName"))) {
			request.setAttribute("batchName",
					"Please enter valid Batch Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("trainerName"))) {
			request.setAttribute("trainerName",
					PropertyReader.getValue("error.require", "Trainer Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("trainerName"))) {
			request.setAttribute("trainerName",
					"Please enter valid Trainer Name");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("batchTiming"))) {
			request.setAttribute("batchTiming",
					PropertyReader.getValue("error.require", "Batch Timing"));
			pass = false;
		}

		return pass;
	}

	// ---------------- POPULATE DTO ----------------
	protected BaseDTO populateDTO(HttpServletRequest request) {

		BatchDTO dto = new BatchDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setBatchCode(DataUtility.getString(request.getParameter("batchCode")));
		dto.setBatchName(DataUtility.getString(request.getParameter("batchName")));
		dto.setTrainerName(DataUtility.getString(request.getParameter("trainerName")));
		dto.setBatchTiming(DataUtility.getString(request.getParameter("batchTiming")));

		populateBean(dto, request);

		return dto;
	}

	// ---------------- DO GET ----------------
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		BatchModelInt model = ModelFactory.getInstance().getBatchModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				BatchDTO dto = model.findByPK(id);
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
		BatchModelInt model = ModelFactory.getInstance().getBatchModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			BatchDTO dto = (BatchDTO) populateDTO(request);

			try {

				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Batch Updated Successfully", request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage("Batch Added Successfully", request);
				}

				ServletUtility.setDto(dto, request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Batch Code already exists", request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			BatchDTO dto = (BatchDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.BATCH_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BATCH_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BATCH_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.BATCH_VIEW;
	}
}
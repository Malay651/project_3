package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.JobApplicationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.JobApplicationModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/JobApplicationCtl" })
public class JobApplicationCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(JobApplicationCtl.class);

	
	  // ----------- PRELOAD (Application Status Dropdown) -----------
    @Override
    protected void preload(HttpServletRequest request) {

        List<String> statusList = new ArrayList<>();

        statusList.add("Pending");
        statusList.add("Interview Scheduled");
        statusList.add("Selected");
        statusList.add("Rejected");

        request.setAttribute("applicationStatusList", statusList);
    }


	// ---------------- VALIDATION ----------------
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("applicationName"))) {
			request.setAttribute("applicationName",
					PropertyReader.getValue("error.require", "Application Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("jobTitle"))) {
			request.setAttribute("jobTitle",
					PropertyReader.getValue("error.require", "Job Title"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("resumeLink"))) {
			request.setAttribute("resumeLink",
					PropertyReader.getValue("error.require", "Resume Link"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("applicationDate"))) {
			request.setAttribute("applicationDate",
					PropertyReader.getValue("error.require", "Application Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("interviewDate"))) {
			request.setAttribute("interviewDate",
					PropertyReader.getValue("error.require", "Interview Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("applicationStatus"))) {
			request.setAttribute("applicationStatus",
					PropertyReader.getValue("error.require", "Application Status"));
			pass = false;
		}

		return pass;
	}

	// ---------------- POPULATE DTO ----------------
	protected BaseDTO populateDTO(HttpServletRequest request) {

		JobApplicationDTO dto = new JobApplicationDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setApplicationName(
				DataUtility.getString(request.getParameter("applicationName")));
		dto.setJobTitle(
				DataUtility.getString(request.getParameter("jobTitle")));
		dto.setResumeLink(
				DataUtility.getString(request.getParameter("resumeLink")));

		dto.setApplicationDate(
				DataUtility.getDate(request.getParameter("applicationDate")));
    System.out.println(request.getParameter("interviewDate"));
		dto.setInterviewDate(
				DataUtility.getDate(request.getParameter("interviewDate")));

		dto.setApplicationStatus(
				DataUtility.getString(request.getParameter("applicationStatus")));

		populateBean(dto, request);

		return dto;
	}

	// ---------------- DO GET ----------------
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		JobApplicationModelInt model =
				ModelFactory.getInstance().getJobApplicationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				JobApplicationDTO dto = model.findByPK(id);
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));

		JobApplicationModelInt model =
				ModelFactory.getInstance().getJobApplicationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)
				|| OP_UPDATE.equalsIgnoreCase(op)) {

			JobApplicationDTO dto =
					(JobApplicationDTO) populateDTO(request);

			try {

				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage(
							"Job Application Updated Successfully",
							request);
				} else {
					model.add(dto);
					ServletUtility.setSuccessMessage(
							"Job Application Added Successfully",
							request);
				}

				ServletUtility.setDto(dto, request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage(
						"Application already exists", request);

			} catch (ApplicationException e) {

				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			JobApplicationDTO dto =
					(JobApplicationDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(
						ORSView.JOB_APPLICATION_LIST_CTL,
						request, response);
				return;

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.JOB_APPLICATION_LIST_CTL,
					request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.JOB_APPLICATION_CTL,
					request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.JOB_APPLICATION_VIEW;
	}
}
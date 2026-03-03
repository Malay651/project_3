package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ContactDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ContactModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/ContactCtl" })
public class ContactCtl extends BaseCtl {

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name",  "Name must contain alphabets only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "email"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "email"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "mobile No"));
			pass = false;
		}else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No. must be digits only ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("message"))) {
			request.setAttribute("message", PropertyReader.getValue("error.require", "message"));
			pass = false;
		}
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		ContactDTO dto = new ContactDTO();
		
		dto.setId(DataUtility.getLong(request.getParameter("contactId")));
        dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setEmail(DataUtility.getString(request.getParameter("email")));
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileno")));
        dto.setMessage(DataUtility.getString(request.getParameter("message")));


		populateBean(dto,request);
		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		ContactModelInt model=ModelFactory.getInstance().getContactModel();
		if (id > 0 || op != null) {
			ContactDTO dto;
			try {
			  dto=model.findByPK(id);
			  ServletUtility.setDto(dto, request);
				
			} catch (ApplicationException e) {
				
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       
		String op=request.getParameter("operation");
       long id=DataUtility.getLong(request.getParameter("id"));
  
       ContactModelInt model=ModelFactory.getInstance().getContactModel();
       
       if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
    	   
    	   ContactDTO dto = (ContactDTO) populateDTO(request);

    	   try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);
					
					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					
					//long pk 
							model.add(dto);
					ServletUtility.setSuccessMessage("Record Successfully Saved", request);
				}
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Email Already Exists", request);
			} 
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CONTACT_CTL, request, response);
				return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CONTACT_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CONTACT_VIEW;
	}

}


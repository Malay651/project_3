package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.TourGuideBookingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.TourGuideBookingModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**

* Tour Guide Booking Controller (Add, Update, Delete)
  */

@WebServlet(urlPatterns = { "/ctl/TourGuideBookingCtl" })
public class TourGuideBookingCtl extends BaseCtl {


private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(TourGuideBookingCtl.class);

// ---------------- VALIDATION ----------------
protected boolean validate(HttpServletRequest request) {

    boolean pass = true;

    if (DataValidator.isNull(request.getParameter("touristName"))) {
        request.setAttribute("touristName",
                PropertyReader.getValue("error.require", "Tourist Name"));
        pass = false;
    }

    if (DataValidator.isNull(request.getParameter("guideName"))) {
        request.setAttribute("guideName",
                PropertyReader.getValue("error.require", "Guide Name"));
        pass = false;
    }

    if (DataValidator.isNull(request.getParameter("location"))) {
        request.setAttribute("location",
                PropertyReader.getValue("error.require", "Location"));
        pass = false;
    }

    if (DataValidator.isNull(request.getParameter("bookingdate"))) {
        request.setAttribute("bookingdate",
                PropertyReader.getValue("error.require", "Booking Date"));
        pass = false;
    }

    return pass;
}

// ---------------- POPULATE DTO ----------------
protected BaseDTO populateDTO(HttpServletRequest request) {

    TourGuideBookingDTO dto = new TourGuideBookingDTO();

    dto.setId(DataUtility.getLong(request.getParameter("id")));
    dto.setTouristName(DataUtility.getString(request.getParameter("touristName")));
    dto.setGuideName(DataUtility.getString(request.getParameter("guideName")));
    dto.setLocation(DataUtility.getString(request.getParameter("location")));
    dto.setBookingdate(DataUtility.getDate(request.getParameter("bookingdate")));

    populateBean(dto, request);

    return dto;
}

// ---------------- DO GET ----------------
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

    TourGuideBookingModelInt model = ModelFactory.getInstance().getTourGuideBookingModel();

    long id = DataUtility.getLong(request.getParameter("id"));

    if (id > 0) {
        try {
            TourGuideBookingDTO dto = model.findByPK(id);
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
    TourGuideBookingModelInt model = ModelFactory.getInstance().getTourGuideBookingModel();

    long id = DataUtility.getLong(request.getParameter("id"));

    if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

        TourGuideBookingDTO dto = (TourGuideBookingDTO) populateDTO(request);

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
            ServletUtility.setErrorMessage("Tourist Name already exists", request);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }

    } else if (OP_DELETE.equalsIgnoreCase(op)) {

        TourGuideBookingDTO dto = (TourGuideBookingDTO) populateDTO(request);

        try {
            model.delete(dto);
            ServletUtility.redirect(ORSView.TOUR_GUIDE_BOOKING_LIST_CTL, request, response);
            return;

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }

    } else if (OP_CANCEL.equalsIgnoreCase(op)) {

        ServletUtility.redirect(ORSView.TOUR_GUIDE_BOOKING_LIST_CTL, request, response);
        return;

    } else if (OP_RESET.equalsIgnoreCase(op)) {

        ServletUtility.redirect(ORSView.TOUR_GUIDE_BOOKING_CTL, request, response);
        return;
    }

    ServletUtility.forward(getView(), request, response);
}

@Override
protected String getView() {
    return ORSView.TOUR_GUIDE_BOOKING_VIEW;
}


}

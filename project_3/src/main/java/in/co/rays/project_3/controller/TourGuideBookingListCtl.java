package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.TourGuideBookingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.TourGuideBookingModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "TourGuideBookingListCtl", urlPatterns = { "/ctl/TourGuideBookingListCtl" })
public class TourGuideBookingListCtl extends BaseCtl {


private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(TourGuideBookingListCtl.class);

@Override
protected BaseDTO populateDTO(HttpServletRequest request) {

    TourGuideBookingDTO dto = new TourGuideBookingDTO();

    dto.setTouristName(DataUtility.getString(request.getParameter("touristName")));
    dto.setGuideName(DataUtility.getString(request.getParameter("guideName")));
    dto.setLocation(DataUtility.getString(request.getParameter("location")));
    dto.setBookingdate(DataUtility.getDate(request.getParameter("bookingdate")));

    populateBean(dto, request);

    return dto;
}

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    log.debug("TourGuideBookingListCtl doGet Start");

    List list = null;
    List next = null;

    int pageNo = 1;
    int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

    TourGuideBookingDTO dto = (TourGuideBookingDTO) populateDTO(request);
    TourGuideBookingModelInt model = ModelFactory.getInstance().getTourGuideBookingModel();

    try {

        list = model.search(dto, pageNo, pageSize);
        next = model.search(dto, pageNo + 1, pageSize);

        ServletUtility.setList(list, request);

        if (list == null || list.size() == 0) {
            ServletUtility.setErrorMessage("No record found", request);
        }

        if (next == null || next.size() == 0) {
            request.setAttribute("nextListSize", 0);
        } else {
            request.setAttribute("nextListSize", next.size());
        }

        ServletUtility.setPageNo(pageNo, request);
        ServletUtility.setPageSize(pageSize, request);

        ServletUtility.forward(getView(), request, response);

    } catch (ApplicationException e) {
        log.error(e);
        ServletUtility.handleException(e, request, response);
    }
}

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    log.debug("TourGuideBookingListCtl doPost Start");

    List list = null;
    List next = null;

    int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
    int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

    pageNo = (pageNo == 0) ? 1 : pageNo;
    pageSize = (pageSize == 0)
            ? DataUtility.getInt(PropertyReader.getValue("page.size"))
            : pageSize;

    TourGuideBookingDTO dto = (TourGuideBookingDTO) populateDTO(request);
    String op = DataUtility.getString(request.getParameter("operation"));

    String[] ids = request.getParameterValues("ids");

    TourGuideBookingModelInt model = ModelFactory.getInstance().getTourGuideBookingModel();

    try {

        if (OP_SEARCH.equalsIgnoreCase(op)
                || OP_NEXT.equalsIgnoreCase(op)
                || OP_PREVIOUS.equalsIgnoreCase(op)) {

            if (OP_SEARCH.equalsIgnoreCase(op)) {
                pageNo = 1;
            } else if (OP_NEXT.equalsIgnoreCase(op)) {
                pageNo++;
            } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                pageNo--;
            }

        } else if (OP_NEW.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.TOUR_GUIDE_BOOKING_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.TOUR_GUIDE_BOOKING_LIST_CTL, request, response);
            return;

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            pageNo = 1;

            if (ids != null && ids.length > 0) {

                for (String id : ids) {
                    TourGuideBookingDTO deleteDto = new TourGuideBookingDTO();
                    deleteDto.setId(DataUtility.getLong(id));
                    model.delete(deleteDto);
                }

                ServletUtility.setSuccessMessage("Data Successfully Deleted!", request);

            } else {
                ServletUtility.setErrorMessage("Select atleast one record", request);
            }
        }

        list = model.search(dto, pageNo, pageSize);
        next = model.search(dto, pageNo + 1, pageSize);

        ServletUtility.setDto(dto, request);
        ServletUtility.setList(list, request);

        if (list == null || list.size() == 0) {
            if (!OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found", request);
            }
        }

        if (next == null || next.size() == 0) {
            request.setAttribute("nextListSize", 0);
        } else {
            request.setAttribute("nextListSize", next.size());
        }

        ServletUtility.setPageNo(pageNo, request);
        ServletUtility.setPageSize(pageSize, request);

        ServletUtility.forward(getView(), request, response);

    } catch (ApplicationException e) {
        log.error(e);
        ServletUtility.handleException(e, request, response);
    }
}

@Override
protected String getView() {
    return ORSView.TOUR_GUIDE_BOOKING_LIST_VIEW;
}


}

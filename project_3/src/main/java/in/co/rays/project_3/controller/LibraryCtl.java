package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.LibraryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.LibraryModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/LibraryCtl" })
public class LibraryCtl extends BaseCtl {

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(LibraryCtl.class);

    // ================== PRELOAD ==================
    protected void preload(HttpServletRequest request) {

        List<String> statusList = new ArrayList<>();

        statusList.add("Available");
        statusList.add("Issued");
        statusList.add("Reserved");

        request.setAttribute("statusList", statusList);
    }

    // ================== VALIDATE ==================
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("bookCode"))) {
            request.setAttribute("bookCode",
                    PropertyReader.getValue("error.require", "Book Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("bookName"))) {
            request.setAttribute("bookName",
                    PropertyReader.getValue("error.require", "Book Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("authorName"))) {
            request.setAttribute("authorName",
                    PropertyReader.getValue("error.require", "Author Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("availabilityStatus"))) {
            request.setAttribute("availabilityStatus",
                    PropertyReader.getValue("error.require", "Availability Status"));
            pass = false;
        }

        return pass;
    }

    // ================== POPULATE DTO ==================
    protected BaseDTO populateDTO(HttpServletRequest request) {

        LibraryDTO dto = new LibraryDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setBookCode(DataUtility.getString(request.getParameter("bookCode")));
        dto.setBookName(DataUtility.getString(request.getParameter("bookName")));
        dto.setAuthorName(DataUtility.getString(request.getParameter("authorName")));
        dto.setAvailabilityStatus(
                DataUtility.getString(request.getParameter("availabilityStatus")));

        populateBean(dto, request);

        return dto;
    }

    // ================== DO GET ==================
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        LibraryModelInt model = ModelFactory.getInstance().getLibraryModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (id > 0) {
            try {
                LibraryDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, request);
            } catch (Exception e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    // ================== DO POST ==================
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        String op = DataUtility.getString(request.getParameter("operation"));
        LibraryModelInt model = ModelFactory.getInstance().getLibraryModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            LibraryDTO dto = (LibraryDTO) populateDTO(request);

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

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage(
                        "Book Code already exists", request);
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            LibraryDTO dto = (LibraryDTO) populateDTO(request);

            try {
                model.delete(dto);
                ServletUtility.redirect(ORSView.LIBRARY_LIST_CTL,
                        request, response);
                return;
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.LIBRARY_LIST_CTL,
                    request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.LIBRARY_CTL,
                    request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.LIBRARY_VIEW;
    }
}
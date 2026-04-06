package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ClaimDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ClaimModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ClaimListCtl", urlPatterns = { "/ctl/ClaimListCtl" })
public class ClaimListCtl extends BaseCtl {


private static final long serialVersionUID = 1L;
private static Logger log = Logger.getLogger(ClaimListCtl.class);

@Override
protected BaseDTO populateDTO(HttpServletRequest request) {

    ClaimDTO dto = new ClaimDTO();

    dto.setClaimNumber(DataUtility.getString(request.getParameter("claimNumber")));
    dto.setClaimAmount(DataUtility.getLong(request.getParameter("claimAmount")));
    dto.setStatus(DataUtility.getString(request.getParameter("status")));

    populateBean(dto, request);

    return dto;
}

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    log.debug("ClaimListCtl doGet Start");

    List list = null;
    List next = null;

    int pageNo = 1;
    int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

    ClaimDTO dto = (ClaimDTO) populateDTO(request);
    ClaimModelInt model = ModelFactory.getInstance().getClaimModel();

    try {

        list = model.search(dto, pageNo, pageSize);
        next = model.search(dto, pageNo + 1, pageSize);

        ServletUtility.setList(list, request);

        if (list == null || list.size() == 0) {
            ServletUtility.setErrorMessage("No record found", request);
        }

        System.out.println("Data in claim " + list.size());

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

    log.debug("ClaimListCtl doPost Start");

    List list = null;
    List next = null;

    int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
    int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

    pageNo = (pageNo == 0) ? 1 : pageNo;
    pageSize = (pageSize == 0)
            ? DataUtility.getInt(PropertyReader.getValue("page.size"))
            : pageSize;

    ClaimDTO dto = (ClaimDTO) populateDTO(request);
    String op = DataUtility.getString(request.getParameter("operation"));

    String[] ids = request.getParameterValues("ids");

    ClaimModelInt model = ModelFactory.getInstance().getClaimModel();

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

            ServletUtility.redirect(ORSView.CLAIM_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CLAIM_LIST_CTL, request, response);
            return;

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            pageNo = 1;

            if (ids != null && ids.length > 0) {

                for (String id : ids) {
                    ClaimDTO deleteDto = new ClaimDTO();
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

        System.out.println("Data in claim " + list.size());

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
    return ORSView.CLAIM_LIST_VIEW;
}


}

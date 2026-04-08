package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.CurrencyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.CurrencyModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Currency List Controller (Search, List, Delete)
 */
@WebServlet(name = "CurrencyListCtl", urlPatterns = { "/ctl/CurrencyListCtl" })
public class CurrencyListCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(CurrencyListCtl.class);

    /**
     * Populate DTO (Search Filters)
     */
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        log.debug("CurrencyListCtl populateDTO start");

        CurrencyDTO dto = new CurrencyDTO();

        dto.setCurrencyCode(DataUtility.getString(request.getParameter("currencyCode")));
        dto.setCurrencyName(DataUtility.getString(request.getParameter("currencyName")));
        dto.setSymbol(DataUtility.getString(request.getParameter("symbol")));
        dto.setStatus(DataUtility.getString(request.getParameter("status")));

        populateBean(dto, request);

        log.debug("CurrencyListCtl populateDTO end");
        return dto;
    }

    /**
     * Display Logic
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("CurrencyListCtl doGet start");

        List list;
        List next;

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        CurrencyDTO dto = (CurrencyDTO) populateDTO(request);
        CurrencyModelInt model = ModelFactory.getInstance().getCurrencyModel();

        try {
            list = model.search(dto, pageNo, pageSize);
            next = model.search(dto, pageNo + 1, pageSize);

            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", next.size());
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }

        log.debug("CurrencyListCtl doGet end");
    }

    /**
     * Submit Logic
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("CurrencyListCtl doPost start");

        List list;
        List next;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        String op = DataUtility.getString(request.getParameter("operation"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        CurrencyDTO dto = (CurrencyDTO) populateDTO(request);
        CurrencyModelInt model = ModelFactory.getInstance().getCurrencyModel();

        String[] ids = request.getParameterValues("ids");

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
                    || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if ("Next".equalsIgnoreCase(op)) {
                    pageNo++;
                } else if ("Previous".equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.CURRENCY_CTL, request, response);
                return;

            } else if (OP_RESET.equalsIgnoreCase(op)) {

                ServletUtility.redirect(ORSView.CURRENCY_LIST_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {

                    CurrencyDTO deleteBean = new CurrencyDTO();

                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage("Data Deleted Successfully", request);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }
            }

            list = model.search(dto, pageNo, pageSize);
            next = model.search(dto, pageNo + 1, pageSize);

            ServletUtility.setDto(dto, request);

            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No Record Found", request);
            }

            if (next == null || next.size() == 0) {
                request.setAttribute("nextListSize", 0);
            } else {
                request.setAttribute("nextListSize", next.size());
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }

        log.debug("CurrencyListCtl doPost end");
    }

    @Override
    protected String getView() {
        return ORSView.CURRENCY_LIST_VIEW;
    }
}
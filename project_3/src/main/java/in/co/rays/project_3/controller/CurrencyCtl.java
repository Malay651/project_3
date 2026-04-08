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
import in.co.rays.project_3.dto.CurrencyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.CurrencyModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "CurrencyCtl", urlPatterns = { "/ctl/CurrencyCtl" })
public class CurrencyCtl extends BaseCtl {

    private static Logger log = Logger.getLogger(CurrencyCtl.class);

    /**
     * Preload method (for Status dropdown)
     */
    @Override
    protected void preload(HttpServletRequest request) {

        List<String> statusList = new ArrayList<>();

        statusList.add("Active");
        statusList.add("Inactive");

        request.setAttribute("statusList", statusList);
    }

    /**
     * Validation
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("CurrencyCtl validate start");
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("currencyCode"))) {
            request.setAttribute("currencyCode",
                    PropertyReader.getValue("error.require", "Currency Code"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("currencyName"))) {
            request.setAttribute("currencyName",
                    PropertyReader.getValue("error.require", "Currency Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("symbol"))) {
            request.setAttribute("symbol",
                    PropertyReader.getValue("error.require", "Symbol"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("status"))) {
            request.setAttribute("status",
                    PropertyReader.getValue("error.require", "Status"));
            pass = false;
        }

        log.debug("CurrencyCtl validate end");
        return pass;
    }

    /**
     * Populate DTO
     */
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        log.debug("CurrencyCtl populateDTO start");

        CurrencyDTO dto = new CurrencyDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setCurrencyCode(DataUtility.getString(request.getParameter("currencyCode")));
        dto.setCurrencyName(DataUtility.getString(request.getParameter("currencyName")));
        dto.setSymbol(DataUtility.getString(request.getParameter("symbol")));
        dto.setStatus(DataUtility.getString(request.getParameter("status")));

        populateBean(dto, request);

        log.debug("CurrencyCtl populateDTO end");
        return dto;
    }

    /**
     * Display Logic
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("CurrencyCtl doGet start");

        CurrencyModelInt model = ModelFactory.getInstance().getCurrencyModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (id > 0) {
            try {
                CurrencyDTO dto = model.findByPK(id);
                ServletUtility.setDto(dto, request);
            } catch (Exception e) {
                log.error(e);
                ServletUtility.handleDBDown(getView(), request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);

        log.debug("CurrencyCtl doGet end");
    }

    /**
     * Submit Logic
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("CurrencyCtl doPost start");

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        CurrencyModelInt model = ModelFactory.getInstance().getCurrencyModel();

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            CurrencyDTO dto = (CurrencyDTO) populateDTO(request);

            try {
                if (id > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage("Data updated successfully", request);
                } else {
                    try {
                        model.add(dto);
                        ServletUtility.setSuccessMessage("Data saved successfully", request);
                    } catch (DuplicateRecordException e) {
                        ServletUtility.setDto(dto, request);
                        ServletUtility.setErrorMessage("Currency Code already exists", request);
                    }
                }

                ServletUtility.setDto(dto, request);

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleDBDown(getView(), request, response);
                return;
            } catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            CurrencyDTO dto = (CurrencyDTO) populateDTO(request);

            try {
                model.delete(dto);
                ServletUtility.redirect(ORSView.CURRENCY_LIST_CTL, request, response);
                return;
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleDBDown(getView(), request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CURRENCY_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CURRENCY_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);

        log.debug("CurrencyCtl doPost end");
    }

    @Override
    protected String getView() {
        return ORSView.CURRENCY_VIEW;
    }
}
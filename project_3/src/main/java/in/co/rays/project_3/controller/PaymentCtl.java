package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PaymentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.PaymentModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/PaymentCtl" })
public class PaymentCtl extends BaseCtl {

    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(PaymentCtl.class);

    // ================== PRELOAD ==================
    @Override
    protected void preload(HttpServletRequest request) {

        List<String> modeList = new ArrayList<>();

        modeList.add("Cash");
        modeList.add("Card");
        modeList.add("UPI");
        modeList.add("Net Banking");

        request.setAttribute("modeList", modeList);
    }

    // ================== VALIDATE ==================
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        // Payment Code
        if (DataValidator.isNull(request.getParameter("paymentCode"))) {
            request.setAttribute("paymentCode",
                    PropertyReader.getValue("error.require", "Payment Code"));
            pass = false;
        }

        // Amount Required Check
        if (DataValidator.isNull(request.getParameter("amount"))) {
            request.setAttribute("amount",
                    PropertyReader.getValue("error.require", "Amount"));
            pass = false;

        } else {

            Long amount = DataUtility.getLong(request.getParameter("amount"));

            // ✅ NEGATIVE AMOUNT VALIDATION
            if (amount <= 0) {
                request.setAttribute("amount",
                        "Amount must be greater than zero");
                pass = false;
            }
        }

        // Payment Mode
        if (DataValidator.isNull(request.getParameter("paymentMode"))) {
            request.setAttribute("paymentMode",
                    PropertyReader.getValue("error.require", "Payment Mode"));
            pass = false;
        }

        // Payment Date
        if (DataValidator.isNull(request.getParameter("paymentDate"))) {
            request.setAttribute("paymentDate",
                    PropertyReader.getValue("error.require", "Payment Date"));
            pass = false;
        }

        return pass;
    }

    // ================== POPULATE DTO ==================
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        PaymentDTO dto = new PaymentDTO();

        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setPaymentCode(DataUtility.getString(request.getParameter("paymentCode")));
        dto.setAmount(DataUtility.getLong(request.getParameter("amount")));
        dto.setPaymentMode(DataUtility.getString(request.getParameter("paymentMode")));
        dto.setPaymentDate(DataUtility.getDate(request.getParameter("paymentDate")));

        populateBean(dto, request);

        return dto;
    }

    // ================== DO GET ==================
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        PaymentModelInt model = ModelFactory.getInstance().getPaymentModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (id > 0) {
            try {
                PaymentDTO dto = model.findByPK(id);
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
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        String op = DataUtility.getString(request.getParameter("operation"));
        PaymentModelInt model = ModelFactory.getInstance().getPaymentModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            PaymentDTO dto = (PaymentDTO) populateDTO(request);

            try {

                if (id > 0) {
                    model.update(dto);
                    ServletUtility.setSuccessMessage(
                            "Payment Updated Successfully", request);
                } else {
                    model.add(dto);
                    ServletUtility.setSuccessMessage(
                            "Payment Saved Successfully", request);
                }

                ServletUtility.setDto(dto, request);

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setDto(dto, request);
                ServletUtility.setErrorMessage(
                        "Payment Code already exists", request);
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PAYMENT_LIST_CTL,
                    request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.PAYMENT_CTL,
                    request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.PAYMENT_VIEW;
    }
}
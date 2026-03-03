package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.StockDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.StockModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

/**
 * Stock Controller (Add, Update, Delete)
 * 
 * @author malay
 */

@WebServlet(urlPatterns = { "/ctl/StockCtl" })
public class StockCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(StockCtl.class);

	// ---------------- VALIDATION ----------------
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("stockName"))) {
			request.setAttribute("stockName",
					PropertyReader.getValue("error.require", "Stock Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("price"))) {
			request.setAttribute("price",
					PropertyReader.getValue("error.require", "Price"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("quantity"))) {
			request.setAttribute("quantity",
					PropertyReader.getValue("error.require", "Quantity"));
			pass = false;
		}

		return pass;
	}

	// ---------------- POPULATE DTO ----------------
	protected BaseDTO populateDTO(HttpServletRequest request) {

		StockDTO dto = new StockDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setStockName(DataUtility.getString(request.getParameter("stockName")));
		dto.setPrice(DataUtility.getLong(request.getParameter("price")));
		dto.setQuantity(DataUtility.getInt(request.getParameter("quantity")));

		populateBean(dto, request);

		return dto;
	}

	// ---------------- DO GET ----------------
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		StockModelInt model = ModelFactory.getInstance().getStockModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			try {
				StockDTO dto = model.findByPK(id);
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
		StockModelInt model = ModelFactory.getInstance().getStockModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			StockDTO dto = (StockDTO) populateDTO(request);

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
				ServletUtility.setErrorMessage("Stock Name already exists", request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			StockDTO dto = (StockDTO) populateDTO(request);

			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.STOCK_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STOCK_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STOCK_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.STOCK_VIEW;
	}
}
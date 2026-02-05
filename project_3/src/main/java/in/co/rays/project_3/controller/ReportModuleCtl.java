package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.ReportModuleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.ReportModuleModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet("/ReportModuleCtl")
public class ReportModuleCtl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        String operation = request.getParameter("operation");

        ReportModuleModelInt model= ModelFactory.getInstance().getreportmodule();

        if (id > 0) {
        	ReportModuleDTO dto = null;
			try {
				dto = model.findByPK(id);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ServletUtility.setDto(dto, request);
        }

        if ("DELETE".equals(operation)) {
            ReportModuleDTO dto = new ReportModuleDTO();
            dto.setReportId(id);
            try {
				model.delete(dto);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ServletUtility.redirect("ReportModuleListCtl", request, response);
            return;
        }

        ServletUtility.forward("ReportModuleView.jsp", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");
        ReportModuleModelInt model= ModelFactory.getInstance().getreportmodule();

        if ("SAVE".equals(operation) || "UPDATE".equals(operation)) {

            ReportModuleDTO dto = new ReportModuleDTO();
            dto.setReportId(DataUtility.getLong(request.getParameter("reportId")));
            dto.setReportName(request.getParameter("reportName"));
            dto.setGeneratedDate(DataUtility.getDate(request.getParameter("generatedDate")));
            dto.setGeneratedBy(request.getParameter("generatedBy"));
            dto.setReportStatus(request.getParameter("reportStatus"));

            if (dto.getReportId() > 0) {
                try {
					model.update(dto);
				} catch (ApplicationException | DuplicateRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                ServletUtility.setSuccessMessage("Report Updated Successfully", request);
            } else {
                try {
					model.add(dto);
				} catch (ApplicationException | DuplicateRecordException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                ServletUtility.setSuccessMessage("Report Added Successfully", request);
            }
        }

        ServletUtility.forward("ReportModuleView.jsp", request, response);
    }
}

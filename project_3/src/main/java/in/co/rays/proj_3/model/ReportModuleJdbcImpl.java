package in.co.rays.proj_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project_3.dto.ReportModuleDTO;
import in.co.rays.project_3.util.JDBCDataSource;

public class ReportModuleJdbcImpl {

    /* ================= ADD ================= */
    public long add(ReportModuleDTO dto) {
        long pk = 0;
        String sql = "INSERT INTO ST_REPORT "
                   + "(REPORT_ID, REPORT_NAME, GENERATED_DATE, GENERATED_BY, REPORT_STATUS) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            pk = nextPK();
            ps.setLong(1, pk);
            ps.setString(2, dto.getReportName());
            ps.setDate(3, new java.sql.Date(dto.getGeneratedDate().getTime()));
            ps.setString(4, dto.getGeneratedBy());
            ps.setString(5, dto.getReportStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pk;
    }

    /* ================= UPDATE ================= */
    public void update(ReportModuleDTO dto) {

        String sql = "UPDATE ST_REPORT SET REPORT_NAME=?, GENERATED_DATE=?, "
                   + "GENERATED_BY=?, REPORT_STATUS=? WHERE REPORT_ID=?";

        try (Connection conn = JDBCDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getReportName());
            ps.setDate(2, new java.sql.Date(dto.getGeneratedDate().getTime()));
            ps.setString(3, dto.getGeneratedBy());
            ps.setString(4, dto.getReportStatus());
            ps.setLong(5, dto.getReportId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= DELETE ================= */
    public void delete(long reportId) {

        String sql = "DELETE FROM ST_REPORT WHERE REPORT_ID=?";

        try (Connection conn = JDBCDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, reportId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= SEARCH ================= */
    public List<ReportModuleDTO> search(ReportModuleDTO dto) {

        List<ReportModuleDTO> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT * FROM ST_REPORT WHERE 1=1");

        if (dto != null) {
            if (dto.getReportName() != null && dto.getReportName().length() > 0) {
                sql.append(" AND REPORT_NAME LIKE '" + dto.getReportName() + "%'");
            }
            if (dto.getReportStatus() != null && dto.getReportStatus().length() > 0) {
                sql.append(" AND REPORT_STATUS='" + dto.getReportStatus() + "'");
            }
        }

        try (Connection conn = JDBCDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ReportModuleDTO rDto = new ReportModuleDTO();
                rDto.setReportId(rs.getLong("REPORT_ID"));
                rDto.setReportName(rs.getString("REPORT_NAME"));
                rDto.setGeneratedDate(rs.getDate("GENERATED_DATE"));
                rDto.setGeneratedBy(rs.getString("GENERATED_BY"));
                rDto.setReportStatus(rs.getString("REPORT_STATUS"));
                list.add(rDto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ================= NEXT PK ================= */
    private long nextPK() {

        long pk = 0;
        String sql = "SELECT MAX(REPORT_ID) FROM ST_REPORT";

        try (Connection conn = JDBCDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                pk = rs.getLong(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pk + 1;
    }
}

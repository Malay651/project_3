package in.co.rays.project_3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.project_3.dto.ContactDTO;
import in.co.rays.project_3.dto.CourseDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DatabaseException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.JDBCDataSource;

public class ContactModelJDBCImpl implements ContactModelInt {

	
	@Override
	public long add(ContactDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		long pk = 0;
        Connection con = null;
		String query = "insert into st_contact values(?,?,?,?,?,?,?,?,?)";
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(query);
			ps.setLong(1, pk);
			ps.setString(2, dto.getName());
			ps.setString(3, dto.getEmail());
			ps.setString(4, dto.getMobileNo());
            ps.setString(5, dto.getMessage());
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());
			ps.setTimestamp(9, dto.getModifiedDatetime());

			int a = ps.executeUpdate();
			System.out.println("ok:");
			ps.close();
			con.commit();
		} catch (Exception e) {
		
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in contact");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		
		return 0;

	}

	@Override
	public void delete(ContactDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("delete from ST_contact where id=?");
			ps.setLong(1, dto.getId());
			ps.executeUpdate();
			con.commit();
			ps.close();

		} catch (Exception e) {
			
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete contact");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		


	}

	@Override
	public void update(ContactDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		ContactDTO dtoExist = findByLogin(dto.getEmail());
		// Check if updated LoginId already exist
		if (dtoExist != null && !(dtoExist.getId() == dto.getId())) {
			throw new DuplicateRecordException("LoginId is already exist");
		}
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(
					"update ST_contact set name=?,email=?,mobileno=?,message=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getEmail());
			ps.setString(3, dto.getMobileNo());
            ps.setString(4, dto.getMessage());
			ps.setString(5, dto.getCreatedBy());
			ps.setString(6, dto.getModifiedBy());
			ps.setTimestamp(7, dto.getCreatedDatetime());
			ps.setTimestamp(8, dto.getModifiedDatetime());

	ps.executeUpdate();
			con.commit();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				con.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

	}

	@Override
	public ContactDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		ContactDTO dto = null;
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement("select * from ST_contact where id=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new ContactDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setEmail(rs.getString(3));
				dto.setMobileNo(rs.getString(4));
				dto.setMessage(rs.getString(5));
				dto.setCreatedBy(rs.getString(6));
				dto.setModifiedBy(rs.getString(7));
				dto.setCreatedDatetime(rs.getTimestamp(8));
				dto.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting contact by pk");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		
		return dto;

	}

	@Override
	public List search(ContactDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		ArrayList array = null;
		StringBuffer sql = new StringBuffer("select * from ST_contact where 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				sql.append(" AND FIRSTNAME like '" + dto.getName() + "%'");
			}
			if (dto.getEmail() != null && dto.getEmail().length() > 0) {
				sql.append(" AND EMAIL like '" + dto.getEmail() + "%'");
			}
			
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit" + pageNo + "," + pageSize);
		}
		array = new ArrayList();
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new ContactDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setEmail(rs.getString(3));
				dto.setMobileNo(rs.getString(4));
				dto.setMessage(rs.getString(5));
				dto.setCreatedBy(rs.getString(6));
				dto.setModifiedBy(rs.getString(7));
				dto.setCreatedDatetime(rs.getTimestamp(8));
				dto.setModifiedDatetime(rs.getTimestamp(9));
			
				array.add(dto);
			}
			rs.close();

		} catch (Exception e) {
			
			throw new ApplicationException("Exception : Exception in search contact");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		

		return array;

	}

	@Override
	public ContactDTO findByLogin(String login) throws ApplicationException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		ContactDTO dto = null;
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement("select * from ST_contact where LOGIN=?");
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new ContactDTO();
				dto.setId(rs.getLong(1));
				dto.setName(rs.getString(2));
				dto.setEmail(rs.getString(3));
				dto.setMobileNo(rs.getString(4));
				dto.setMessage(rs.getString(5));
				dto.setCreatedBy(rs.getString(6));
				dto.setModifiedBy(rs.getString(7));
				dto.setCreatedDatetime(rs.getTimestamp(8));
				dto.setModifiedDatetime(rs.getTimestamp(9));
				}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ApplicationException("Exception : Exception in getting User by login");
		} finally {
			JDBCDataSource.closeConnection(con);
		}
		

		return dto;

	}

	
}

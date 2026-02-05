package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ReportModuleDTO;
import in.co.rays.project_3.dto.RoleDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

public class ReportModuleHibImpl implements ReportModuleModelInt {


		
	
	public List list() throws ApplicationException {
		
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Users list");
		} finally {
			session.close();
		}

		return list;
	}

	
	
	@Override
	public long add(ReportModuleDTO dto) throws ApplicationException, DuplicateRecordException {
	
		
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			int pk = 0;
			tx = session.beginTransaction();

			System.out.println("trac1");
			session.save(dto);
			System.out.println("trac2");
			tx.commit();
			System.out.println("trac3");
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in User Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getReportId();


	}

	@Override
	public void delete(ReportModuleDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void update(ReportModuleDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in User update" + e.getMessage());
		} finally {
			session.close();
		}

	}

	@Override
	public List search(ReportModuleDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
		
	}

	@Override
	public List search(ReportModuleDTO dto, int pageNo, int pageSize) throws ApplicationException {
	
		Session session = null;
		ArrayList<UserDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ReportModuleDTO.class);
			if (dto != null) {
				if (dto.getReportId() != null) {
					criteria.add(Restrictions.like("reportid", dto.getReportId()));
				}
				if (dto.getReportName() != null && dto.getReportName().length() > 0) {
					criteria.add(Restrictions.like("reportName", dto.getReportName() + "%"));
				}

				if (dto.getGeneratedDate() != null && dto.getGeneratedDate().getTime() > 0) {
					criteria.add(Restrictions.eq("generateddate", dto.getGeneratedDate()));
				}
				if (dto.getGeneratedBy() != null && dto.getGeneratedBy().length() > 0) {
					criteria.add(Restrictions.eq("lastLogin", dto.getGeneratedBy()));
				}
				if (dto.getReportStatus() != null && dto.getGeneratedBy().length() > 0) {
					criteria.add(Restrictions.eq("reportstatus", dto.getReportStatus()));
				}
			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<UserDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in user search");
		} finally {
			session.close();
		}

		return list;

	}

	@Override
	public ReportModuleDTO authenticate(String login, String password) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		ReportModuleDTO dto = null;
		session = HibDataSource.getSession();
		Query q = session.createQuery("from DTO where login=? and password=?");
		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (ReportModuleDTO) list.get(0);
		} else {
			dto = null;

		}
		return dto;

	}

	@Override
	public ReportModuleDTO findByPK(long pk) throws ApplicationException {
		Session session = null;
		ReportModuleDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (ReportModuleDTO) session.get(ReportModuleDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			session.close();
		}

		return dto;

	}

	

}

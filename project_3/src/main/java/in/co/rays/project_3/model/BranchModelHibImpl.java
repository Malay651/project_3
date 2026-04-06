package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BranchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**

* Hibernate implementation of Branch Model
* @author malay
  */

public class BranchModelHibImpl implements BranchModelInt {


// -------------------- ADD --------------------
public long add(BranchDTO dto) throws ApplicationException, DuplicateRecordException {

	BranchDTO existDto = findByBranchCode(dto.getBranchCode());
	if (existDto != null) {
		throw new DuplicateRecordException("Branch Code already exists");
	}

	Session session = HibDataSource.getSession();
	Transaction tx = null;

	try {
		tx = session.beginTransaction();
		session.save(dto);
		tx.commit();
	} catch (HibernateException e) {
		if (tx != null)
			tx.rollback();
		throw new ApplicationException("Exception in Branch Add " + e.getMessage());
	} finally {
		session.close();
	}

	return dto.getId();
}

// -------------------- DELETE --------------------
public void delete(BranchDTO dto) throws ApplicationException {

	Session session = null;
	Transaction tx = null;

	try {
		session = HibDataSource.getSession();
		tx = session.beginTransaction();
		session.delete(dto);
		tx.commit();
	} catch (HibernateException e) {
		if (tx != null)
			tx.rollback();
		throw new ApplicationException("Exception in Branch Delete " + e.getMessage());
	} finally {
		session.close();
	}
}

// -------------------- UPDATE --------------------
public void update(BranchDTO dto) throws ApplicationException, DuplicateRecordException {

	BranchDTO existDto = findByBranchCode(dto.getBranchCode());

	if (existDto != null && existDto.getId() != dto.getId()) {
		throw new DuplicateRecordException("Branch Code already exists");
	}

	Session session = null;
	Transaction tx = null;

	try {
		session = HibDataSource.getSession();
		tx = session.beginTransaction();
		session.saveOrUpdate(dto);
		tx.commit();
	} catch (HibernateException e) {
		if (tx != null)
			tx.rollback();
		throw new ApplicationException("Exception in Branch Update " + e.getMessage());
	} finally {
		session.close();
	}
}

// -------------------- FIND BY PK --------------------
public BranchDTO findByPK(long pk) throws ApplicationException {

	Session session = null;
	BranchDTO dto = null;

	try {
		session = HibDataSource.getSession();
		dto = (BranchDTO) session.get(BranchDTO.class, pk);
	} catch (HibernateException e) {
		throw new ApplicationException("Exception in getting Branch by PK");
	} finally {
		session.close();
	}

	return dto;
}

// -------------------- FIND BY BRANCH CODE --------------------
public BranchDTO findByBranchCode(String branchCode) throws ApplicationException {

	Session session = null;
	BranchDTO dto = null;

	try {
		session = HibDataSource.getSession();
		Criteria criteria = session.createCriteria(BranchDTO.class);
		criteria.add(Restrictions.eq("branchCode", branchCode));

		List list = criteria.list();
		if (list.size() == 1) {
			dto = (BranchDTO) list.get(0);
		}

	} catch (HibernateException e) {
		throw new ApplicationException("Exception in getting Branch by Code " + e.getMessage());
	} finally {
		session.close();
	}

	return dto;
}

// -------------------- LIST --------------------
public List list() throws ApplicationException {
	return list(0, 0);
}

public List list(int pageNo, int pageSize) throws ApplicationException {

	Session session = null;
	List list = null;

	try {
		session = HibDataSource.getSession();
		Criteria criteria = session.createCriteria(BranchDTO.class);

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			criteria.setFirstResult(pageNo);
			criteria.setMaxResults(pageSize);
		}

		list = criteria.list();

	} catch (HibernateException e) {
		throw new ApplicationException("Exception in Branch List");
	} finally {
		session.close();
	}

	return list;
}

// -------------------- SEARCH --------------------
public List search(BranchDTO dto) throws ApplicationException {
	return search(dto, 0, 0);
}

public List search(BranchDTO dto, int pageNo, int pageSize) throws ApplicationException {

	Session session = null;
	List list = null;

	try {
		session = HibDataSource.getSession();
		Criteria criteria = session.createCriteria(BranchDTO.class);

		if (dto != null) {

			if (dto.getId() != null) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}

			if (dto.getBranchCode() != null && dto.getBranchCode().length() > 0) {
				criteria.add(Restrictions.like("branchCode", dto.getBranchCode() + "%"));
			}

			if (dto.getBranchName() != null && dto.getBranchName().length() > 0) {
				criteria.add(Restrictions.like("branchName", dto.getBranchName() + "%"));
			}

			if (dto.getBranchLocation() != null && dto.getBranchLocation().length() > 0) {
				criteria.add(Restrictions.like("branchLocation", dto.getBranchLocation() + "%"));
			}

			if (dto.getBranchStatus() != null && dto.getBranchStatus().length() > 0) {
				criteria.add(Restrictions.like("branchStatus", dto.getBranchStatus() + "%"));
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			criteria.setFirstResult(pageNo);
			criteria.setMaxResults(pageSize);
		}

		list = criteria.list();

	} catch (HibernateException e) {
		throw new ApplicationException("Exception in Branch Search");
	} finally {
		session.close();
	}

	return list;
}


}

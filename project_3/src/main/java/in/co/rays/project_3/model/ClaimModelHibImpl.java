package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.ClaimDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**

* Hibernate implementation of Claim Model
* @author malay
  */

public class ClaimModelHibImpl implements ClaimModelInt {


// -------------------- ADD --------------------
public long add(ClaimDTO dto) throws ApplicationException, DuplicateRecordException {

    ClaimDTO existDto = findByClaimNumber(dto.getClaimNumber());
    if (existDto != null) {
        throw new DuplicateRecordException("Claim Number already exists");
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
        throw new ApplicationException("Exception in Claim Add " + e.getMessage());
    } finally {
        session.close();
    }

    return dto.getId();
}

// -------------------- DELETE --------------------
public void delete(ClaimDTO dto) throws ApplicationException {

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
        throw new ApplicationException("Exception in Claim Delete " + e.getMessage());
    } finally {
        session.close();
    }
}

// -------------------- UPDATE --------------------
public void update(ClaimDTO dto) throws ApplicationException, DuplicateRecordException {

    ClaimDTO existDto = findByClaimNumber(dto.getClaimNumber());

    if (existDto != null && existDto.getId() != dto.getId()) {
        throw new DuplicateRecordException("Claim Number already exists");
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
        throw new ApplicationException("Exception in Claim Update " + e.getMessage());
    } finally {
        session.close();
    }
}

// -------------------- FIND BY PK --------------------
public ClaimDTO findByPK(long pk) throws ApplicationException {

    Session session = null;
    ClaimDTO dto = null;

    try {
        session = HibDataSource.getSession();
        dto = (ClaimDTO) session.get(ClaimDTO.class, pk);
    } catch (HibernateException e) {
        throw new ApplicationException("Exception in getting Claim by PK");
    } finally {
        session.close();
    }

    return dto;
}

// -------------------- FIND BY CLAIM NUMBER --------------------
public ClaimDTO findByClaimNumber(String claimNumber) throws ApplicationException {

    Session session = null;
    ClaimDTO dto = null;

    try {
        session = HibDataSource.getSession();
        Criteria criteria = session.createCriteria(ClaimDTO.class);
        criteria.add(Restrictions.eq("claimNumber", claimNumber));

        List list = criteria.list();
        if (list.size() == 1) {
            dto = (ClaimDTO) list.get(0);
        }

    } catch (HibernateException e) {
        throw new ApplicationException("Exception in getting Claim by Number " + e.getMessage());
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
        Criteria criteria = session.createCriteria(ClaimDTO.class);

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            criteria.setFirstResult(pageNo);
            criteria.setMaxResults(pageSize);
        }

        list = criteria.list();

    } catch (HibernateException e) {
        throw new ApplicationException("Exception in Claim List");
    } finally {
        session.close();
    }

    return list;
}

// -------------------- SEARCH --------------------
public List search(ClaimDTO dto) throws ApplicationException {
    return search(dto, 0, 0);
}

public List search(ClaimDTO dto, int pageNo, int pageSize) throws ApplicationException {

    Session session = null;
    List list = null;

    try {
        session = HibDataSource.getSession();
        Criteria criteria = session.createCriteria(ClaimDTO.class);

        if (dto != null) {

            if (dto.getId() != null) {
                criteria.add(Restrictions.eq("id", dto.getId()));
            }

            if (dto.getClaimNumber() != null && dto.getClaimNumber().length() > 0) {
                criteria.add(Restrictions.like("claimNumber", dto.getClaimNumber() + "%"));
            }

            if (dto.getClaimAmount() != null && dto.getClaimAmount() > 0) {
                criteria.add(Restrictions.eq("claimAmount", dto.getClaimAmount()));
            }

            if (dto.getStatus() != null && dto.getStatus().length() > 0) {
                criteria.add(Restrictions.like("status", dto.getStatus() + "%"));
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            criteria.setFirstResult(pageNo);
            criteria.setMaxResults(pageSize);
        }

        list = criteria.list();

    } catch (HibernateException e) {
        throw new ApplicationException("Exception in Claim Search");
    } finally {
        session.close();
    }

    return list;
}


}

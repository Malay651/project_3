package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.CurrencyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Currency Model
 */
public class CurrencyModelHibImpl implements CurrencyModelInt {

    public long add(CurrencyDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;
        long pk = 0;

        CurrencyDTO existDto = findByCurrencyCode(dto.getCurrencyCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Currency already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.save(dto);
            pk = dto.getId();
            tx.commit();

        } catch (HibernateException e) {

            if (tx != null) {
                tx.rollback();
            }
            throw new ApplicationException("Exception in Currency Add " + e.getMessage());

        } finally {
            session.close();
        }

        return pk;
    }

    public void delete(CurrencyDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Currency delete " + e.getMessage());

        } finally {
            session.close();
        }
    }

    public void update(CurrencyDTO dto) throws ApplicationException, DuplicateRecordException {

        Session session = null;
        Transaction tx = null;

        CurrencyDTO existDto = findByCurrencyCode(dto.getCurrencyCode());
        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Currency already exists");
        }

        try {
            session = HibDataSource.getSession();
            tx = session.beginTransaction();
            session.update(dto);
            tx.commit();

        } catch (HibernateException e) {

            if (tx != null) {
                tx.rollback();
            }
            throw new ApplicationException("Exception in Currency update " + e.getMessage());

        } finally {
            session.close();
        }
    }

    public CurrencyDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        CurrencyDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (CurrencyDTO) session.get(CurrencyDTO.class, pk);

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in getting Currency by PK");

        } finally {
            session.close();
        }

        return dto;
    }

    public CurrencyDTO findByCurrencyCode(String currencyCode) throws ApplicationException {

        Session session = null;
        CurrencyDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CurrencyDTO.class);
            criteria.add(Restrictions.eq("currencyCode", currencyCode));

            List list = criteria.list();
            if (list.size() > 0) {
                dto = (CurrencyDTO) list.get(0);
            }

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in getting Currency by Code " + e.getMessage());

        } finally {
            session.close();
        }

        return dto;
    }

    public List list() throws ApplicationException {
        return list(0, 0);
    }

    public List list(int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CurrencyDTO.class);

            if (pageSize > 0) {
                pageNo = ((pageNo - 1) * pageSize);
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Currency list");

        } finally {
            session.close();
        }

        return list;
    }

    public List search(CurrencyDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(CurrencyDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CurrencyDTO.class);

            if (dto.getId() > 0) {
                criteria.add(Restrictions.eq("id", dto.getId()));
            }

            if (dto.getCurrencyCode() != null && dto.getCurrencyCode().length() > 0) {
                criteria.add(Restrictions.like("currencyCode", dto.getCurrencyCode() + "%"));
            }

            if (dto.getCurrencyName() != null && dto.getCurrencyName().length() > 0) {
                criteria.add(Restrictions.like("currencyName", dto.getCurrencyName() + "%"));
            }

            if (dto.getSymbol() != null && dto.getSymbol().length() > 0) {
                criteria.add(Restrictions.like("symbol", dto.getSymbol() + "%"));
            }

            if (dto.getStatus() != null && dto.getStatus().length() > 0) {
                criteria.add(Restrictions.eq("status", dto.getStatus()));
            }

            if (pageSize > 0) {
                criteria.setFirstResult((pageNo - 1) * pageSize);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {

            throw new ApplicationException("Exception in Currency search");

        } finally {
            session.close();
        }

        return list;
    }
}
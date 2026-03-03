package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.StockDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Stock Model
 * 
 * @author malay
 */
public class StockModelHibImpl implements StockModelInt {

    // -------------------- ADD --------------------
    public long add(StockDTO dto) throws ApplicationException, DuplicateRecordException {

        StockDTO existDto = findByStockName(dto.getStockName());
        if (existDto != null) {
            throw new DuplicateRecordException("Stock Name already exists");
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
            throw new ApplicationException("Exception in Stock Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    // -------------------- DELETE --------------------
    public void delete(StockDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Stock Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // -------------------- UPDATE --------------------
    public void update(StockDTO dto) throws ApplicationException, DuplicateRecordException {

        StockDTO existDto = findByStockName(dto.getStockName());

        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Stock Name already exists");
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
            throw new ApplicationException("Exception in Stock Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // -------------------- FIND BY PK --------------------
    public StockDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        StockDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (StockDTO) session.get(StockDTO.class, pk);
        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Stock by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    // -------------------- FIND BY STOCK NAME --------------------
    public StockDTO findByStockName(String stockName) throws ApplicationException {

        Session session = null;
        StockDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(StockDTO.class);
            criteria.add(Restrictions.eq("stockName", stockName));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (StockDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Stock by Name " + e.getMessage());
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
            Criteria criteria = session.createCriteria(StockDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Stock List");
        } finally {
            session.close();
        }

        return list;
    }

    // -------------------- SEARCH --------------------
    public List search(StockDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(StockDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        List list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(StockDTO.class);

            if (dto != null) {

                if (dto.getId() != null) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getStockName() != null && dto.getStockName().length() > 0) {
                    criteria.add(Restrictions.like("stockName", dto.getStockName() + "%"));
                }

                if (dto.getPrice() != null && dto.getPrice() > 0) {
                    criteria.add(Restrictions.eq("price", dto.getPrice()));
                }

            }

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Stock Search");
        } finally {
            session.close();
        }

        return list;
    }
}
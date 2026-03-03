package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.BatchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Batch Model
 * 
 * @author malay
 */
public class BatchModelHibImpl implements BatchModelInt {

    // -------------------- ADD --------------------
    public long add(BatchDTO dto) throws ApplicationException, DuplicateRecordException {

        BatchDTO existDto = findByBatchCode(dto.getBatchCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Batch Code already exists");
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
            throw new ApplicationException("Exception in Batch Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    // -------------------- DELETE --------------------
    public void delete(BatchDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Batch Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // -------------------- UPDATE --------------------
    public void update(BatchDTO dto) throws ApplicationException, DuplicateRecordException {

        BatchDTO existDto = findByBatchCode(dto.getBatchCode());

        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Batch Code already exists");
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
            throw new ApplicationException("Exception in Batch Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // -------------------- FIND BY PK --------------------
    public BatchDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        BatchDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (BatchDTO) session.get(BatchDTO.class, pk);
        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Batch by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    // -------------------- FIND BY BATCH CODE --------------------
    public BatchDTO findByBatchCode(String batchCode) throws ApplicationException {

        Session session = null;
        BatchDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BatchDTO.class);
            criteria.add(Restrictions.eq("batchCode", batchCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (BatchDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Batch by Code " + e.getMessage());
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
            Criteria criteria = session.createCriteria(BatchDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Batch List");
        } finally {
            session.close();
        }

        return list;
    }

    // -------------------- SEARCH --------------------
    public List search(BatchDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(BatchDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        ArrayList<BatchDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(BatchDTO.class);

            if (dto != null) {

                if (dto.getId() != null) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getBatchCode() != null && dto.getBatchCode().length() > 0) {
                    criteria.add(Restrictions.like("batchCode", dto.getBatchCode() + "%"));
                }

                if (dto.getBatchName() != null && dto.getBatchName().length() > 0) {
                    criteria.add(Restrictions.like("batchName", dto.getBatchName() + "%"));
                }

              
            }

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<BatchDTO>) criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Batch Search");
        } finally {
            session.close();
        }

        return list;
    }
}
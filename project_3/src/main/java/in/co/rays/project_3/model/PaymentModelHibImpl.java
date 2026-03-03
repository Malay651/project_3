package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.PaymentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Payment Model
 * 
 * @author malay
 */
public class PaymentModelHibImpl implements PaymentModelInt {

    // -------------------- ADD --------------------
    public long add(PaymentDTO dto) throws ApplicationException, DuplicateRecordException {

        PaymentDTO existDto = findByPaymentCode(dto.getPaymentCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Payment Code already exists");
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
            throw new ApplicationException("Exception in Payment Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    // -------------------- DELETE --------------------
    public void delete(PaymentDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Payment Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // -------------------- UPDATE --------------------
    public void update(PaymentDTO dto) throws ApplicationException, DuplicateRecordException {

        PaymentDTO existDto = findByPaymentCode(dto.getPaymentCode());

        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Payment Code already exists");
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
            throw new ApplicationException("Exception in Payment Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // -------------------- FIND BY PK --------------------
    public PaymentDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        PaymentDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (PaymentDTO) session.get(PaymentDTO.class, pk);
        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Payment by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    // -------------------- FIND BY PAYMENT CODE --------------------
    public PaymentDTO findByPaymentCode(String paymentCode) throws ApplicationException {

        Session session = null;
        PaymentDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PaymentDTO.class);
            criteria.add(Restrictions.eq("paymentCode", paymentCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (PaymentDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Payment by Code " + e.getMessage());
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
            Criteria criteria = session.createCriteria(PaymentDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Payment List");
        } finally {
            session.close();
        }

        return list;
    }

    // -------------------- SEARCH --------------------
    public List search(PaymentDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(PaymentDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        ArrayList<PaymentDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(PaymentDTO.class);

            if (dto != null) {

                if (dto.getId() != null) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getPaymentCode() != null && dto.getPaymentCode().length() > 0) {
                    criteria.add(Restrictions.like("paymentCode", dto.getPaymentCode() + "%"));
                }

                if (dto.getPaymentMode() != null && dto.getPaymentMode().length() > 0) {
                    criteria.add(Restrictions.like("paymentMode", dto.getPaymentMode() + "%"));
                }

                if (dto.getAmount() != null) {
                    criteria.add(Restrictions.eq("amount", dto.getAmount()));
                }

                if (dto.getPaymentDate() != null) {
                    criteria.add(Restrictions.eq("paymentDate", dto.getPaymentDate()));
                }
            }

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<PaymentDTO>) criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Payment Search");
        } finally {
            session.close();
        }

        return list;
    }
}
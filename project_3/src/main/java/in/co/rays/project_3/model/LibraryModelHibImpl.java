package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.LibraryDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of Library Model
 * 
 * @author malay
 */
public class LibraryModelHibImpl implements LibraryModelInt {

    // -------------------- ADD --------------------
    public long add(LibraryDTO dto) throws ApplicationException, DuplicateRecordException {

        LibraryDTO existDto = findByBookCode(dto.getBookCode());
        if (existDto != null) {
            throw new DuplicateRecordException("Book Code already exists");
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
            throw new ApplicationException("Exception in Library Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    // -------------------- DELETE --------------------
    public void delete(LibraryDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Library Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // -------------------- UPDATE --------------------
    public void update(LibraryDTO dto) throws ApplicationException, DuplicateRecordException {

        LibraryDTO existDto = findByBookCode(dto.getBookCode());

        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Book Code already exists");
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
            throw new ApplicationException("Exception in Library Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // -------------------- FIND BY PK --------------------
    public LibraryDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        LibraryDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (LibraryDTO) session.get(LibraryDTO.class, pk);
        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Library by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    // -------------------- FIND BY BOOK CODE --------------------
    public LibraryDTO findByBookCode(String bookCode) throws ApplicationException {

        Session session = null;
        LibraryDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(LibraryDTO.class);
            criteria.add(Restrictions.eq("bookCode", bookCode));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (LibraryDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Library by Code " + e.getMessage());
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
            Criteria criteria = session.createCriteria(LibraryDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Library List");
        } finally {
            session.close();
        }

        return list;
    }

    // -------------------- SEARCH --------------------
    public List search(LibraryDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(LibraryDTO dto, int pageNo, int pageSize) throws ApplicationException {

        Session session = null;
        ArrayList<LibraryDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(LibraryDTO.class);

            if (dto != null) {

                if (dto.getId() != null) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getBookCode() != null && dto.getBookCode().length() > 0) {
                    criteria.add(Restrictions.like("bookCode", dto.getBookCode() + "%"));
                }

                if (dto.getBookName() != null && dto.getBookName().length() > 0) {
                    criteria.add(Restrictions.like("bookName", dto.getBookName() + "%"));
                }

                if (dto.getAuthorName() != null && dto.getAuthorName().length() > 0) {
                    criteria.add(Restrictions.like("authorName", dto.getAuthorName() + "%"));
                }

                if (dto.getAvailabilityStatus() != null && dto.getAvailabilityStatus().length() > 0) {
                    criteria.add(Restrictions.like("availability", dto.getAvailabilityStatus() + "%"));
                }
            }

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<LibraryDTO>) criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Library Search");
        } finally {
            session.close();
        }

        return list;
    }
}
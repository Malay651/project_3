package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.JobApplicationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**
 * Hibernate implementation of JobApplication Model
 * 
 * @author malay
 */
public class JobApplicationModelHibImpl implements JobApplicationModelInt {

    // -------------------- ADD --------------------
    public long add(JobApplicationDTO dto)
            throws ApplicationException, DuplicateRecordException {

        JobApplicationDTO existDto = findByApplicationName(dto.getApplicationName());
        if (existDto != null) {
            throw new DuplicateRecordException("Application Name already exists");
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
            throw new ApplicationException("Exception in Application Add " + e.getMessage());
        } finally {
            session.close();
        }

        return dto.getId();
    }

    // -------------------- DELETE --------------------
    public void delete(JobApplicationDTO dto) throws ApplicationException {

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
            throw new ApplicationException("Exception in Application Delete " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // -------------------- UPDATE --------------------
    public void update(JobApplicationDTO dto)
            throws ApplicationException, DuplicateRecordException {

        JobApplicationDTO existDto = findByApplicationName(dto.getApplicationName());

        if (existDto != null && existDto.getId() != dto.getId()) {
            throw new DuplicateRecordException("Application Name already exists");
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
            throw new ApplicationException("Exception in Application Update " + e.getMessage());
        } finally {
            session.close();
        }
    }

    // -------------------- FIND BY PK --------------------
    public JobApplicationDTO findByPK(long pk) throws ApplicationException {

        Session session = null;
        JobApplicationDTO dto = null;

        try {
            session = HibDataSource.getSession();
            dto = (JobApplicationDTO) session.get(JobApplicationDTO.class, pk);
        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Application by PK");
        } finally {
            session.close();
        }

        return dto;
    }

    // -------------------- FIND BY APPLICATION NAME --------------------
    public JobApplicationDTO findByApplicationName(String applicationName)
            throws ApplicationException {

        Session session = null;
        JobApplicationDTO dto = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(JobApplicationDTO.class);
            criteria.add(Restrictions.eq("applicationName", applicationName));

            List list = criteria.list();
            if (list.size() == 1) {
                dto = (JobApplicationDTO) list.get(0);
            }

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in getting Application by Name " + e.getMessage());
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
            Criteria criteria = session.createCriteria(JobApplicationDTO.class);

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Application List");
        } finally {
            session.close();
        }

        return list;
    }

    // -------------------- SEARCH --------------------
    public List search(JobApplicationDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    public List search(JobApplicationDTO dto, int pageNo, int pageSize)
            throws ApplicationException {

        Session session = null;
        ArrayList<JobApplicationDTO> list = null;

        try {
            session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(JobApplicationDTO.class);

            if (dto != null) {

                if (dto.getId() != null) {
                    criteria.add(Restrictions.eq("id", dto.getId()));
                }

                if (dto.getApplicationName() != null
                        && dto.getApplicationName().length() > 0) {
                    criteria.add(Restrictions.like("applicationName",
                            dto.getApplicationName() + "%"));
                }

                if (dto.getJobTitle() != null
                        && dto.getJobTitle().length() > 0) {
                    criteria.add(Restrictions.like("jobTitle",
                            dto.getJobTitle() + "%"));
                }

                if (dto.getApplicationStatus() != null
                        && dto.getApplicationStatus().length() > 0) {
                    criteria.add(Restrictions.eq("applicationStatus",
                            dto.getApplicationStatus()));
                }

                if (dto.getApplicationDate() != null) {
                    criteria.add(Restrictions.eq("applicationDate",
                            dto.getApplicationDate()));
                }

                if (dto.getInterviewDate() != null) {
                    criteria.add(Restrictions.eq("interviewDate",
                            dto.getInterviewDate()));
                }
            }

            if (pageSize > 0) {
                pageNo = (pageNo - 1) * pageSize;
                criteria.setFirstResult(pageNo);
                criteria.setMaxResults(pageSize);
            }

            list = (ArrayList<JobApplicationDTO>) criteria.list();

        } catch (HibernateException e) {
            throw new ApplicationException("Exception in Application Search");
        } finally {
            session.close();
        }

        return list;
    }
}
package in.co.rays.project_3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.TourGuideBookingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.util.HibDataSource;

/**

* Hibernate implementation of Tour Guide Booking Model
  */
  public class TourGuideBookingModelHibImpl implements TourGuideBookingModelInt {

  // -------------------- ADD --------------------
  public long add(TourGuideBookingDTO dto) throws ApplicationException, DuplicateRecordException {

  
   TourGuideBookingDTO existDto = findByTouristName(dto.getTouristName());
   if (existDto != null) {
       throw new DuplicateRecordException("Tourist Name already exists");
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
       throw new ApplicationException("Exception in TourGuideBooking Add " + e.getMessage());
   } finally {
       session.close();
   }

   return dto.getId();
  

  }

  // -------------------- DELETE --------------------
  public void delete(TourGuideBookingDTO dto) throws ApplicationException {

  
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
       throw new ApplicationException("Exception in TourGuideBooking Delete " + e.getMessage());
   } finally {
       session.close();
   }
  

  }

  // -------------------- UPDATE --------------------
  public void update(TourGuideBookingDTO dto) throws ApplicationException, DuplicateRecordException {

  
   TourGuideBookingDTO existDto = findByTouristName(dto.getTouristName());

   if (existDto != null && existDto.getId() != dto.getId()) {
       throw new DuplicateRecordException("Tourist Name already exists");
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
       throw new ApplicationException("Exception in TourGuideBooking Update " + e.getMessage());
   } finally {
       session.close();
   }
  

  }

  // -------------------- FIND BY PK --------------------
  public TourGuideBookingDTO findByPK(long pk) throws ApplicationException {

  
   Session session = null;
   TourGuideBookingDTO dto = null;

   try {
       session = HibDataSource.getSession();
       dto = (TourGuideBookingDTO) session.get(TourGuideBookingDTO.class, pk);
   } catch (HibernateException e) {
       throw new ApplicationException("Exception in getting TourGuideBooking by PK");
   } finally {
       session.close();
   }

   return dto;
  

  }

  // -------------------- FIND BY TOURIST NAME --------------------
  public TourGuideBookingDTO findByTouristName(String touristName) throws ApplicationException {

  
   Session session = null;
   TourGuideBookingDTO dto = null;

   try {
       session = HibDataSource.getSession();
       Criteria criteria = session.createCriteria(TourGuideBookingDTO.class);
       criteria.add(Restrictions.eq("touristName", touristName));

       List list = criteria.list();
       if (list.size() == 1) {
           dto = (TourGuideBookingDTO) list.get(0);
       }

   } catch (HibernateException e) {
       throw new ApplicationException("Exception in getting Booking by Tourist Name " + e.getMessage());
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
       Criteria criteria = session.createCriteria(TourGuideBookingDTO.class);

       if (pageSize > 0) {
           pageNo = (pageNo - 1) * pageSize;
           criteria.setFirstResult(pageNo);
           criteria.setMaxResults(pageSize);
       }

       list = criteria.list();

   } catch (HibernateException e) {
       throw new ApplicationException("Exception in TourGuideBooking List");
   } finally {
       session.close();
   }

   return list;
  

  }

  // -------------------- SEARCH --------------------
  public List search(TourGuideBookingDTO dto) throws ApplicationException {
  return search(dto, 0, 0);
  }

  public List search(TourGuideBookingDTO dto, int pageNo, int pageSize) throws ApplicationException {

 
   Session session = null;
   List list = null;

   try {
       session = HibDataSource.getSession();
       Criteria criteria = session.createCriteria(TourGuideBookingDTO.class);

       if (dto != null) {

           if (dto.getId() != null) {
               criteria.add(Restrictions.eq("id", dto.getId()));
           }

           if (dto.getTouristName() != null && dto.getTouristName().length() > 0) {
               criteria.add(Restrictions.like("touristName", dto.getTouristName() + "%"));
           }

           if (dto.getGuideName() != null && dto.getGuideName().length() > 0) {
               criteria.add(Restrictions.like("guideName", dto.getGuideName() + "%"));
           }

           if (dto.getLocation() != null && dto.getLocation().length() > 0) {
               criteria.add(Restrictions.like("location", dto.getLocation() + "%"));
           }

           if (dto.getBookingdate() != null) {
               criteria.add(Restrictions.eq("bookingdate", dto.getBookingdate()));
           }
       }

       if (pageSize > 0) {
           pageNo = (pageNo - 1) * pageSize;
           criteria.setFirstResult(pageNo);
           criteria.setMaxResults(pageSize);
       }

       list = criteria.list();

   } catch (HibernateException e) {
       throw new ApplicationException("Exception in TourGuideBooking Search");
   } finally {
       session.close();
   }

   return list;
  

  }
  }

package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.TourGuideBookingDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

/**

* Interface of Tour Guide Booking Model
*
* @author
  */
  public interface TourGuideBookingModelInt {

  public long add(TourGuideBookingDTO dto)
  throws ApplicationException, DuplicateRecordException;

  public void delete(TourGuideBookingDTO dto)
  throws ApplicationException;

  public void update(TourGuideBookingDTO dto)
  throws ApplicationException, DuplicateRecordException;

  public TourGuideBookingDTO findByPK(long pk)
  throws ApplicationException;

  // Agar touristName unique ho to useful hoga
  public TourGuideBookingDTO findByTouristName(String touristName)
  throws ApplicationException;

  public List list()
  throws ApplicationException;

  public List list(int pageNo, int pageSize)
  throws ApplicationException;

  public List search(TourGuideBookingDTO dto, int pageNo, int pageSize)
  throws ApplicationException;

  public List search(TourGuideBookingDTO dto)
  throws ApplicationException;
  }

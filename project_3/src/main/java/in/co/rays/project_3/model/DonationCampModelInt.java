package in.co.rays.project_3.model;

import java.util.Date;
import java.util.List;

import in.co.rays.project_3.dto.DonationCampDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

/**

* Interface of DonationCamp Model
* @author malay
  */

public interface DonationCampModelInt {


public long add(DonationCampDTO dto)
throws ApplicationException, DuplicateRecordException;

public void delete(DonationCampDTO dto)
throws ApplicationException;

public void update(DonationCampDTO dto)
throws ApplicationException, DuplicateRecordException;

public DonationCampDTO findByPK(long pk)
throws ApplicationException;

// Agar campName UNIQUE hai to ye method useful hoga
public DonationCampDTO findByCampName(String campName)
throws ApplicationException;

public List list()
throws ApplicationException;

public List list(int pageNo, int pageSize)
throws ApplicationException;

public List search(DonationCampDTO dto, int pageNo, int pageSize)
throws ApplicationException;

public List search(DonationCampDTO dto)
throws ApplicationException;


}

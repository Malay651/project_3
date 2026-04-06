package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ClaimDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

/**

* Interface of Claim Model
* @author malay
  */
  public interface ClaimModelInt {

  public long add(ClaimDTO dto)
  throws ApplicationException, DuplicateRecordException;

  public void delete(ClaimDTO dto)
  throws ApplicationException;

  public void update(ClaimDTO dto)
  throws ApplicationException, DuplicateRecordException;

  public ClaimDTO findByPK(long pk)
  throws ApplicationException;

  // Agar claimNumber UNIQUE hai to ye method useful hoga
  public ClaimDTO findByClaimNumber(String claimNumber)
  throws ApplicationException;

  public List list()
  throws ApplicationException;

  public List list(int pageNo, int pageSize)
  throws ApplicationException;

  public List search(ClaimDTO dto, int pageNo, int pageSize)
  throws ApplicationException;

  public List search(ClaimDTO dto)
  throws ApplicationException;
  }

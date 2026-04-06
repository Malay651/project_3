package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.BranchDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

/**

* Interface of Branch Model
* @author malay
  */
  public interface BranchModelInt {

  public long add(BranchDTO dto)
  throws ApplicationException, DuplicateRecordException;

  public void delete(BranchDTO dto)
  throws ApplicationException;

  public void update(BranchDTO dto)
  throws ApplicationException, DuplicateRecordException;

  public BranchDTO findByPK(long pk)
  throws ApplicationException;

  // Agar branchCode UNIQUE hai to ye method useful hoga
  public BranchDTO findByBranchCode(String branchCode)
  throws ApplicationException;

  public List list()
  throws ApplicationException;

  public List list(int pageNo, int pageSize)
  throws ApplicationException;

  public List search(BranchDTO dto, int pageNo, int pageSize)
  throws ApplicationException;

  public List search(BranchDTO dto)
  throws ApplicationException;
  }

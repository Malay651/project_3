package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.JobApplicationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface JobApplicationModelInt {

    public long add(JobApplicationDTO dto)
            throws ApplicationException, DuplicateRecordException;

    public void delete(JobApplicationDTO dto)
            throws ApplicationException;

    public void update(JobApplicationDTO dto)
            throws ApplicationException, DuplicateRecordException;

    public JobApplicationDTO findByPK(long pk)
            throws ApplicationException;

    // Assume applicationName is UNIQUE
    public JobApplicationDTO findByApplicationName(String applicationName)
            throws ApplicationException;

    public List list()
            throws ApplicationException;

    public List list(int pageNo, int pageSize)
            throws ApplicationException;

    public List search(JobApplicationDTO dto, int pageNo, int pageSize)
            throws ApplicationException;

    public List search(JobApplicationDTO dto)
            throws ApplicationException;
}
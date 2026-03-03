
 
	package in.co.rays.project_3.model;

	import java.util.List;

	import in.co.rays.project_3.dto.BatchDTO;
	import in.co.rays.project_3.exception.ApplicationException;
	import in.co.rays.project_3.exception.DuplicateRecordException;
	import in.co.rays.project_3.exception.RecordNotFoundException;

	/**
	 * Interface of Batch Model
	 * @author malay
	 */
	public interface BatchModelInt {

	    public long add(BatchDTO dto)
	            throws ApplicationException, DuplicateRecordException;

	    public void delete(BatchDTO dto)
	            throws ApplicationException;

	    public void update(BatchDTO dto)
	            throws ApplicationException, DuplicateRecordException;

	    public BatchDTO findByPK(long pk)
	            throws ApplicationException;

	    // Since batchCode is UNIQUE
	    public BatchDTO findByBatchCode(String batchCode)
	            throws ApplicationException;

	    public List list()
	            throws ApplicationException;

	    public List list(int pageNo, int pageSize)
	            throws ApplicationException;

	    public List search(BatchDTO dto, int pageNo, int pageSize)
	            throws ApplicationException;

	    public List search(BatchDTO dto)
	            throws ApplicationException;
	}


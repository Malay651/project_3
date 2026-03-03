package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.StockDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

/**
 * Interface of Stock Model
 * @author malay
 */
public interface StockModelInt {

    public long add(StockDTO dto)
            throws ApplicationException, DuplicateRecordException;

    public void delete(StockDTO dto)
            throws ApplicationException;

    public void update(StockDTO dto)
            throws ApplicationException, DuplicateRecordException;

    public StockDTO findByPK(long pk)
            throws ApplicationException;

    // Agar stockName UNIQUE hai to ye method useful hoga
    public StockDTO findByStockName(String stockName)
            throws ApplicationException;

    public List list()
            throws ApplicationException;

    public List list(int pageNo, int pageSize)
            throws ApplicationException;

    public List search(StockDTO dto, int pageNo, int pageSize)
            throws ApplicationException;

    public List search(StockDTO dto)
            throws ApplicationException;
}
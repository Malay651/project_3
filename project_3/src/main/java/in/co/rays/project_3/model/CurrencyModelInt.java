package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.CurrencyDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Currency Model
 * @author 
 */
public interface CurrencyModelInt {

    public long add(CurrencyDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(CurrencyDTO dto) throws ApplicationException;

    public void update(CurrencyDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(CurrencyDTO dto) throws ApplicationException;

    public List search(CurrencyDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public CurrencyDTO findByPK(long pk) throws ApplicationException;

    public CurrencyDTO findByCurrencyCode(String currencyCode) throws ApplicationException;

}
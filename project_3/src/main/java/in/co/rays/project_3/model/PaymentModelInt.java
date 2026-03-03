package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PaymentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

/**
 * Interface of Payment Model
 * @author malay
 */
public interface PaymentModelInt {

    public long add(PaymentDTO dto)
            throws ApplicationException, DuplicateRecordException;

    public void delete(PaymentDTO dto)
            throws ApplicationException;

    public void update(PaymentDTO dto)
            throws ApplicationException, DuplicateRecordException;

    public PaymentDTO findByPK(long pk)
            throws ApplicationException;

    // Since paymentCode is UNIQUE
    public PaymentDTO findByPaymentCode(String paymentCode)
            throws ApplicationException;

    public List list()
            throws ApplicationException;

    public List list(int pageNo, int pageSize)
            throws ApplicationException;

    public List search(PaymentDTO dto, int pageNo, int pageSize)
            throws ApplicationException;

    public List search(PaymentDTO dto)
            throws ApplicationException;
}
package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.ReportModuleDTO;
import in.co.rays.project_3.dto.RoleDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface ReportModuleModelInt {

	public long add(ReportModuleDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(ReportModuleDTO  dto)throws ApplicationException;
	public void update(ReportModuleDTO  dto)throws ApplicationException,DuplicateRecordException;
	public List search(ReportModuleDTO  dto)throws ApplicationException;
	public List search(ReportModuleDTO dto ,int pageNo,int pageSize)throws ApplicationException;
	public ReportModuleDTO authenticate(String login,String password)throws ApplicationException;
	public ReportModuleDTO findByPK(long pk)throws ApplicationException;
}

package in.co.rays.project_3.dto;

public class EmployeeDTO extends BaseDTO {

	//private Long employeeId;
	private String employeeCode;
	private String employeeName;
	private String department;
	private String email;
	private String designation;
	
	/*
	 * public Long getEmployeeId() { return employeeId; } public void
	 * setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

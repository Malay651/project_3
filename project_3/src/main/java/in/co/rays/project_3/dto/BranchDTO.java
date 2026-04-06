package in.co.rays.project_3.dto;

public class BranchDTO extends BaseDTO {

	private String branchCode;
	private String branchName;
	private String branchLocation;
	private String branchStatus;
	
	
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchLocation() {
		return branchLocation;
	}
	public void setBranchLocation(String branchLocation) {
		this.branchLocation = branchLocation;
	}
	public String getBranchStatus() {
		return branchStatus;
	}
	public void setBranchStatus(String branchStatus) {
		this.branchStatus = branchStatus;
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

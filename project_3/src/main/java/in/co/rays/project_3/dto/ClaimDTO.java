package in.co.rays.project_3.dto;

public class ClaimDTO extends BaseDTO{
     
	private String claimNumber;
	private Long claimAmount;
	private String status;
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public Long getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(Long claimAmount) {
		this.claimAmount = claimAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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

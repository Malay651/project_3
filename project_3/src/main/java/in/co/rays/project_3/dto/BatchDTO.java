package in.co.rays.project_3.dto;

public class BatchDTO extends BaseDTO {

	private String batchCode;
	private String batchName;
	private String trainerName;
	private String batchTiming;
	
	
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getTrainerName() {
		return trainerName;
	}
	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}
	public String getBatchTiming() {
		return batchTiming;
	}
	public void setBatchTiming(String batchTiming) {
		this.batchTiming = batchTiming;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id+ "";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return batchName+ "";
	}
	
}

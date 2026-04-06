package in.co.rays.project_3.dto;

import java.util.Date;

public class DonationCampDTO extends BaseDTO {

	public String getCampName() {
		return campName;
	}
	public void setCampName(String campName) {
		this.campName = campName;
	}
	public Date getCampDate() {
		return campDate;
	}
	public void setCampDate(Date campDate) {
		this.campDate = campDate;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	private String campName;
	private Date campDate;
	private String organizer;
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

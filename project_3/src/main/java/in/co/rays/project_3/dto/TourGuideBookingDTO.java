package in.co.rays.project_3.dto;

import java.util.Date;

public class TourGuideBookingDTO extends BaseDTO{

	private String touristName;
	private String guideName;
	private String location;
	private Date bookingdate;
	
	public String getTouristName() {
		return touristName;
	}
	public void setTouristName(String touristName) {
		this.touristName = touristName;
	}
	public String getGuideName() {
		return guideName;
	}
	public void setGuideName(String guideName) {
		this.guideName = guideName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getBookingdate() {
		return bookingdate;
	}
	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
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

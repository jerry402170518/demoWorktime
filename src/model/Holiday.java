package model;

import java.io.Serializable;
import java.util.Date;

public class Holiday implements Serializable{
	private static final long serialVersionUID = 1L;

	private Date holiday;

	private Integer hours;

	private String reason;

	private String status;

	public Holiday() {
	}

	public Integer getHours() {
		return this.hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the holiday
	 */
	public Date getHoliday() {
		return holiday;
	}

	/**
	 * @param holiday the holiday to set
	 */
	public void setHoliday(Date holiday) {
		this.holiday = holiday;
	}
}

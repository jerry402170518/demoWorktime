package model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class SubmissionHistory {
	
	private static final long serialVersionUID = 1L;

	private String empNo;
	
	private Date weekFirstDay;

	private String note;

	private String status;
	
	private String name;
	
	private int id;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SubmissionHistory() {
	}

	public String getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public Date getWeekFirstDay() {
		return weekFirstDay;
	}

	public void setWeekFirstDay(Date weekFirstDay) {
		this.weekFirstDay = weekFirstDay;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	
}

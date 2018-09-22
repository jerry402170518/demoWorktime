package model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Worktime {
	
	private static final long serialVersionUID = 1L;

	private String empNo;
	
	private Date weekFirstDay;

	private String note;

	private String status;
	
	private String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Worktime() {
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

	
}

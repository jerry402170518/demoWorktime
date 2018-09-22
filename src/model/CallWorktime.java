package model;

import java.sql.Date;

public class CallWorktime {
	private static final long serialVersionUID = 1L;

	private String empno;

	private Date weekFirstDay;
	
	private Date urgeDate;

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public Date getWeekFirstDay() {
		return weekFirstDay;
	}

	public void setWeekFirstDay(Date weekFirstDay) {
		this.weekFirstDay = weekFirstDay;
	}

	public Date getUrgeDate() {
		return urgeDate;
	}

	public void setUrgeDate(Date urgeDate) {
		this.urgeDate = urgeDate;
	}
	

	
	

}
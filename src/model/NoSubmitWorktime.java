package model;

import java.sql.Date;

public class NoSubmitWorktime {
	
	private static final long serialVersionUID = 1L;

	private Date weekFirstdate;
	
	private String empno;
	
	private String name;
	
	private int urgeTimes;
	
	private Date urgeDate;
	
	private int id;

	public Date getWeekFirstdate() {
		return weekFirstdate;
	}

	public void setWeekFirstdate(Date weekFirstdate) {
		this.weekFirstdate = weekFirstdate;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUrgeTimes() {
		return urgeTimes;
	}

	public void setUrgeTimes(int urgeTimes) {
		this.urgeTimes = urgeTimes;
	}

	public Date getUrgeDate() {
		return urgeDate;
	}

	public void setUrgeDate(Date urgeDate) {
		this.urgeDate = urgeDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}

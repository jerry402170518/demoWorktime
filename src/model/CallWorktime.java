package model;

import java.sql.Date;

public class CallWorktime {
	private static final long serialVersionUID = 1L;

	private String empNo;

	private Date weekFirstDay;
	
	private Integer callTimes;
	
	private Employee employee;

	public String getEmpNo() {
		return empNo;
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

	public Integer getCallTimes() {
		return callTimes;
	}

	public void setCallTimes(Integer callTimes) {
		this.callTimes = callTimes;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	

}
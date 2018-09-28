package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	private String empno;

	private Date begin;

	private String email;
	
	private Date accountBegin;

	private Date accountEnd;

	private String name;

	private String idNumber;

	private String password;

	private String position;


	public Employee() {
	}

	public String getEmpno() {
		return this.empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public Date getBegin() {
		return this.begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNumber() {
		return this.idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the accountBegin
	 */
	public Date getAccountBegin() {
		return accountBegin;
	}

	/**
	 * @param accountBegin the accountBegin to set
	 */
	public void setAccountBegin(Date accountBegin) {
		this.accountBegin = accountBegin;
	}

	/**
	 * @return the accountEnd
	 */
	public Date getAccountEnd() {
		return accountEnd;
	}

	/**
	 * @param accountEnd the accountEnd to set
	 */
	public void setAccountEnd(Date accountEnd) {
		this.accountEnd = accountEnd;
	}

	

}

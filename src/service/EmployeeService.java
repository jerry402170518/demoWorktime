package service;

import model.Employee;
import model.SubmissionHistory;

import java.util.List;
import java.util.Map;

import dao.EmployeeDAO;
import dao.EmployeeDAOJDBC;

public class EmployeeService {
	
	EmployeeDAO empDAO = new EmployeeDAOJDBC();
	
	/**
	 * 檢查傳入的employee是否帳號與密碼皆正確
	 * @param employee
	 * 員工
	 * @return boolean
	 */
	public boolean checkAccount(Employee employee){
		return empDAO.checkAccount(employee);
	}
	/**
	 * 讀取該帳號之個人資料
	 * @param empno
	 * 員工編號
	 * @return Map
	 */
	public Map<String, String> getLoginInfo(String empno) {
		// TODO Auto-generated method stub
		return empDAO.getLoginInfo(empno);
	}
	/**
	 * 變更密碼
	 * @param empno
	 * 員工編號
	 */
	public void changePassword(String empno,String password) {
		// TODO Auto-generated method stub
		empDAO.changePassword(empno,password);
	}
	public List<Employee> getEmpInfoByName(String inputSearch) {
		// TODO Auto-generated method stub
		return empDAO.getEmpInfoByName(inputSearch);
	}
	public List<Employee> getEmpInfoByEmpno(String inputSearch) {
		// TODO Auto-generated method stub
		return empDAO.getEmpInfoByEmpno(inputSearch);
	}
	public void modifyEmp(String name, String email, String position, String idNumber, String empno) {
		// TODO Auto-generated method stub
		empDAO.modifyEmp(name, email, position, idNumber, empno);
	}
}

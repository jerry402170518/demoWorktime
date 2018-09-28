package dao;

import java.util.List;
import java.util.Map;

import model.Employee;
import model.SubmissionHistory;

public interface EmployeeDAO {
	boolean checkAccount(Employee employee);

	Map<String, String> getLoginInfo(String empno);

	void changePassword(String empno,String password);

	List<Employee> getEmpInfoByName(String inputSearch);

	List<Employee> getEmpInfoByEmpno(String inputSearch);

	void modifyEmp(String name, String email, String position, String idNumber, String empno);

	Boolean checkEmpExist(String empno);

	void addEmp(String name, String email, String position, String idNumber, String empno);

	boolean startedOrNot(String empno);

	void insertPassword(String empno, String password);

	

}

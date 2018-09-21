package dao;

import java.util.List;
import java.util.Map;

import model.Employee;
import model.Worktime;

public interface EmployeeDAO {
	boolean checkAccount(Employee employee);

	Map<String, String> getLoginInfo(String empno);

	void changePassword(String empno,String password);

	List<Employee> getEmpInfoByName(String inputSearch);

	List<Employee> getEmpInfoByEmpno(String inputSearch);

}

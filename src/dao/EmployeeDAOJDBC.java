package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.*;
import model.Employee;
import model.SubmissionHistory;

public class EmployeeDAOJDBC implements EmployeeDAO{
	Connection conn = null;
	ResultSet rs = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
	
	private void close() {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace(System.err);
			}
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace(System.err);
			}
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace(System.err);
			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace(System.err);
			}
	}
	
	@Override
	public boolean checkAccount(Employee employee) {
		try {
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement("select empno, password from employee where empno=?");
			pstmt.setString(1, employee.getEmpno());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getString("password") == null) {
					return false;
				}
				if(rs.getString("empno").equals(employee.getEmpno()) && 
				   rs.getString("password").equals(employee.getPassword())){
					return true;
				}
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}
	
	

	@Override
	public Map<String, String> getLoginInfo(String empno) {
		try {
			
			Map<String, String> empInfo = new HashMap<String,String>();
			empInfo.put("empno",empno);
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement("select * from employee where empno = ?");
			pstmt.setString(1,empno);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				empInfo.put("name", rs.getString("NAME"));
				empInfo.put("position", rs.getString("POSITION"));
				empInfo.put("email", rs.getString("EMAIL"));
				empInfo.put("password", rs.getString("password"));
				if(rs.getString("account_end") != null) {
					empInfo.put("accountEnd", rs.getString("account_end"));
				}
				return empInfo;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return null;
	}
	


	@Override
	public boolean startedOrNot(String empno) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) ");
			sql.append("from employee ");
			sql.append("where empno= ? ");
			sql.append("and password is null ");
			
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, empno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1) == 1) {
					return true;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}

	@Override
	public void changePassword(String empno,String password) {
		// TODO Auto-generated method stub
		try {
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement("UPDATE employee SET password = ? WHERE empno = ? ");
			pstmt.setString(1,password);
			pstmt.setString(2,empno);
			rs = pstmt.executeQuery();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
	}

	@Override
	public List<Employee> getEmpInfoByName(String inputSearch) {
		// TODO Auto-generated method stub
		List<Employee> employeeList = new ArrayList<>();
		try {
			System.out.println(inputSearch);
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM EMPLOYEE WHERE NAME = ?");
			pstmt.setString(1,inputSearch);

			System.out.println("DDD");
			rs = pstmt.executeQuery();
			System.out.println("CCC");
			if(rs.next()) {
				employeeList.add(createEmployeeInfo(rs));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return employeeList;
	}

	@Override
	public List<Employee> getEmpInfoByEmpno(String inputSearch) {
		// TODO Auto-generated method stub
		List<Employee> employeeList = new ArrayList<>();
		try {
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM EMPLOYEE WHERE EMPNO = ? ");
			pstmt.setString(1,inputSearch);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				employeeList.add(createEmployeeInfo(rs));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return employeeList;
	}
	
	private Employee createEmployeeInfo(ResultSet rs) {
		// TODO Auto-generated method stub
		Employee employee = new Employee();
		try {
			employee.setEmpno(rs.getString("EMPNO"));
			employee.setName(rs.getString("NAME"));
			employee.setPosition(rs.getString("POSITION"));
			employee.setIdNumber(rs.getString("ID_NUMBER"));
			employee.setEmail(rs.getString("EMAIL"));
			return employee;
		} catch (SQLException e) {
			// TODO 自動產生的 catch 區塊
			throw new RuntimeException("資料庫錯誤. " + e.getMessage());
		}
	}

	@Override
	public void modifyEmp(String name, String email, String position, String idNumber,String empno) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("update employee ");
			sql.append("set name = ? , position = ? , email = ? , id_number = ? ");
			sql.append("where empno = ? ");
			
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			pstmt.setString(2, position);
			pstmt.setString(3, email);
			pstmt.setString(4, idNumber);
			pstmt.setString(5, empno);
			
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}

	@Override
	public Boolean checkEmpExist(String empno) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(empno) ");
			sql.append("from employee ");
			sql.append("where empno= ? ");
			
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, empno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1) == 0) {
					return false;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return true;
	}

	@Override
	public void addEmp(String name, String email, String position, String idNumber, String empno) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into employee(empno, name, position, id_number, email) ");
			sql.append("values (? , ? , ? , ? , ?)");
			System.out.println(email);
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, empno);
			pstmt.setString(2, name);
			pstmt.setString(3, position);
			pstmt.setString(4, idNumber);
			pstmt.setString(5, email);
			
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}

	@Override
	public void insertPassword(String empno, String password) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("update employee ");
			sql.append("set password = ? , account_begin = sysdate");
			sql.append("where empno = ? ");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, password);
			pstmt.setString(2, empno);
			
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}

	
}

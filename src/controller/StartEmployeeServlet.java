package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employee;
import service.EmployeeService;

public class StartEmployeeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private EmployeeService employeeService = new EmployeeService();
	private static final String START_EMPLOYEE_PAGE = "/WEB-INF/view/admin/startAccount.jsp";
	private static final String START_EMPLOYEE_VERIFICATION_PAGE = "/WEB-INF/view/admin/startAccountVerification.jsp";
	private static final String START_EMPLOYEE_PASSWORD_PAGE = "/WEB-INF/view/admin/startAccountPassword.jsp";
	private static final String lOGIN_PAGE = "login.jsp";
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		System.out.println(action);
		String page = null;
		
		switch(action) {
			//轉交至啟動帳號頁面
			case "startAccount_page":
				request.getRequestDispatcher(START_EMPLOYEE_PAGE).forward(request, response);
				break;
			//獲取啟動帳號之驗證碼
			case "getVerification":
				getVerification(request);
				request.getRequestDispatcher(START_EMPLOYEE_VERIFICATION_PAGE).forward(request, response);
			    break;
			//檢查啟動帳號之驗證碼
			case "checkVerification":
				page = checkVerification(request);
				request.getRequestDispatcher(page).forward(request, response);
				break;
			//啟動帳號之設定密碼
			case "insertPassword":
				insertPassword(request);
				request.getRequestDispatcher(lOGIN_PAGE).forward(request, response);
				break;
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void getVerification(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String empno = request.getParameter("empno");
		String idNumber = request.getParameter("idNumber");
		Employee employee = employeeService.getEmpInfoByEmpno(empno).get(0);
		System.out.println(employee.getEmpno());
		System.out.println(employee.getIdNumber());
		if(employee == null) {return;}
		if(empno.equals(employee.getEmpno()) && idNumber.equals(employee.getIdNumber()) && employee.getPassword() == null) {
			employeeService.getVerification(empno, idNumber,request);
		}
		request.setAttribute("empno", empno);
	}

	private String checkVerification(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String empno = request.getParameter("empno");
		String verification = request.getParameter("verification");
		System.out.println(verification);
		System.out.println(request.getSession().getAttribute("verificationCode"));
		if(verification.equals(request.getSession().getAttribute("verificationCode"))) {

			request.setAttribute("empno", empno);
			return START_EMPLOYEE_PASSWORD_PAGE;
		}
		return START_EMPLOYEE_VERIFICATION_PAGE;
		
	}
	

	private void insertPassword(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String empno = request.getParameter("empno");
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("repeatPassword");
		
		if(password.equals(repeatPassword)) {
			employeeService.insertPassword(empno, password);
		}
	}
}

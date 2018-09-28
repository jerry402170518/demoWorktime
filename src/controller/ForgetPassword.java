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

public class ForgetPassword extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private EmployeeService employeeService = new EmployeeService();
	
	private static final String LOGIN_PAGE = "login.jsp";
	private static final String FORGET_PASSWORD_PAGE = "/WEB-INF/view/admin/forgetPassword.jsp";
	private static final String FORGET_PASSWORD_VERIFICATION_PAGE = "/WEB-INF/view/admin/forgetPasswordVerification.jsp";
	private static final String GET_PASSWORD_PAGE = "/WEB-INF/view/admin/getPassword.jsp";
	private static final String lOGIN_PAGE = "login.jsp";
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		System.out.println(action);
		String page = null;
		
		switch(action) {
			//轉交至忘記密碼頁面
			case "forgetPassword_page":
				request.getRequestDispatcher(FORGET_PASSWORD_PAGE).forward(request, response);
				break;
			//獲取驗證碼
			case "getVerification":
				getVerification(request);
				request.getRequestDispatcher(FORGET_PASSWORD_VERIFICATION_PAGE).forward(request, response);
			    break;
			//檢查驗證碼
			case "checkVerification":
				page = checkVerification(request);
				request.getRequestDispatcher(page).forward(request, response);
				break;
			case "login_page":
				request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
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
		if(empno.equals(employee.getEmpno()) && idNumber.equals(employee.getIdNumber())) {
			employeeService.getVerificationByForgetPassword(empno, idNumber,request);
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
			String password = employeeService.getLoginInfo(empno).get("password");
			System.out.println(password);
			request.setAttribute("password", password);
			return GET_PASSWORD_PAGE;
		}
		return FORGET_PASSWORD_VERIFICATION_PAGE;
		
	}
}

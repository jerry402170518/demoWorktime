package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.EmployeeService;


public class ChangePassword extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private static final String EMPLOYEE_PAGE = "empMain.jsp";
	private static final String MANAGER_PAGE = "mgrMain.jsp";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = EMPLOYEE_PAGE;
		 Map<String,String> login = (Map)request.getSession().getAttribute("login");
		 

		 System.out.println(login.get("position"));
		 if(login.get("position").equals("主管")) {
			 path = MANAGER_PAGE;
			 System.out.println(path);
		 }
		EmployeeService empService = new EmployeeService();
		//更改密碼
		String oldPwd = request.getParameter("oldPwd");
		String newPwdFirst = request.getParameter("newPwdFirst");
		String newPwdSecond = request.getParameter("newPwdSecond");
		if(!oldPwd.equals(login.get("password"))) {
			String oldPwdError = "輸入的舊密碼有誤!";
			System.out.println(oldPwdError);
			
			request.setAttribute("oldPwdError", oldPwdError);
			if(login.get("position").equals("主管")) {
				request.getRequestDispatcher("Worktime?action=getMgrMainInfo").include(request, response);
				request.getRequestDispatcher(path).forward(request, response);
			}else {
				request.getRequestDispatcher("Worktime?action=getEmpMainOnfo").include(request, response);
				request.getRequestDispatcher(path).forward(request, response);
			}
			return;
		}
		if(oldPwd.equals(newPwdFirst)) {
			String pwdTheSame = "輸入的新密碼與舊密碼不可相同!";
			System.out.println(pwdTheSame);
			request.setAttribute("pwdTheSame", pwdTheSame);
			if(login.get("position").equals("主管")) {
				request.getRequestDispatcher("Worktime?action=getMgrMainInfo").include(request, response);
				request.getRequestDispatcher(path).forward(request, response);
			}else {
				request.getRequestDispatcher("Worktime?action=getEmpMainOnfo").include(request, response);
				request.getRequestDispatcher(path).forward(request, response);
			}
			return;
		}
		if(!newPwdFirst.equals(newPwdSecond)) {
			String doubleCheckError = "輸入的新密碼需相同!";
			System.out.println(doubleCheckError);
			request.setAttribute("doubleCheckError", doubleCheckError);
			if(login.get("position").equals("主管")) {
				request.getRequestDispatcher("Worktime?action=getMgrMainInfo").include(request, response);
				request.getRequestDispatcher(path).forward(request, response);
			}else {
				request.getRequestDispatcher("Worktime?action=getEmpMainOnfo").include(request, response);
				request.getRequestDispatcher(path).forward(request, response);
			}
			return;
		}else {
			System.out.println("變更成功");
			empService.changePassword(login.get("empno"),newPwdFirst);
			String changeSuccess = "變更成功!";
			request.setAttribute("changeSuccess", changeSuccess);
			
			String empno = login.get("empno");
			request.getSession().removeAttribute("login");
			Map<String, String> loginNew = empService.getLoginInfo(empno);
			request.getSession().setAttribute("login",loginNew);
			System.out.println(login.get("position"));
			if(login.get("position").equals("主管")) {
				request.getRequestDispatcher("Worktime?action=getMgrMainInfo").include(request, response);
				request.getRequestDispatcher(path).forward(request, response);
			}else {
				request.getRequestDispatcher("Worktime?action=getEmpMainOnfo").include(request, response);
				request.getRequestDispatcher(path).forward(request, response);
			}
		}
		
	}
}

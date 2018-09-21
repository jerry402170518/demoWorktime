package controller;

import java.io.IOException;
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
		 
		 if(login.get("position") == "主管") {
			 path = MANAGER_PAGE;
		 }
		EmployeeService empService = new EmployeeService();
		//更改密碼
		String oldPwd = request.getParameter("oldPwd");
		String newPwdFirst = request.getParameter("newPwdFirst");
		String newPwdSecond = request.getParameter("newPwdSecond");
		int countError =0 ;
		if(!oldPwd.equals(login.get("password"))) {
			countError++;
			String oldPwdError = "輸入的舊密碼有誤!";
			System.out.println(oldPwdError);
			request.setAttribute("oldPwdError", oldPwdError);
			request.getRequestDispatcher(path).forward(request, response);
			return;
		}
		if(!newPwdFirst.equals(newPwdSecond)) {
			countError++;
			String doubleCheckError = "請確認輸入的新密碼是否一樣!";
			System.out.println(doubleCheckError);
			request.setAttribute("doubleCheckError", doubleCheckError);
			request.getRequestDispatcher(path).forward(request, response);
			return;
		}
		if(oldPwd.equals(newPwdFirst)) {
			countError++;
			String pwdTheSame = "新密碼請與舊密碼相異";
			System.out.println(pwdTheSame);
			request.setAttribute("pwdTheSame", pwdTheSame);
			request.getRequestDispatcher(path).forward(request, response);
			return;
		}else {
			System.out.println("變更成功");
			empService.changePassword(login.get("empno"),newPwdFirst);
			request.getRequestDispatcher(path).forward(request, response);
		}
		
	}
}

package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList; 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employee;
import service.EmployeeService;

public class Login extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private static final String EMPLOYEE_PAGE = "empMain.jsp";
	private static final String MANAGER_PAGE = "mgrMain.jsp";
	private static final String ENGINEER_PAGE = "adminMain.jsp";
	private static final String ERROR_PAGE = "login.jsp";
	
	private EmployeeService empService = new EmployeeService();
	private WorktimeServlet worktimeServlet = new WorktimeServlet();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 如果用get傳送則導回 ERROR_PAGE
		request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = ERROR_PAGE;
		List<String> errorMsg = new ArrayList<String>();
		Employee employee = new Employee();
		employee.setEmpno(request.getParameter("inputUsername"));
		employee.setPassword(request.getParameter("inputPassword"));
		
		//驗證帳號密碼是否有輸入資料
		if (employee.getEmpno().trim().isEmpty()) {
			errorMsg.add("請輸入帳號!");
		}
		if (employee.getPassword().trim().isEmpty()) {
			errorMsg.add("請輸入密碼!");
		}
		if (!errorMsg.isEmpty()) {
			request.setAttribute("errorMsg", errorMsg);
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
		
		
		//驗證帳號密碼是否正確
		
		/*if(帳號與密碼皆正確){
		 * 	if(是否已離職){errorMsg:已離職:}
		 * 	else{ 判斷身分:顯示應當畫面}
		}else if(帳號是否未啟動){
			{errorMsg:帳號未啟動，請至信箱啟動帳號:}
		}else{
			{errorMsg:輸入帳號或密碼錯誤}
		*/
		if(empService.checkAccount(employee)) {
			Map<String, String> login=empService.getLoginInfo(employee.getEmpno());
			request.getSession().setAttribute("login",login);
			if(login.get("end")==null) {	
				switch (login.get("position")){
				case "員工":
					request.getRequestDispatcher("Worktime?action=getEmpMainOnfo").include(request, response);
					path=EMPLOYEE_PAGE;
					break;
				case "主管":
					request.getRequestDispatcher("Worktime?action=getMgrMainInfo").include(request, response);
					path=MANAGER_PAGE;
					break;
				case "系統管理員":
					path=ENGINEER_PAGE;
					break;
				}
			}else {
				errorMsg.add("您已離職!帳號已無法使用!");
				request.setAttribute("errorMsg", errorMsg);
			}
		}else if(empService.startedOrNot(employee.getEmpno())){
			errorMsg.add("帳號未啟動，請至信箱啟動帳號!");
			request.setAttribute("errorMsg", errorMsg);
		}else {
			errorMsg.add("帳號或密碼輸入錯誤!");
			request.setAttribute("errorMsg", errorMsg);
		}
		request.getRequestDispatcher(path).forward(request, response);
	}
	
}

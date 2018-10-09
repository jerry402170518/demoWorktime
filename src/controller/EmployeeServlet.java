package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employee;
import model.SubmissionHistory;
import service.EmployeeService;
import service.WorktimeService;

public class EmployeeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private EmployeeService employeeService = new EmployeeService();

	private static final String SEARCHEMPINFO_PAGE = "/WEB-INF/view/manager/mgrSearchEmpInfo.jsp";
	private static final String MODIFY_EMPLOYEE_PAGE = "/WEB-INF/view/admin/adminModifyEmp.jsp";
	private static final String ADD_EMPLOYEE_PAGE = "/WEB-INF/view/admin/adminAddEmp.jsp";
	private static final String EMPLOYEE_PAGE = "empMain.jsp";
	private static final String MANAGER_PAGE = "mgrMain.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String page = null;
		if (request.getSession().getAttribute("login") == null) {
			request.getRequestDispatcher("Logout").forward(request, response);
			return;
		}
		System.out.println(action);
		
		switch(action){
			//搜尋員工個人資料頁面
			case "searchEmpInfo_page":
				getAllempInfo(request);
				request.getRequestDispatcher(SEARCHEMPINFO_PAGE).forward(request, response);
				break;
			//轉交至搜尋員工個人資料頁面
			case "searchEmpInfo":
				doGetEmpInfo(request);
				request.getRequestDispatcher(SEARCHEMPINFO_PAGE).forward(request, response);
				break;
			//轉交至修改員工頁面
			case "modifyEmployee_page":
				request.getRequestDispatcher(MODIFY_EMPLOYEE_PAGE).forward(request, response);
				break;
			//搜尋欲修改之員工
			case "searchModifyEmp":
				doGetEmpInfo(request);
				request.getRequestDispatcher(MODIFY_EMPLOYEE_PAGE).forward(request, response);
				break;
			//修改員工資料
			case "modifyEmployee":
				modifyEmp(request);
				request.getRequestDispatcher(MODIFY_EMPLOYEE_PAGE).forward(request, response);
				break;
			//轉交至增加員工頁面
			case "addEmployee_page":
				request.getRequestDispatcher(ADD_EMPLOYEE_PAGE).forward(request, response);
				break;
			//新增員工:
			case "addEmployee":
				addEmployee(request);
				request.getRequestDispatcher(ADD_EMPLOYEE_PAGE).forward(request, response);
				break;
			//轉交至員工主頁面
			case "empMain_page":
				request.getRequestDispatcher("Worktime?action=getEmpMainOnfo").include(request, response);
				request.getRequestDispatcher(EMPLOYEE_PAGE).forward(request, response);
				break;
			//轉交至主管主頁面
			case "mgrMain_page":
				request.getRequestDispatcher("Worktime?action=getMgrMainInfo").include(request, response);
				request.getRequestDispatcher(MANAGER_PAGE).forward(request, response);
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void doGetEmpInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String nameOrEmpno = request.getParameter("nameOrEmpno");
		String inputSearch = request.getParameter("inputSearch");
		System.out.println(inputSearch);
		System.out.println(nameOrEmpno);
		if(inputSearch == null) {
			return;
		}
		List<Employee> employeeList = new ArrayList<>();
		
		if(nameOrEmpno.equals("name")) {
			employeeList = employeeService.getEmpInfoByName(inputSearch);
		}else {
			employeeList = employeeService.getEmpInfoByEmpno(inputSearch);
		}
		System.out.println("B");
		request.setAttribute("employeeList", employeeList);
	}

	private void modifyEmp(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String empno = request.getParameter("empno");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String position = request.getParameter("position");
		String idNumber = request.getParameter("idNumber");
		
		employeeService.modifyEmp(name, email, position, idNumber, empno);
	}

	private void addEmployee(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String position = request.getParameter("position");
		String idNumber = request.getParameter("idNumber");
		System.out.println(email);
		employeeService.addEmp(name, email, position, idNumber);
	}
	


	private void getAllempInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<Employee> employeeList = new ArrayList<>();
		employeeList = employeeService.getAllEmpInfo();
		System.out.println(employeeList);
		request.setAttribute("employeeList", employeeList);
	}


}

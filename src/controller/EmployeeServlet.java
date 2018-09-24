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

public class EmployeeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private EmployeeService employeeService = new EmployeeService();

	private static final String SEARCHEMPINFO_PAGE = "/WEB-INF/view/manager/mgrSearchEmpInfo.jsp";
	
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
			//轉交至搜尋員工個人資料頁面
			case "searchEmpInfo_page":
				doGetEmpInfo(request);
				request.getRequestDispatcher(SEARCHEMPINFO_PAGE).forward(request, response);
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
}

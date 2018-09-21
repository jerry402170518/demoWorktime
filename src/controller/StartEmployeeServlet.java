package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StartEmployeeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private static final String EMPLOYEE_PAGE = "empMain.jsp";
	private static final String MANAGER_PAGE = "mgrMain.jsp";
	private static final String ENGINEER_PAGE = "egrMain.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
}

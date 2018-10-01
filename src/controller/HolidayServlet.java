package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Employee;
import model.Holiday;
import service.EmployeeService;
import service.HolidayService;

public class HolidayServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private HolidayService holidayService = new HolidayService();
	
	private static final String HOLIDAY_MODIFY_PAGE = "/WEB-INF/view/admin/adminHoliday.jsp";
	private static final String CALENDAR = "/WEB-INF/view/admin/calendar.jsp";
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		System.out.println(action);
		String page = null;
		
		switch(action) {
			//轉交至假日修改頁面
			case "modifyHoliday_page":
				request.getRequestDispatcher(HOLIDAY_MODIFY_PAGE).forward(request, response);
				break;
			//顯示日曆
			case "calendar":
				request.getRequestDispatcher(CALENDAR).forward(request, response);
				break;
			//修改日期:
			case "modifyHoliday":
				modifyHoliday(request);
				request.getRequestDispatcher(CALENDAR).forward(request, response);
				break;
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

	private void modifyHoliday(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String status = request.getParameter("status");
		String hour = request.getParameter("hour");
		String reason = request.getParameter("reason");
		String holiday = request.getParameter("holiday");
		System.out.println(holiday);
	
		if(status.equals("holiday")) {
			holidayService.insertHoliday(hour, reason, holiday);
		}else {
			holidayService.deleteHoliday(holiday);
		}
	}
}

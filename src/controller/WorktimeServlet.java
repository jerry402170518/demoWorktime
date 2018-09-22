package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Worktime;
import model.WorktimeDetail;
import service.WorktimeService;

public class WorktimeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private WorktimeService worktimeService = new WorktimeService();
	
	private static final String WRITEWORKTIME_PAGE = "/WEB-INF/view/worktime/empWriteWorktime.jsp";
	private static final String SEARCHWORKTIME_PAGE = "/WEB-INF/view/employee/empSearchWorktime.jsp";
	private static final String SEARCHEMPWORKTIME_PAGE = "/WEB-INF/view/manager/mgrSearchWorktime.jsp";
	private static final String MGR_CHECK_WORKTIME_PAGE = "/WEB-INF/view/manager/mgrCheckWorktime.jsp";
	private static final String MGR_CALL_WORKTIME_PAGE = "/WEB-INF/view/manager/mgrCallWorktime.jsp";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String page = null;
		if (request.getSession().getAttribute("login") == null) {
			request.getRequestDispatcher("Logout").forward(request, response);
			return;
		}
		
		
		switch(action){
			//取得當月工時資訊
			case "getWriteWorktime":
				doGetEmpWorktime(request);
				break;
			//轉交至填寫工時頁面
			case "writeWorktime_page":
				doGetEmpWorktime(request);
				request.getRequestDispatcher(WRITEWORKTIME_PAGE).forward(request, response);
				break;
			//查詢工時
			case "searchWorktime":
				doGetPassWorktime(request);
				request.getRequestDispatcher(SEARCHWORKTIME_PAGE).forward(request, response);
				break;
			//轉交至員工查詢工時頁面
			case "searchWorktime_page":
				request.getRequestDispatcher(SEARCHWORKTIME_PAGE).forward(request, response);
				break;
			//轉交至主管查詢員工工時頁面
			case "mgrSearchWorktime_page":
				request.getRequestDispatcher(SEARCHEMPWORKTIME_PAGE).forward(request, response);
				break;
			//主管查詢員工工時
			case "mgrSearchWorktime":
				doSearchEmpWorktime(request);
				request.getRequestDispatcher(SEARCHEMPWORKTIME_PAGE).forward(request, response);
				break;
			//轉交至主管審核工時頁面
			case "mgrCheckWorktime_page":
				request.getRequestDispatcher(MGR_CHECK_WORKTIME_PAGE).forward(request, response);
				break;
			//主管查詢欲審核之工時:
			case "mgrSearchCheckWorktime":
				doSearchEmpWorktime(request);
				request.getRequestDispatcher(MGR_CHECK_WORKTIME_PAGE).forward(request, response);
				break;
			//轉交至主管催繳工時頁面
			case "mgrCallWorktime_page":
				doGetUrgeList(request);
				request.getRequestDispatcher(MGR_CHECK_WORKTIME_PAGE).forward(request, response);
		}

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void doGetEmpWorktime(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, String> loginInfo = (Map<String, String>) request.getSession().getAttribute("login");
		String empno = loginInfo.get("empno");
		String name = loginInfo.get("name");
		List<String> weekLastDays = new ArrayList<String>();
		List<Worktime> worktimeList = worktimeService.getWorktimeInfo(empno);
		//檢查本月是否已建立工時表
		if(worktimeService.checkCurrentMonth(empno)) {
			worktimeService.insertWorktime(empno);
			worktimeList = worktimeService.getWorktimeInfo(empno);
		}
		
		List<Integer> hours = worktimeService.getHours(worktimeList);
		for(int i=0; i < worktimeList.size(); i++){
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			c.setTime(worktimeList.get(i).getWeekFirstDay());
			c.add(Calendar.DAY_OF_MONTH, 6);
			String weekLastDay = sdf.format(c.getTime());
			weekLastDays.add(weekLastDay);
        }
		request.setAttribute("hours", hours);
		request.setAttribute("weekLastDays", weekLastDays);
		request.setAttribute("worktimeList", worktimeList);
	}
	


	private void doGetPassWorktime(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, String> loginInfo = (Map<String, String>) request.getSession().getAttribute("login");
		String empno = loginInfo.get("empno");
		String searchMonth  = request.getParameter("searchMonth");
		System.out.println(searchMonth);
		
		if(searchMonth == null) {
			return;
		}
		List<Worktime> worktimeList = worktimeService.getWorktime(empno,searchMonth);
		if(worktimeList.size() == 0) {
			return;
		}
		List<Integer> hours = worktimeService.getHours(worktimeList);
		List<String> weekLastDays = new ArrayList<String>();

		for(int i=0; i < worktimeList.size(); i++){
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			c.setTime(worktimeList.get(i).getWeekFirstDay());
			c.add(Calendar.DAY_OF_MONTH, 6);
			String weekLastDay = sdf.format(c.getTime());
			weekLastDays.add(weekLastDay);
        }
		request.setAttribute("hours", hours);
		request.setAttribute("weekLastDays", weekLastDays);
		request.setAttribute("worktimeList", worktimeList);
	}
	


	private void doSearchEmpWorktime(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String inputMonth  = request.getParameter("inputMonth");
		String nameOrEmpno = request.getParameter("nameOrEmpno");
		String inputSearch = request.getParameter("inputSearch");
		System.out.println(inputMonth);
		System.out.println(inputSearch);
		List<Worktime> worktimeList = new ArrayList<>();
		
	
		//只輸入姓名或員編
		if(inputMonth.isEmpty() && !inputSearch.isEmpty()) {
			System.out.println("只輸入姓名或員編");
			worktimeList = worktimeService.mgrSearchWorktime(nameOrEmpno, inputSearch, inputMonth);
		}
		//只輸入月份
		if(!inputMonth.isEmpty() && inputSearch.isEmpty()) {
			String empno = null;
			System.out.println("只輸入月份");
			worktimeList = worktimeService.getWorktime(empno, inputMonth);
		}
		//姓名與員編都輸入
		if(!inputMonth.isEmpty() && !inputSearch.isEmpty()) {
			System.out.println("姓名與員編都輸入");
			worktimeList = worktimeService.mgrSearchWorktime(nameOrEmpno, inputSearch, inputMonth);
		}
		//查無資料，直接轉交頁面
		if(worktimeList.size() == 0) {
			System.out.println("查無資料，直接轉交頁面");
			return;
		}
		List<Integer> hours = worktimeService.getHours(worktimeList);
		List<String> weekLastDays = new ArrayList<String>();

		for(int i=0; i < worktimeList.size(); i++){
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			c.setTime(worktimeList.get(i).getWeekFirstDay());
			c.add(Calendar.DAY_OF_MONTH, 6);
			String weekLastDay = sdf.format(c.getTime());
			weekLastDays.add(weekLastDay);
        }
		request.setAttribute("hours", hours);
		request.setAttribute("weekLastDays", weekLastDays);
		request.setAttribute("worktimeList", worktimeList);
	}


	private void doGetUrgeList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<Worktime> worktimeList = worktimeService.
		
	}


}

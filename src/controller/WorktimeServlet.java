package controller;

import java.io.IOException;
import java.text.ParseException;
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

import model.SubmissionHistory;
import model.WorktimeDetail;
import service.EmployeeService;
import service.HolidayService;
import service.WorktimeService;
import model.NoSubmitWorktime;

public class WorktimeServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	private WorktimeService worktimeService = new WorktimeService();
	private HolidayService holidayService = new HolidayService();
	private EmployeeService employeeService = new EmployeeService();
	
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
		System.out.println(action);
		
		switch(action){
			//取得當月工時資訊
			case "getWriteWorktime":
				try {
					doGetEmpWorktime(request);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			//轉交至填寫工時頁面
			case "writeWorktime_page":
				try {
					doGetEmpWorktime(request);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getRequestDispatcher(WRITEWORKTIME_PAGE).forward(request, response);
				break;
			//查詢工時
			case "searchWorktime":
				try {
					doGetEmpWorktime(request);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			case "mgrUrgeWorktime_page":
				doGetNoSubmitWorktime(request);
				request.getRequestDispatcher(MGR_CALL_WORKTIME_PAGE).forward(request, response);
				break;
			//催繳工時
			case "urgeWorktime":
				doUrgeWorktime(request);
				doGetNoSubmitWorktime(request);
				request.getRequestDispatcher(MGR_CALL_WORKTIME_PAGE).forward(request, response);
				break;
			//主管審核工時為未通過
			case "worktimeCheckNoPass":
				checkNoPass(request);
				request.getRequestDispatcher(MGR_CHECK_WORKTIME_PAGE).forward(request, response);
				break;
			//主管審核工時為已通過
			case "worktimeCheckPass":
				checkPass(request);
				request.getRequestDispatcher(MGR_CHECK_WORKTIME_PAGE).forward(request, response);
				break;
			//取得未繳交與未通過筆數
			case "getEmpMainOnfo":
				getEmpMainOnfo(request);
				try {
					doGetEmpWorktime(request);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "getMgrMainInfo":
				getMgrMainInfo(request);
				break;
		}

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void doGetEmpWorktime(HttpServletRequest request) throws ParseException {
		// TODO Auto-generated method stub
		Map<String, String> loginInfo = (Map<String, String>) request.getSession().getAttribute("login");
		String empno = loginInfo.get("empno");
		String name = loginInfo.get("name");
		String currentMonth = request.getParameter("currentMonth");
		//alert
		String submitSuccess = request.getParameter("submitSuccess");

		List<String> weekLastDays = new ArrayList<String>();
		List<SubmissionHistory> worktimeList = worktimeService.getWorktimeInfo(empno, currentMonth);
		//檢查本月是否已建立工時表
		if(worktimeList.size() == 0) {
			worktimeService.insertWorktime(empno, currentMonth);
			worktimeList = worktimeService.getWorktimeInfo(empno, currentMonth);
		}
		List<Integer> hours = worktimeService.getHours(worktimeList);
		List<String> holiday = holidayService.getHoliday(worktimeList);
		
		for(int i=0; i < worktimeList.size(); i++){
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			c.setTime(worktimeList.get(i).getWeekFirstDay());
			c.add(Calendar.DAY_OF_MONTH, 6);
			String weekLastDay = sdf.format(c.getTime());
			weekLastDays.add(weekLastDay);
        }
		
		if(submitSuccess != null) {
			System.out.println(submitSuccess);
			submitSuccess = "提交成功!";
			request.setAttribute("submitSuccess", submitSuccess);
		}
		request.setAttribute("hours", hours);
		request.setAttribute("holiday", holiday);
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
		List<SubmissionHistory> worktimeList = new ArrayList<>();
		
	
		//只輸入姓名或員編
		if(inputMonth.isEmpty() && !inputSearch.isEmpty()) {
			System.out.println("只輸入姓名或員編");
			worktimeList = worktimeService.mgrSearchWorktime(nameOrEmpno, inputSearch, inputMonth);
		}
		//只輸入月份
		if(!inputMonth.isEmpty() && inputSearch.isEmpty()) {
			String empno = null;
			System.out.println("只輸入月份");
			worktimeList = worktimeService.getWorktimeInfo(empno, inputMonth);
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
		List<String> names = employeeService.getNames(worktimeList);
		List<String> weekLastDays = new ArrayList<String>();

		for(int i=0; i < worktimeList.size(); i++){
			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			c.setTime(worktimeList.get(i).getWeekFirstDay());
			c.add(Calendar.DAY_OF_MONTH, 6);
			String weekLastDay = sdf.format(c.getTime());
			weekLastDays.add(weekLastDay);
        }
		
		
		request.setAttribute("names", names);
		request.setAttribute("hours", hours);
		request.setAttribute("weekLastDays", weekLastDays);
		request.setAttribute("worktimeList", worktimeList);
	}


	private void doGetNoSubmitWorktime(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<NoSubmitWorktime> noSubmotWorktimeList = worktimeService.getNoSubmitWorktime();
		request.setAttribute("noSubmotWorktimeList", noSubmotWorktimeList);
	}
	
	private void doUrgeWorktime(HttpServletRequest request) {
		// TODO Auto-generated method stub
		//取得最新的催繳日期
		List<NoSubmitWorktime> noSubmotWorktimeList = worktimeService.getNewstUrgeDate();
		worktimeService.urgeEmployee(noSubmotWorktimeList);
	}


	private void checkPass(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String submssionId = request.getParameter("submssionId");
		System.out.println(submssionId);
		worktimeService.checkPass(submssionId);
	}

	private void checkNoPass(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String submssionId = request.getParameter("submssionId");
		String noPassReason = request.getParameter("noPassReason");
		System.out.println(noPassReason);
		worktimeService.checkNoPass(submssionId,noPassReason);
	}


	private void getEmpMainOnfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<String, String> loginInfo = (Map<String, String>) request.getSession().getAttribute("login");
		String empno = loginInfo.get("empno");
		int days = worktimeService.getNoPassAndNoSubmit(empno);
		List<Integer> lastweek = worktimeService.getlastWeekHours(empno);
		request.setAttribute("days", days);
		request.setAttribute("lastweek", lastweek);
	}
	
	private void getMgrMainInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		List<Integer> totalCheck = worktimeService.getTotalCheck();
		List<Integer> totalPass = worktimeService.getTotalPass();
		System.out.println("IM IN");
		request.setAttribute("totalCheck", totalCheck);
		request.setAttribute("totalPass", totalPass);
	}


}

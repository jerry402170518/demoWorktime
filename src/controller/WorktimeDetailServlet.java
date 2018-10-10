package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SubmissionHistory;
import model.WorktimeDetail;
import service.WorktimeDetailService;
import service.WorktimeService;

public class WorktimeDetailServlet extends HttpServlet{
		private static final long serialVersionUID = 1L;
		
		private WorktimeDetailService worktimeDetailService = new WorktimeDetailService();
		private WorktimeService worktimeService = new WorktimeService();
		
		private static final String WRITEWORKTIMEDETAIL_PAGE = "/WEB-INF/view/worktime/empWriteWorktimeDetail.jsp";
		private static final String WRITEWORKTIME_PAGE = "/WEB-INF/view/worktime/empWriteWorktime.jsp";
		private static final String WORKTIME_DETAIL_PAGE = "/WEB-INF/view/worktime/worktimeDetail.jsp";
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			
			String action = request.getParameter("action");
//			System.out.println(action);
			String page = null;
			
			if (request.getSession().getAttribute("login") == null) {
				request.getRequestDispatcher("Logout").forward(request, response);
				return;
			}
			
			
			switch(action){
				// 轉交至填寫工時詳細頁面
				case "writeWorktimeDetail_page":
					doGetEmpWorktimeDetail(request);
					request.getRequestDispatcher(WRITEWORKTIMEDETAIL_PAGE).forward(request, response);
					break;
				//刪除此筆工時紀錄
				case "deleteWorktime":
					page = doDeleteWorktime(request);
					request.getRequestDispatcher(page).forward(request, response);
					break;
				//新增此筆工時紀錄
				case "insertWorktime":
					page = doInsertWorktime(request);
					request.getRequestDispatcher(page).forward(request, response);
					break;
				//員工取得詳細工時資訊
				case "getWorktimeDetail":
					doGetEmpWorktimeDetail(request);
					request.getRequestDispatcher(WORKTIME_DETAIL_PAGE).forward(request, response);
					break;
				//主管取得詳細工時資訊
				case "mgrGetWorktimeDetail":
					doMgrGetEmpWorktimeDetail(request);

//					request.getRequestDispatcher("Worktime?action=searchWorktime").include(request, response);
					request.getRequestDispatcher(WORKTIME_DETAIL_PAGE).forward(request, response);
					break;
				case "submitWortkime":
					page = submitWortkime(request);
					request.getRequestDispatcher(page).forward(request, response);
					break;
			}
		}
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			doGet(request, response);
		}
		

		
		private void doGetEmpWorktimeDetail(HttpServletRequest request){
			// TODO Auto-generated method stub
			Map<String, String> loginInfo = (Map<String, String>) request.getSession().getAttribute("login");
			String empno = loginInfo.get("empno");
			String weekFirstDay = request.getParameter("weekFirstDay");
			System.out.println(weekFirstDay);
			List<WorktimeDetail> worktimeDetailList = worktimeDetailService.getWorktimeDetailInfo(empno, weekFirstDay);
			
			LocalDate localDateWeekFirstDay = LocalDate.parse(weekFirstDay);
	    	LocalDate localDateWeekLastDay = localDateWeekFirstDay.plusDays(6);
	    	
	    	request.setAttribute("weekLastDay", localDateWeekLastDay);
			request.setAttribute("worktimeDetailList", worktimeDetailList);
			request.setAttribute("weekFirstDay",weekFirstDay);
		}
		

		private String doDeleteWorktime(HttpServletRequest request) {
			// TODO Auto-generated method stub
			Map<String, String> loginInfo = (Map<String, String>) request.getSession().getAttribute("login");
			String empno = loginInfo.get("empno");
			String currentDate = request.getParameter("currentDate");
			String project = request.getParameter("project");
			
			String weekFirstDay = request.getParameter("weekFirstDay");
			worktimeDetailService.deleteWorktime(empno, currentDate, project);
			
			String deleteSuccess = "刪除成功";
			request.setAttribute("deleteSuccess", deleteSuccess);
			System.out.println("刪除成功");
			return "/WorktimeDetail?action=writeWorktimeDetail_page&weekFirstDay=" + weekFirstDay;
		}

		private String doInsertWorktime(HttpServletRequest request) {
			// TODO Auto-generated method stub
			Map<String, String> loginInfo = (Map<String, String>) request.getSession().getAttribute("login");
			String empno = loginInfo.get("empno");
			String currentDate = request.getParameter("currentDate");
			String project = request.getParameter("project");
			String workNote = request.getParameter("workNote");
			String hours = request.getParameter("hours");
			String weekFirstDay = request.getParameter("weekFirstDay");
			
			LocalDate localDateWeekFirstDay = LocalDate.parse(weekFirstDay);
	    	LocalDate localDateWeekLastDay = localDateWeekFirstDay.plusDays(6);
	    	LocalDate mid = LocalDate.parse(currentDate); 
	    	
	    	Map<String, String> employeeInput = new HashMap<>();
	    	employeeInput.put("currentDate", currentDate);
	    	employeeInput.put("project", project);
	    	employeeInput.put("workNote", workNote);
	    	employeeInput.put("hours", hours);
    		request.getSession().setAttribute("employeeInput", employeeInput);
	    	
	    	if(!mid.isAfter(localDateWeekLastDay) && !mid.isBefore(localDateWeekFirstDay)) {
	    		if(!worktimeDetailService.checkHolidayOrNot(currentDate)) {
					worktimeDetailService.insertWorktime(empno, currentDate, project, workNote, hours);					
	    		}else {
	    			String errorMsg = "你輸入的日期為放假日，請重新確認所填寫的日期";
		    		request.setAttribute("errorMsg", errorMsg);
		    		
		    		return "/WorktimeDetail?action=writeWorktimeDetail_page&weekFirstDay=" + weekFirstDay;
	    		}
	    	}else {
	    		String errorMsg = "你輸入的日期未介於該周別之中，請填寫正確的日期";
	    		request.setAttribute("errorMsg", errorMsg);
	    		return "/WorktimeDetail?action=writeWorktimeDetail_page&weekFirstDay=" + weekFirstDay;
	    	}
			
			System.out.println(weekFirstDay);
			System.out.println("新增成功");
			request.getSession().removeAttribute("employeeInput");
			return "/WorktimeDetail?action=writeWorktimeDetail_page&weekFirstDay=" + weekFirstDay;
		}
		

		private void doMgrGetEmpWorktimeDetail(HttpServletRequest request) {
			// TODO Auto-generated method stub
			System.out.println("doMgrGetEmpWorktimeDetail start");
			String weekFirstDay = request.getParameter("weekFirstDay");
			String note = request.getParameter("note");
			List<WorktimeDetail> worktimeDetailList = worktimeDetailService.mgrGetWorktimeDetailInfo(weekFirstDay);
			if(worktimeDetailList.size() == 0) {
				worktimeDetailList.add(null);
			}
			System.out.println(note);
			request.setAttribute("worktimeDetailList", worktimeDetailList);
			request.setAttribute("weekFirstDay",weekFirstDay);
			request.setAttribute("note",note);
			System.out.println("doMgrGetEmpWorktimeDetail end");
		}
		

		private String submitWortkime(HttpServletRequest request) {
			// TODO Auto-generated method stub
			Map<String, String> loginInfo = (Map<String, String>) request.getSession().getAttribute("login");
			String empno = loginInfo.get("empno");
			String weekFirstDay = request.getParameter("weekFirstDay");
			System.out.println(empno);
			System.out.println(weekFirstDay);
			worktimeDetailService.submitWortkime(empno, weekFirstDay);
			
			String submitSuccess = "提交成功";
			request.setAttribute("submitSuccess",submitSuccess);
			return "/Worktime?action=writeWorktime_page";
		}



}

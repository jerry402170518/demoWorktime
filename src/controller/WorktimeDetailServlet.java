package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.WorktimeDetail;
import service.WorktimeDetailService;

public class WorktimeDetailServlet extends HttpServlet{
		private static final long serialVersionUID = 1L;
		
		private WorktimeDetailService worktimeDetailService = new WorktimeDetailService();
		
		private static final String WRITEWORKTIMEDETAIL_PAGE = "/WEB-INF/view/worktime/empWriteWorktimeDetail.jsp";
		private static final String WRITEWORKTIME_PAGE = "/WEB-INF/view/worktime/empWriteWorktime.jsp";
		private static final String WORKTIME_DETAIL_PAGE = "/WEB-INF/view/worktime/worktimeDetail.jsp";
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			
			String action = request.getParameter("action");
			System.out.println(action);
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
				//取得詳細工時資訊
				case "getWorktimeDetail":
					doGetEmpWorktimeDetail(request);
					request.getRequestDispatcher(WORKTIME_DETAIL_PAGE).forward(request, response);
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
			List<WorktimeDetail> worktimeDetailList = worktimeDetailService.getWorktimeDetailInfo(empno, weekFirstDay);
			
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
			
			worktimeDetailService.insertWorktime(empno, currentDate, project, workNote, hours);
			
			System.out.println(weekFirstDay);
			System.out.println("新增成功");
			return "/WorktimeDetail?action=writeWorktimeDetail_page&weekFirstDay=" + weekFirstDay;
		}
}

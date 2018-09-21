package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Worktime;
import model.WorktimeDetail;

public class WorktimeDAOJDBC implements WorktimeDAO{
	Connection conn = null;
	ResultSet rs = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	
	private void close() {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace(System.err);
			}
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace(System.err);
			}
		if (pstmt != null)
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace(System.err);
			}
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO 自動產生的 catch 區塊
				e.printStackTrace(System.err);
			}
	}
	
	@Override
	public List<Worktime> getWorktime(String empno) {
		// TODO Auto-generated method stub
		List<Worktime> worktimeList = new ArrayList<Worktime>();
		
		Calendar c = Calendar.getInstance();   // this takes current date
	    c.set(Calendar.DAY_OF_MONTH, 1);
	    SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd");
	    String firstDayOfMonth = sdfDate.format(c.getTime());
	    
	    SimpleDateFormat sdfDateEnd = new SimpleDateFormat("YYYY-MM");
		String endDate = sdfDateEnd.format(c.getTime());
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from submission_history where empno = ?");
			sql.append("and week_first_day >= TO_DATE(?,'YYYY-MM-DD')");
			sql.append("and week_first_day < ADD_MONTHS(TO_DATE(?,'YYYY-MM'),1)" );
			sql.append("order by week_first_day");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,empno);
			pstmt.setString(2,firstDayOfMonth);
			pstmt.setString(3,endDate);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				worktimeList.add(createWorktime(rs));
			}
				
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return worktimeList;
	}

	private Worktime createWorktime(ResultSet rs) {
		// TODO Auto-generated method stub
		Worktime worktime = new Worktime();
		try {
			worktime.setEmpNo(rs.getString("EMPNO"));
			worktime.setWeekFirstDay(rs.getDate("WEEK_FIRST_DAY"));
			worktime.setStatus(rs.getString("STATUS"));
			
			return worktime;
		} catch (SQLException e) {
			// TODO 自動產生的 catch 區塊
			throw new RuntimeException("資料庫錯誤. " + e.getMessage());
		}
	}

	@Override
	public void insertWorktime(String empno) {
		// TODO Auto-generated method stub
		//取得第一個星期日
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1); 
		while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			cal.add(Calendar.DATE, 1);
		}
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd");
		int currentMonth =cal.get(Calendar.MONTH)+1;
		int nextMonth = cal.get(Calendar.MONTH)+1;
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO SUBMISSION_HISTORY (EMPNO, WEEK_FIRST_DAY, STATUS, NOTE)");
			sql.append("VALUES (?, TO_DATE(?,'YYYY-MM-DD'), '未繳交', null)");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,empno);
			
			while(currentMonth == nextMonth) {
			    String firstDayOfMonth = sdfDate.format(cal.getTime());
				pstmt.setString(2, firstDayOfMonth);
				rs = pstmt.executeQuery();
				cal.add(Calendar.DAY_OF_MONTH, 7);
				nextMonth = cal.get(Calendar.MONTH)+1;
				System.out.println("test");
			}
				
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		
	}

	@Override
	public List<Integer> getHours(List<Worktime> worktimeList) {
		// TODO Auto-generated method stub
		List<Integer> hours = new ArrayList<>();
		SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd");
		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		calBegin.set(Calendar.DATE, 1); 
		while (calBegin.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			calBegin.add(Calendar.DATE, 1);
		}
		
		calEnd.set(Calendar.DATE, calEnd.getActualMaximum(Calendar.DATE));
    	while (calEnd.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
		{
    		calEnd.add(Calendar.DATE, 1);
		}
    	calEnd.add(Calendar.DATE, 1);
    	String endDate = sdfDate.format(calEnd.getTime());
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select sum(hours) from working_records where empno = ?");
			sql.append("and working_date = TO_DATE(?,'YYYY-MM-DD')");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,worktimeList.get(0).getEmpNo());
			while(!calBegin.getTime().equals(calEnd.getTime())){
				
				String beginDate = sdfDate.format(calBegin.getTime());
				
				pstmt.setString(2,beginDate);
				
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					hours.add(rs.getInt(1));
				}
				calBegin.add(Calendar.DATE, 1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return hours;
	}

	@Override
	public List<Worktime> getWorktime(String empno, String searchMonth) {
		// TODO Auto-generated method stub
		List<Worktime> worktimeList = new ArrayList<Worktime>();
	    
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("select * from submission_history where empno = ? ");
			sql.append("and week_first_day >= TO_DATE(?,'YYYY-MM') ");
			sql.append("and week_first_day < ADD_MONTHS(TO_DATE(?,'YYYY-MM'),1) ");
			sql.append("order by week_first_day");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,empno);
			pstmt.setString(2,searchMonth);
			pstmt.setString(3,searchMonth);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				worktimeList.add(createWorktime(rs));
			}	
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return worktimeList;
	}

	@Override
	public boolean checkCurrentMonth(String empno) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd");
		Calendar calBegin = Calendar.getInstance();
		calBegin.set(Calendar.DATE, 1); 
		while (calBegin.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			calBegin.add(Calendar.DATE, 1);
		}
		String beginDate = sdfDate.format(calBegin.getTime());
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM SUBMISSION_HISTORY ");
			sql.append("WHERE EMPNO= ? ");
			sql.append("AND WEEK_FIRST_DAY = TO_DATE(?,'YYYY-MM-DD') ");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,empno);
			pstmt.setString(2,beginDate);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return true;
	}

}

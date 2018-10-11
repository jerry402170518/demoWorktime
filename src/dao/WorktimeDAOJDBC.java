package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sun.xml.internal.ws.util.StringUtils;

import model.NoSubmitWorktime;
import model.SubmissionHistory;
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

	private SubmissionHistory createWorktime(ResultSet rs) {
		// TODO Auto-generated method stub
		SubmissionHistory worktime = new SubmissionHistory();
		try {
			worktime.setEmpNo(rs.getString("EMPNO"));
			worktime.setWeekFirstDay(rs.getDate("WEEK_FIRST_DAY"));
			worktime.setStatus(rs.getString("STATUS"));
			worktime.setId(rs.getInt("id"));
			worktime.setNote(rs.getString("NOTE"));
			return worktime;
		} catch (SQLException e) {
			// TODO 自動產生的 catch 區塊
			throw new RuntimeException("資料庫錯誤. " + e.getMessage());
		}
	}

	@Override
	public List<Integer> getHours(List<SubmissionHistory> worktimeList) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Integer> hours = new ArrayList<>();
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select sum(hours) from working_records where empno = ?");
			sql.append("and working_date = TO_DATE(?,'YYYY-MM-DD')");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,worktimeList.get(0).getEmpNo());
			for(int i = 0; i < worktimeList.size(); i++) {
				Date weekfirstDay = worktimeList.get(i).getWeekFirstDay();
				Calendar c = Calendar.getInstance(); 
				c.setTime(weekfirstDay); 
				for(int j = 0; j < 7; j++) {
					pstmt.setString(2, sdf.format(weekfirstDay));
					rs = pstmt.executeQuery();
					if(rs.next()) {
						hours.add(rs.getInt(1));
					}
					c.add(Calendar.DATE, 1);
					weekfirstDay = c.getTime();
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return hours;
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

	
	@Override
	public List<SubmissionHistory> mgrSearchWorktime(String nameOrEmpno, String inputSearch,String inputMonth) {
		// TODO Auto-generated method stub
		List<SubmissionHistory> worktimeList = new ArrayList<SubmissionHistory>();
		System.out.println(nameOrEmpno+"A");
		System.out.println(inputSearch+"A");
		System.out.println(inputMonth+"A");
		try {
//			StringBuilder sql = new StringBuilder();
//			sql.append("select employee.empno,  employee.name, submission_history.* ");
//			sql.append("from submission_history ");
//			sql.append("inner join employee on submission_history.empno = employee.empno ");
//			if(nameOrEmpno != null && !nameOrEmpno.trim().isEmpty()) {
//				System.out.println(nameOrEmpno +" is not null and empty");
//				if(inputSearch != null && !inputSearch.trim().isEmpty()) {
//					System.out.println(inputSearch +" is not null and empty");
//					if(nameOrEmpno.equals("name")) {
//						sql.append("where employee.name = ? ");
//					}else {
//						sql.append("where employee.empno = ? ");
//					}
//				}
//			}
//			if(inputMonth != null && !inputMonth.trim().isEmpty()){
//				System.out.println(inputMonth +" is not null and empty");
//				sql.append("and week_first_day >= TO_DATE(?,'YYYY-MM') ");
//				sql.append("and week_first_day < ADD_MONTHS(TO_DATE(?,'YYYY-MM'),1) ");
//			}
//			sql.append("order by week_first_day");
//			conn = ConnectionHelper.getConnection();
//			pstmt = conn.prepareStatement(sql.toString());
//			if(nameOrEmpno != null && !nameOrEmpno.trim().isEmpty()) {
//				System.out.println(nameOrEmpno +" is not null and empty A");
//				if(inputSearch != null && !inputSearch.trim().isEmpty()) {
//					System.out.println(inputSearch +" is not null and empty A");
//					pstmt.setString(1,inputSearch);
//				}
//			}
//			if(inputMonth != null && !inputMonth.trim().isEmpty()){
//				System.out.println(inputMonth +" is not null and empty A");
//				if(inputSearch != null && !inputSearch.trim().isEmpty()) {
//					pstmt.setString(1,inputMonth);
//					pstmt.setString(2,inputMonth);
//				}else {
//					pstmt.setString(2,inputMonth);
//					pstmt.setString(3,inputMonth);
//				}
//			}
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				worktimeList.add(createWorktime(rs));
//			}
			StringBuilder sql = new StringBuilder();
			sql.append("select employee.empno,  employee.name, submission_history.* ");
			sql.append("from submission_history ");
			sql.append("inner join employee on submission_history.empno = employee.empno ");
			if(nameOrEmpno != null && !nameOrEmpno.trim().isEmpty()) {
				if(nameOrEmpno.equals("name")) {
					sql.append("where employee.name = ?");
				}else {
					sql.append("where employee.empno = ?");
				}
				if(inputMonth !=null && !inputMonth.trim().isEmpty()){
					sql.append("and week_first_day >= TO_DATE(?,'YYYY-MM') ");
					sql.append("and week_first_day < ADD_MONTHS(TO_DATE(?,'YYYY-MM'),1) ");
				}
			}
			sql.append("order by week_first_day");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			if(nameOrEmpno != null && !nameOrEmpno.trim().isEmpty()) {
				pstmt.setString(1,inputSearch);
				if(inputMonth !=null && !inputMonth.trim().isEmpty()){
					pstmt.setString(2,inputMonth);
					pstmt.setString(3,inputMonth);
				}
			}
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
	public List<NoSubmitWorktime> getNoSubmitWorktime() {
		// TODO Auto-generated method stub
		List<NoSubmitWorktime> noSubmitWorktimeList = new ArrayList<NoSubmitWorktime>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    	String deadline = sdf.format(c.getTime());
    	System.out.println(deadline);
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT submission_history.id, employee.name, submission_history.empno, submission_history.week_first_day, count(urge_history.id) urge_times ");
			sql.append("FROM submission_history ");
			sql.append("left join employee on submission_history.empno = employee.empno ");
			sql.append("left join urge_history on submission_history.id = urge_history.submission_id ");
			sql.append("where submission_history.status = '未繳交' ");
			sql.append("and submission_history.week_first_day < TO_DATE(? ,'YYYY-MM-DD') ");
			sql.append("group by submission_history.id, submission_history.status, submission_history.empno, employee.name, submission_history.week_first_day ");
			sql.append("order by submission_history.week_first_day");
			
			System.out.println("SUCCESS");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,deadline);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NoSubmitWorktime noSubmitWorktime = new NoSubmitWorktime();
				noSubmitWorktime.setWeekFirstdate(rs.getDate("WEEK_FIRST_DAY"));
				noSubmitWorktime.setEmpno(rs.getString("EMPNO"));
				noSubmitWorktime.setName(rs.getString("NAME"));
				noSubmitWorktime.setUrgeTimes(rs.getInt("URGE_TIMES"));
				System.out.println(rs.getInt("URGE_TIMES"));
				System.out.println("C");
				noSubmitWorktimeList.add(noSubmitWorktime);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return noSubmitWorktimeList;	
	}

	@Override
	public List<NoSubmitWorktime> getNewstUrgeDate() {
		// TODO Auto-generated method stub
		List<NoSubmitWorktime> noSubmitWorktimeList = new ArrayList<NoSubmitWorktime>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    	String deadline = sdf.format(c.getTime());
    	System.out.println(deadline);
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT submission_history.id, employee.name, submission_history.empno, submission_history.week_first_day, max(urge_history.urge_date) news_urge_date,employee.email ");
			sql.append("FROM submission_history ");
			sql.append("left join employee on submission_history.empno = employee.empno ");
			sql.append("left join urge_history on submission_history.id = urge_history.submission_id ");
			sql.append("where submission_history.status = '未繳交'  ");
			sql.append("and submission_history.week_first_day < TO_DATE(?,'YYYY-MM-DD') ");
			sql.append("group by submission_history.id, submission_history.status, submission_history.empno, employee.name, submission_history.week_first_day,employee.email ");
			sql.append("order by submission_history.week_first_day");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,deadline);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NoSubmitWorktime noSubmitWorktime = new NoSubmitWorktime();
				noSubmitWorktime.setWeekFirstdate(rs.getDate("WEEK_FIRST_DAY"));
				noSubmitWorktime.setEmpno(rs.getString("EMPNO"));
				noSubmitWorktime.setName(rs.getString("NAME"));
				noSubmitWorktime.setUrgeDate(rs.getDate("NEWS_URGE_DATE"));
				noSubmitWorktime.setId(rs.getInt("ID"));
				noSubmitWorktime.setEmail(rs.getString("EMAIL"));
				noSubmitWorktimeList.add(noSubmitWorktime);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return noSubmitWorktimeList;
	}

	@Override
	public void urgeEmployee(List<NoSubmitWorktime> noSubmotWorktimeList) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into urge_history(urge_date, submission_id ) ");
			sql.append("values(sysdate,?)");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());

			System.out.println("noSubmitWorktimeList size:"+ noSubmotWorktimeList.size());
			for(int i = 0;i<noSubmotWorktimeList.size();i++) {
				pstmt.setInt(1, noSubmotWorktimeList.get(i).getId());
				pstmt.executeUpdate();
				System.out.println("WHAT");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}

	@Override
	public void checkPass(String submssionId) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE SUBMISSION_HISTORY ");
			sql.append("SET STATUS = '已通過' ");
			sql.append("WHERE ID = ? ");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, submssionId);
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}

	@Override
	public void checNokPass(String submssionId, String noPassReason) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE SUBMISSION_HISTORY ");
			sql.append("SET STATUS = '未通過'  , NOTE = ? ");
			sql.append("WHERE ID = ? ");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, noPassReason);
			pstmt.setString(2, submssionId);
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}

	@Override
	public int getNoPassAndNoSubmit(String empno, String currentMonth, String lastMonth) {
		// TODO Auto-generated method stub
		int days = 0;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(status) ");
			sql.append("from submission_history ");
			sql.append("where status in ('未繳交','未通過') ");
			sql.append("and empno = ?  ");
			sql.append("and week_first_day < TO_DATE( ? ,'YYYY-MM') ");
			sql.append("and week_first_day >= TO_DATE( ? ,'YYYY-MM') ");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.setString(1, empno);
			pstmt.setString(2, currentMonth);
			pstmt.setString(3, lastMonth);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				days = rs.getInt(1);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		System.out.println(days);
		return days;
	}

	@Override
	public List<Integer> getlastWeekHours(String empno) {
		// TODO Auto-generated method stub
		List<Integer> hours = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    	cal.add(Calendar.DATE, -7);
    	
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select sum(hours) from working_records where empno = ? ");
			sql.append("and working_date = TO_DATE(?,'YYYY-MM-DD') ");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, empno);
			for(int i = 0; i < 7; i++) {
				pstmt.setString(2, sdf.format(cal.getTime()));
				rs = pstmt.executeQuery();
				if(rs.next()) {
					hours.add(rs.getInt(1));
				}
				cal.add(Calendar.DATE, 1);
			}
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return hours;
	}

	@Override
	public List<SubmissionHistory> getWorktimeInfo(String empno, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		List<SubmissionHistory> worktimeList = new ArrayList<SubmissionHistory>();
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from submission_history ");
			sql.append("where week_first_day ");
			sql.append("between TO_DATE( ? ,'YYYY-MM-DD') " );
			sql.append("and TO_DATE( ? ,'YYYY-MM-DD') ");
			if(empno != null) {
				sql.append("and empno = ? ");
			}
			sql.append("order by week_first_day ");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,beginDate);
			pstmt.setString(2,endDate);
			if(empno != null) {
				pstmt.setString(3,empno);
			}
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
	public void insertWorktime(String empno, Calendar calBegin) {
		// TODO Auto-generated method stub
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd");
		int currentMonth =calBegin.get(Calendar.MONTH)+1;
		int nextMonth = calBegin.get(Calendar.MONTH)+1;
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO SUBMISSION_HISTORY (EMPNO, WEEK_FIRST_DAY, STATUS, NOTE) ");
			sql.append("VALUES (?, TO_DATE(?,'YYYY-MM-DD'), '未繳交', null)");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,empno);
			
			while(currentMonth == nextMonth) {
			    String firstDayOfMonth = sdfDate.format(calBegin.getTime());
				pstmt.setString(2, firstDayOfMonth);
				rs = pstmt.executeQuery();
				calBegin.add(Calendar.DAY_OF_MONTH, 7);
				nextMonth = calBegin.get(Calendar.MONTH)+1;
			}
				
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
	}

	@Override
	public List<Integer> getTotalCheck(String beginDate, String currentMonth) {
		// TODO Auto-generated method stub
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("yyyy-MM");
		List<Integer> totalCheck = new ArrayList<>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) from submission_history ");
			sql.append("where week_first_day = TO_DATE( ? , 'YYYY-MM-DD') ");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			LocalDate firstWeekDate = LocalDate.parse(beginDate);
			
			while(formatterMonth.format(firstWeekDate).equals(currentMonth)) {
				pstmt.setString(1,formatterDate.format(firstWeekDate));
				rs = pstmt.executeQuery();
				if(rs.next()) {
					totalCheck.add(rs.getInt(1));
				}
				System.out.println(formatterDate.format(firstWeekDate));
				firstWeekDate = firstWeekDate.plusDays(7);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return totalCheck;
	}

	@Override
	public List<Integer> getTotalPass(String beginDate, String currentMonth) {
		// TODO Auto-generated method stub
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("yyyy-MM");
		List<Integer> totalCheck = new ArrayList<>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) from submission_history ");
			sql.append("where week_first_day = TO_DATE( ? , 'YYYY-MM-DD') ");
			sql.append("and status = '已通過'");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			
			LocalDate firstWeekDate = LocalDate.parse(beginDate);
			while(formatterMonth.format(firstWeekDate).equals(currentMonth)) {
				pstmt.setString(1,formatterDate.format(firstWeekDate));
				rs = pstmt.executeQuery();
				if(rs.next()) {
					totalCheck.add(rs.getInt(1));
				}
				System.out.println(formatterDate.format(firstWeekDate));
				firstWeekDate = firstWeekDate.plusDays(7);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		return totalCheck;
	}

	@Override
	public List<SubmissionHistory> getPassWorktime(String empno, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		List<SubmissionHistory> worktimeList = new ArrayList<SubmissionHistory>();
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from submission_history ");
			sql.append("where week_first_day ");
			sql.append("between TO_DATE( ? ,'YYYY-MM-DD') " );
			sql.append("and TO_DATE( ? ,'YYYY-MM-DD') ");
			sql.append("and empno = ? ");
			sql.append("and status = '已通過'");
			sql.append("order by week_first_day ");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,beginDate);
			pstmt.setString(2,endDate);
			pstmt.setString(3,empno);
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

}

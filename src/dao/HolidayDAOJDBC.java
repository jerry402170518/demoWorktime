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

import model.SubmissionHistory;

public class HolidayDAOJDBC implements HolidayDAO{
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
	public void insertHoliday(String hour, String reason, String holiday) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into holidays(holiday, reason, hours) ");
			sql.append("values(TO_DATE( ? ,'YYYY-MM-DD'), ? , ?) ");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, holiday);
			pstmt.setString(2, reason);
			pstmt.setString(3, hour);
			
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}


	@Override
	public void updateHoliday(String hour, String reason, String holiday) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("update holidays ");
			sql.append("set holiday = TO_DATE( ? ,'YYYY-MM-DD'), reason = ?, hours = ?");
			sql.append("where holiday = TO_DATE( ? ,'YYYY-MM-DD')");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, holiday);
			pstmt.setString(2, reason);
			pstmt.setString(3, hour);
			pstmt.setString(4, holiday);
			
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}

	@Override
	public boolean checkExist(String holiday) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select count(*) ");
			sql.append("from holidays ");
			sql.append("where holiday = TO_DATE( ? ,'YYYY-MM-DD') ");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, holiday);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1) == 1) {
					return true;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return false;
	}


	@Override
	public void deleteHoliday(String holiday) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("delete from holidays ");
			sql.append("where holiday = TO_DATE( ? ,'YYYY-MM-DD')");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, holiday);
			
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}


	@Override
	public List<String> getHoliday(List<SubmissionHistory> worktimeList) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> holiday = new ArrayList<>();
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("select * from holidays ");
			sql.append("where holiday = TO_DATE( ? ,'YYYY-MM-DD')");
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			
			for(int i = 0; i < worktimeList.size(); i++) {
				Date weekfirstDay = worktimeList.get(i).getWeekFirstDay();
				Calendar c = Calendar.getInstance(); 
				c.setTime(weekfirstDay); 
				for(int j = 0; j < 7; j++) {
					pstmt.setString(1, sdf.format(weekfirstDay));
					rs = pstmt.executeQuery();
					if(rs.next()) {
						holiday.add(rs.getString("REASON"));
					}else {
						holiday.add(null);
					}
					c.add(Calendar.DATE, 1);
					weekfirstDay = c.getTime();
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return holiday;
	}

}

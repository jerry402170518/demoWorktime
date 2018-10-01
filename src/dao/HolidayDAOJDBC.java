package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

}

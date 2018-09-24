package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.SubmissionHistory;
import model.WorktimeDetail;

public class WorktimeDetailDAOJDBC implements WorktimeDetailDAO{
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
	public void insertWorktime(String empno, String currentDate, String project, String workNote, String hours) {
		// TODO Auto-generated method stub
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into working_records(empno,working_date,project_no,hours,note)");
			sql.append("values (?,TO_DATE(?,'YYYY-MM-DD'),?,?,?)");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,empno);
			pstmt.setString(2,currentDate);
			pstmt.setString(3,project);
			pstmt.setString(4,hours);
			pstmt.setString(5,workNote);
			rs = pstmt.executeQuery();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
	}

	@Override
	public void deleteWorktime(String empno, String currentDate, String project) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("delete from working_records ");
			sql.append("where empno=?");
			sql.append("and working_date = TO_DATE(?,'YYYY-MM-DD') ");
			sql.append("and project_no = ?");
			
			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,empno);
			pstmt.setString(2,currentDate);
			pstmt.setString(3,project);
			rs = pstmt.executeQuery();
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
	}

	@Override
	public List<WorktimeDetail> getWorktimeDetailInfo(String empno, String weekFirstDay) {
		// TODO Auto-generated method stub
		List<WorktimeDetail> worktimeDetailList = new ArrayList<>();
		
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from working_records ");
			sql.append("where empno=? ");
			sql.append("and working_date between TO_DATE(?,'YYYY-MM-DD') ");
			sql.append("and TO_DATE(?,'YYYY-MM-DD')+6");
			sql.append("order by working_date");

			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,empno);
			pstmt.setString(2,weekFirstDay);
			pstmt.setString(3,weekFirstDay);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				worktimeDetailList.add(createWorktimeDetail(rs));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		
		return worktimeDetailList;
	}

	private WorktimeDetail createWorktimeDetail(ResultSet rs) {
		// TODO Auto-generated method stub
		WorktimeDetail worktimeDetail = new WorktimeDetail();
		try {
			worktimeDetail.setEmpNo(rs.getString("EmpNo"));
			worktimeDetail.setCurrentDate(rs.getDate("WORKING_DATE"));
			worktimeDetail.setProjectNo(rs.getString("PROJECT_NO"));
			worktimeDetail.setNote(rs.getString("NOTE"));
			worktimeDetail.setHours(rs.getInt("HOURS"));
			return worktimeDetail;
		} catch (SQLException e) {
			// TODO 自動產生的 catch 區塊
			throw new RuntimeException("資料庫錯誤. " + e.getMessage());
		}
	}

	@Override
	public List<WorktimeDetail> mgrGetWorktimeDetailInfo(String weekFirstDay) {
		// TODO Auto-generated method stub
		List<WorktimeDetail> worktimeDetailList = new ArrayList<>();
		
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from working_records ");
			sql.append("where working_date between TO_DATE(?,'YYYY-MM-DD') ");
			sql.append("and TO_DATE(?,'YYYY-MM-DD')+6");
			sql.append("order by working_date");

			conn = ConnectionHelper.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,weekFirstDay);
			pstmt.setString(2,weekFirstDay);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				worktimeDetailList.add(createWorktimeDetail(rs));
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			close();
		}
		
		return worktimeDetailList;
	}

}

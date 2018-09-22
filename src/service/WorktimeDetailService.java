package service;

import java.util.List;

import dao.WorktimeDetailDAO;
import dao.WorktimeDetailDAOJDBC;
import model.WorktimeDetail;

public class WorktimeDetailService {
	
	private WorktimeDetailDAO wDao = new WorktimeDetailDAOJDBC();
	
	public void insertWorktime(String empno, String currentDate, String project, String workNote, String hours) {
		// TODO Auto-generated method stub
		wDao.insertWorktime(empno, currentDate, project, workNote, hours);
	}

	public void deleteWorktime(String empno, String currentDate, String project) {
		// TODO Auto-generated method stub
		wDao.deleteWorktime(empno, currentDate, project);
	}

	public List<WorktimeDetail> getWorktimeDetailInfo(String empno, String weekFirstDay) {
		// TODO Auto-generated method stub
		return wDao.getWorktimeDetailInfo(empno,weekFirstDay);
	}

	public List<WorktimeDetail> mgrGetWorktimeDetailInfo(String weekFirstDay) {
		// TODO Auto-generated method stub
		return wDao.mgrGetWorktimeDetailInfo(weekFirstDay);
	}

}

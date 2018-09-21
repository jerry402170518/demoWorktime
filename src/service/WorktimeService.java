package service;

import java.util.List;
import java.util.Map;

import dao.WorktimeDAO;
import dao.WorktimeDAOJDBC;
import model.Worktime;
import model.WorktimeDetail;

public class WorktimeService {
	
	private WorktimeDAO wDao = new WorktimeDAOJDBC();
	
	public List<Worktime> getWorktimeInfo(String empno) {
		// TODO Auto-generated method stub
		return wDao.getWorktime(empno);
	}

	public void insertWorktime(String empno,String name) {
		// TODO Auto-generated method stub
		wDao.insertWorktime(empno, name);
	}

	public List<Integer> getHours(List<Worktime> worktimeList) {
		// TODO Auto-generated method stub
		return wDao.getHours(worktimeList);
	}

	public List<Worktime> getWorktime(String empno, String searchMonth) {
		// TODO Auto-generated method stub
		return wDao.getWorktime(empno,searchMonth);
	}

	public boolean checkCurrentMonth(String empno) {
		// TODO Auto-generated method stub
		return wDao.checkCurrentMonth(empno);
	}

	public List<Worktime> mgrSearchWorktime(String nameOrEmpno, String inputSearch,String inputMonth) {
		// TODO Auto-generated method stub
		return wDao.mgrSearchWorktime(nameOrEmpno, inputSearch, inputMonth);
	}




}

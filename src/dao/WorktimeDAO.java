package dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.Worktime;
import model.WorktimeDetail;

public interface WorktimeDAO {
	
	public List<Worktime> getWorktime(String empno);

	public void insertWorktime(String empno, String name);

	public List<Integer> getHours(List<Worktime> worktimeList);

	public List<Worktime> getWorktime(String empno, String searchMonth);

	public boolean checkCurrentMonth(String empno);

	public List<Worktime> mgrSearchWorktime(String nameOrEmpno, String inputSearch,String inputMonth);

	

}

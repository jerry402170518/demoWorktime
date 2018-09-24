package service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import dao.WorktimeDAO;
import dao.WorktimeDAOJDBC;
import model.NoSubmitWorktime;
import model.SubmissionHistory;
import model.WorktimeDetail;

public class WorktimeService {
	
	private WorktimeDAO wDao = new WorktimeDAOJDBC();
	
	public List<SubmissionHistory> getWorktimeInfo(String empno) {
		// TODO Auto-generated method stub
		return wDao.getWorktime(empno);
	}

	public void insertWorktime(String empno) {
		// TODO Auto-generated method stub
		wDao.insertWorktime(empno);
	}

	public List<Integer> getHours(List<SubmissionHistory> worktimeList) {
		// TODO Auto-generated method stub
		return wDao.getHours(worktimeList);
	}

	public List<SubmissionHistory> getWorktime(String empno, String searchMonth) {
		// TODO Auto-generated method stub
		return wDao.getWorktime(empno,searchMonth);
	}

	public boolean checkCurrentMonth(String empno) {
		// TODO Auto-generated method stub
		return wDao.checkCurrentMonth(empno);
	}

	public List<SubmissionHistory> mgrSearchWorktime(String nameOrEmpno, String inputSearch,String inputMonth) {
		// TODO Auto-generated method stub
		return wDao.mgrSearchWorktime(nameOrEmpno, inputSearch, inputMonth);
	}

	public List<NoSubmitWorktime> getNoSubmitWorktime() {
		// TODO Auto-generated method stub
		return wDao.getNoSubmitWorktime();
	}

	public List<NoSubmitWorktime> getNewstUrgeDate() {
		// TODO Auto-generated method stub
		return wDao.getNewstUrgeDate();
	}
	
	
	public void urgeEmployee(List<NoSubmitWorktime> noSubmotWorktimeList) {
		// TODO Auto-generated method stub
		//判斷urgeDate 是否為當日，true代表今日已催繳，從noSubmotWorktimeList把此筆物件移除
		for(int i = 0; i < noSubmotWorktimeList.size(); i++) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
			if(noSubmotWorktimeList.get(i).getUrgeDate() == null) {
				continue;
			}
			String urgeDate = sdf.format(noSubmotWorktimeList.get(i).getUrgeDate());
			String currentDate = sdf.format(cal.getTime());
			if(urgeDate.equals(currentDate)) {
				noSubmotWorktimeList.remove(i);
			}
		}
		
	
		
		wDao.urgeEmployee(noSubmotWorktimeList);
	}

	public void checkPass(String submssionId) {
		// TODO Auto-generated method stub
		wDao.checkPass(submssionId);
	}

	public void checkNoPass(String submssionId, String noPassReason) {
		// TODO Auto-generated method stub
		wDao.checNokPass(submssionId,noPassReason);
	}




}

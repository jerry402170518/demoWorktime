package dao;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.NoSubmitWorktime;
import model.SubmissionHistory;
import model.WorktimeDetail;

public interface WorktimeDAO {
	
//	public List<SubmissionHistory> getWorktime(String empno);

	public List<Integer> getHours(List<SubmissionHistory> worktimeList);

	public List<SubmissionHistory> getWorktime(String empno, String searchMonth);

	public boolean checkCurrentMonth(String empno);

	public List<SubmissionHistory> mgrSearchWorktime(String nameOrEmpno, String inputSearch,String inputMonth);

	public List<NoSubmitWorktime> getNoSubmitWorktime();

	public List<NoSubmitWorktime> getNewstUrgeDate();

	public void urgeEmployee(List<NoSubmitWorktime> noSubmotWorktimeList);

	public void checkPass(String submssionId);

	public void checNokPass(String submssionId, String noPassReason);

	public int getNoPassAndNoSubmit(String empno, String currentMonth, String lastMonth);

	public List<Integer> getlastWeekHours(String empno);

	public List<SubmissionHistory> getWorktimeInfo(String empno, String beginDate, String endDate);

	public void insertWorktime(String empno, Calendar calBegin);

	public List<Integer> getTotalCheck(String beginDate, String currentMonth);

	public List<Integer> getTotalPass(String beginDate, String currentMonth);

}

package dao;

import java.util.List;

import model.WorktimeDetail;

public interface WorktimeDetailDAO {

	void insertWorktime(String empno, String currentDate, String project, String workNote, String hours);

	void deleteWorktime(String empno, String currentDate, String project);

	List<WorktimeDetail> getWorktimeDetailInfo(String empno, String weekFirstDay);

	List<WorktimeDetail> mgrGetWorktimeDetailInfo(String weekFirstDay);

}

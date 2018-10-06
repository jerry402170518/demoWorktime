package dao;

import java.util.List;

import model.Holiday;
import model.SubmissionHistory;

public interface HolidayDAO {
	void insertHoliday(String hour, String reason, String holiday);

	boolean checkExist(String holiday);

	void updateHoliday(String hour, String reason, String holiday);

	void deleteHoliday(String holiday);

	List<String> getHoliday(List<SubmissionHistory> worktimeList);

}

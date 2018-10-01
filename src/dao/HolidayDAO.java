package dao;

import model.Holiday;

public interface HolidayDAO {
	void insertHoliday(String hour, String reason, String holiday);

	boolean checkExist(String holiday);

	void updateHoliday(String hour, String reason, String holiday);

	void deleteHoliday(String holiday);

}

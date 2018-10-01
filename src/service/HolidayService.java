package service;


import java.sql.Date;
import java.util.List;
import java.util.Map;

import dao.HolidayDAO;
import dao.HolidayDAOJDBC;
import model.Holiday;

public class HolidayService {

	private HolidayDAO hDao=new HolidayDAOJDBC();
	
	public HolidayService() {
		// TODO 自動產生的建構子 Stub
	}
	/**
	 * 
	 * @param status
	 * @param hour
	 * @param reason
	 * @param holidayString
	 */
	public void insertHoliday(String hour, String reason, String holiday) {
		// TODO Auto-generated method stub
		if(hDao.checkExist(holiday)) {
			hDao.updateHoliday(hour, reason, holiday);
		}else {
			hDao.insertHoliday(hour, reason, holiday);
		}
	}
	public void deleteHoliday(String holiday) {
		// TODO Auto-generated method stub
		hDao.deleteHoliday(holiday);
	}
}

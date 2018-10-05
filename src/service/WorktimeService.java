package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
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
		//取得當月的日期
	    
		return wDao.getWorktime(empno);
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
		
		for(int i = 0;i < noSubmotWorktimeList.size(); i++) {
			EmailService emailService = new EmailService();
			StringBuilder html = new StringBuilder();
			html.append("<style>.border{border:1px solid gray;height: 1px;}</style>");
			html.append("<center><h1>工時系統-工時尚未繳交</h1>");
			html.append("<div class='border'></div>");
			html.append("<p>您的工時尚未繳交，請盡速填寫並繳交</p>");
			html.append("<p>員工:" + noSubmotWorktimeList.get(i).getName() + "</p>");
			html.append("<p>員編:" + noSubmotWorktimeList.get(i).getEmpno()+ "</p>");
			html.append("<p>周別起始日:" + noSubmotWorktimeList.get(i).getWeekFirstdate()+"</p></center>");
			
			String email = noSubmotWorktimeList.get(i).getEmail();
			String title = "工時尚未繳交";
			try {
				emailService.sendHtmlMail(email, title, html.toString());
			} catch (AddressException e) {
				// TODO 自動產生的 catch 區塊
				System.out.println("email地址錯誤: " + e.getMessage());
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO 自動產生的 catch 區塊
				System.out.println("email寄送錯誤: " + e.getMessage());
				e.printStackTrace();
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

	public int getNoPassAndNoSubmit(String empno) {
		// TODO Auto-generated method stub
		//查詢出當天日期屬於哪個月份的工時範圍，並傳入當月份的字串 EX : '2018-09'
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
	    c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	    String currentMonth = sdf.format(c.getTime());
	    System.out.println(currentMonth);
	    c.add(Calendar.MONTH, -1);
	    String lastMonth = sdf.format(c.getTime());
	    System.out.println(lastMonth);
		return wDao.getNoPassAndNoSubmit(empno, currentMonth, lastMonth);
	}

	public List<Integer> getlastWeekHours(String empno) {
		// TODO Auto-generated method stub
		return wDao.getlastWeekHours(empno);
	}

	public List<SubmissionHistory> getWorktimeInfo(String empno, String currentMonth) {
		// TODO Auto-generated method stub
		if(currentMonth == null) {
			 SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM");
		     Calendar c = Calendar.getInstance();
		     c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		     currentMonth = sdfDate.format(c.getTime());
		}
		Map<String, String> betweenDate = new HashMap<>();
		betweenDate = getbetweenDate(currentMonth);
		String beginDate = betweenDate.get("beginDate");
		String endDate = betweenDate.get("endDate");
		return wDao.getWorktimeInfo(empno, beginDate, endDate);
	}


	public void insertWorktime(String empno, String currentMonth) throws ParseException {
		// TODO Auto-generated method stub
		if(currentMonth == null) {
			 SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM");
		     Calendar c = Calendar.getInstance();
		     c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		     currentMonth = sdfDate.format(c.getTime());
		     System.out.println(sdfDate.format(c.getTime()));
		}
		
		Map<String, String> betweenDate = new HashMap<>();
		betweenDate = getbetweenDate(currentMonth);
		String beginDate = betweenDate.get("beginDate");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(sdf.parse(beginDate));
		wDao.insertWorktime(empno, calBegin);
	}
	
	public Map<String, String> getbetweenDate(String currentMonth) {
		String[] parts = currentMonth.split("-");
    	String year = parts[0]; 
    	String month = parts[1]; 
    	
    	SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd");
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, Integer.parseInt(year));
    	c.set(Calendar.MONTH, Integer.parseInt(month)-1);
    	c.set(Calendar.DATE, 1);
    	c.set(Calendar.DAY_OF_MONTH, 1);
    	while (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
		{
			c.add(Calendar.DATE, 1);
		}
	    String beginDate = sdfDate.format(c.getTime());
	    c.add(Calendar.MONTH,1);
	    c.set(Calendar.DATE, 1); 
	    String endDate = sdfDate.format(c.getTime());
	    Map<String, String> betweenDate = new HashMap<>();
	    betweenDate.put("beginDate", beginDate);
	    betweenDate.put("endDate", endDate);
		return betweenDate;
		
	}




}

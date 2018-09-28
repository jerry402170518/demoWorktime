package service;

import model.Employee;
import model.SubmissionHistory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import dao.EmployeeDAO;
import dao.EmployeeDAOJDBC;

public class EmployeeService {
	
	EmployeeDAO empDAO = new EmployeeDAOJDBC();
	
	/**
	 * 檢查傳入的employee是否帳號與密碼皆正確
	 * @param employee
	 * 員工
	 * @return boolean
	 */
	public boolean checkAccount(Employee employee){
		return empDAO.checkAccount(employee);
	}
	/**
	 * 讀取該帳號之個人資料
	 * @param empno
	 * 員工編號
	 * @return Map
	 */
	public Map<String, String> getLoginInfo(String empno) {
		// TODO Auto-generated method stub
		return empDAO.getLoginInfo(empno);
	}
	/**
	 * 變更密碼
	 * @param empno
	 * 員工編號
	 */
	public void changePassword(String empno,String password) {
		// TODO Auto-generated method stub
		empDAO.changePassword(empno,password);
	}
	/**
	 * 確認帳號是否已啟動
	 * @param string
	 * 員工編號
	 * @return boolean
	 */
	public boolean startedOrNot(String empno) {
		// TODO Auto-generated method stub
		
		return empDAO.startedOrNot(empno);
	}

	public List<Employee> getEmpInfoByName(String inputSearch) {
		// TODO Auto-generated method stub
		return empDAO.getEmpInfoByName(inputSearch);
	}
	public List<Employee> getEmpInfoByEmpno(String inputSearch) {
		// TODO Auto-generated method stub
		return empDAO.getEmpInfoByEmpno(inputSearch);
	}
	public void modifyEmp(String name, String email, String position, String idNumber, String empno) {
		// TODO Auto-generated method stub
		empDAO.modifyEmp(name, email, position, idNumber, empno);
	}
	public void addEmp(String name, String email, String position, String idNumber) {
		// TODO Auto-generated method stub
		// 產生員編
		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat();
    	String md5String = sdf.format(cal.getTime()) + name + idNumber ;
    	String md5 = md5(md5String);
    	String empno = md5.substring(10,20);
    	
    	//驗證此員編存在與否
    	Boolean  empExist = empDAO.checkEmpExist(empno);
    	System.out.println(empExist);
    	if(!empExist) {
    		empDAO.addEmp(name, email, position, idNumber, empno);
    	}

	}
	
	public void getVerification(String empno, String idNumber,HttpServletRequest request) {
		// TODO Auto-generated method stub
		request.getSession().setMaxInactiveInterval(600);
		String md5String =  empno + idNumber ;
    	String verificationCode = md5(md5String);
    	request.getSession().setAttribute("verificationCode", verificationCode);
    	
    	System.out.println(verificationCode);
    	EmailService emailService = new EmailService();
		StringBuilder html = new StringBuilder();
		html.append("<style>.border{border:1px solid gray;height: 1px;}</style>");
		html.append("<center><h1>工時系統-啟動帳號</h1>");
		html.append("<div class='border'></div>");
		html.append("<p>以下寄送的驗證碼有時效性，請在取得驗證碼後十分鐘內盡速開通。</p>");
		html.append("<p>驗證碼:" + verificationCode + "</p>");
		
		Map<String, String> EmployeeInfo = empDAO.getLoginInfo(empno);
		
		String email = EmployeeInfo.get("email");
		System.out.println(email);
		String title = "取得啟動帳號之驗證碼";
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
	
	//傳入字串並回傳一組亂碼
	public static String md5(String s) 
	    {
	        try
	        {
	            // Create MD5 Hash
	            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	            digest.update(s.getBytes());
	            byte messageDigest[] = digest.digest();
	     
	            // Create Hex String
	            StringBuffer hexString = new StringBuffer();
	            for(int i=0; i<messageDigest.length; i++)
	            {
	                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
	            }
	            return hexString.toString();
	        } 
	        catch (NoSuchAlgorithmException e) 
	        {
	            e.printStackTrace();
	        }
	        return "";
	    }
	public void insertPassword(String empno, String password) {
		// TODO Auto-generated method stub
		empDAO.insertPassword(empno, password);
	}
	
	
	
}

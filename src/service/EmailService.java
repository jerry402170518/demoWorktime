package service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServlet;

public class EmailService {

	private Properties props = new Properties();
	private Session session;

	private String to;
	private String Subject = "我很懶，所以沒設定";
	private String Text = "我很懶，所以沒設定";

	public EmailService() {
		// TODO 自動產生的建構子 Stub
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.setProperty("mail.smtp.auth", "true");
		this.session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				// 這定寄送的照號密碼
				return new PasswordAuthentication("test402170518@gmail.com", "pyfmsauilommnqqx");
			}
		});

	}

	// 文字版
	private Message getMessage() throws AddressException, MessagingException {
		Message message = new MimeMessage(this.session);
		// 從哪裡發送
		try {
			message.setFrom(new InternetAddress("test402170518@gmail.com", "工時系統-第三組"));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		// 設定email標題
		message.setSubject(this.Subject);
		// 設定日期
		message.setSentDate(new Date());
		// 設定email內容
		message.setText(this.Text);

		return message;
	}

	// html版
	private Message getHTMLMessage(String html) throws AddressException, MessagingException {
		Message message = new MimeMessage(this.session);
		// 從哪裡發送
		try {
			message.setFrom(new InternetAddress("test402170518@gmail.com", "工時系統-第三組"));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		// 設定email標題
		message.setSubject(this.Subject);
		// 設定日期
		message.setSentDate(new Date());

		MimeBodyPart htmlPart = new MimeBodyPart();

		htmlPart.setContent(html, "text/html;charset=UTF-8");
		;
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(htmlPart);
		message.setContent(multipart);

		return message;
	}

	// 附件+html版
	private Message getFileMessage(String html, File file) throws AddressException, MessagingException {
		Message message = new MimeMessage(this.session);
		// 從哪裡發送
		try {
			message.setFrom(new InternetAddress("test402170518@gmail.com", "工時系統-第三組"));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		// 設定email標題
		message.setSubject(this.Subject);
		// 設定日期
		message.setSentDate(new Date());

		MimeBodyPart htmlPart = new MimeBodyPart();
		MimeBodyPart filePart = new MimeBodyPart();

		htmlPart.setContent("html", "text/html;charset=UTF-8");

		FileDataSource fileDataSource = new FileDataSource(file);

		filePart.setDataHandler(new DataHandler(fileDataSource));
		try {
			filePart.setFileName(MimeUtility.encodeText(fileDataSource.getName(), "UTF-8", "B"));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(htmlPart);
		multipart.addBodyPart(filePart);

		message.setContent(multipart);

		return message;
	}

	private Message getExcelFileMessage(String html, String fileName, byte[] data)
			throws AddressException, MessagingException {
		Message message = new MimeMessage(this.session);
		// 從哪裡發送
		try {
			message.setFrom(new InternetAddress("test402170518@gmail.com", "工時系統-第三組"));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
		// 設定email標題
		message.setSubject(this.Subject);
		// 設定日期
		message.setSentDate(new Date());

		MimeBodyPart htmlPart = new MimeBodyPart();
		MimeBodyPart filePart = new MimeBodyPart();

		htmlPart.setContent(html, "text/html;charset=UTF-8");

		filePart.setContent(data, "application/vnd.ms-excel");
		try {
			filePart.setFileName(MimeUtility.encodeText(fileName, "UTF-8", "B"));
		} catch (UnsupportedEncodingException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(htmlPart);
		multipart.addBodyPart(filePart);

		message.setContent(multipart);

		return message;
	}

	/**
	 * 
	 * 寄送 email。
	 * 
	 * @param To
	 *            傳送到To的電子郵件地址
	 * @param Subject
	 *            email的 標題(或稱主旨)
	 * @param html
	 *            email的html內容
	 * @param file
	 *            email的file內容
	 * @throws AddressException
	 *             電子郵件地址形式錯誤。
	 * @throws MessagingException
	 *             email 設定錯誤
	 */
	public void sendFileMail(String To, String Subject, String html, File file, String uri, HttpServlet ss)
			throws AddressException, MessagingException {
		String path = ss.getServletContext().getRealPath("/").replace("\\", "/") + uri;

		System.out.println("未至:" + path);
		file = new File(path);
		this.Subject = Subject;
		try {
			Message message = this.getFileMessage(html, file);
			// 送到哪個email
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(To));
			Transport.send(message);
		} catch (AddressException e) {
			// TODO 自動產生的 catch 區塊
			throw new AddressException("電子郵件地址設定錯誤: " + e.getMessage());
		} catch (MessagingException e) {
			// TODO 自動產生的 catch 區塊
			throw new MessagingException("電子郵件設定錯誤: " + e.getMessage());
		}
	}

	/**
	 * 
	 * 寄送 email。
	 * 
	 * @param To
	 *            傳送到To的電子郵件地址
	 * @param Subject
	 *            email的 標題(或稱主旨)
	 * @param html
	 *            email的html內容
	 * @param fileName
	 *            email的附件檔案名稱
	 * @param data
	 *            附件內容
	 * @throws AddressException
	 *             電子郵件地址形式錯誤。
	 * @throws MessagingException
	 *             email 設定錯誤
	 */
	public void sendExcelFileMail(String To, String Subject, String html, String fileName, byte[] data)
			throws AddressException, MessagingException {
		this.Subject = Subject;
		try {
			
			Message message = this.getExcelFileMessage(html, fileName+".xls", data);
			// 送到哪個email
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(To));
			Transport.send(message);
		} catch (AddressException e) {
			// TODO 自動產生的 catch 區塊
			throw new AddressException("電子郵件地址設定錯誤: " + e.getMessage());
		} catch (MessagingException e) {
			// TODO 自動產生的 catch 區塊
			throw new MessagingException("電子郵件設定錯誤: " + e.getMessage());
		}
	}

	/**
	 * 寄送 email。
	 * 
	 * @param To
	 *            傳送到To的電子郵件地址
	 * @param Subject
	 *            email的 標題(或稱主旨)
	 * @param Text
	 *            email的內容
	 * @throws AddressException
	 *             電子郵件地址形式錯誤。
	 * @throws MessagingException
	 *             email 設定錯誤
	 */
	public void sendMail(String To, String Subject, String Text) throws AddressException, MessagingException {
		this.Subject = Subject;
		this.Text = Text;

		try {
			Message message = this.getMessage();
			// 送到哪個email
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(To));

			Transport.send(message);
		} catch (AddressException e) {
			// TODO 自動產生的 catch 區塊
			throw new AddressException("電子郵件地址設定錯誤: " + e.getMessage());
		} catch (MessagingException e) {
			// TODO 自動產生的 catch 區塊
			throw new MessagingException("電子郵件設定錯誤: " + e.getMessage());
		}
	}

	/**
	 * 寄送 email。(多人)
	 * 
	 * @param To
	 *            傳送到To的電子郵件地址
	 * @param Subject
	 *            email的 標題(或稱主旨)
	 * @param Text
	 *            email的內容
	 * @throws AddressException
	 *             電子郵件地址形式錯誤。
	 * @throws MessagingException
	 *             email 設定錯誤
	 */
	public void sendMail(String[] To, String Subject, String Text) throws AddressException, MessagingException {
		InternetAddress[] addresses = new InternetAddress[To.length];
		for (int i = 0; i < To.length; i++) {
			addresses[i] = new InternetAddress(To[i]);
		}
		this.Subject = Subject;
		this.Text = Text;

		try {
			Message message = this.getMessage();
			// 送到哪個email
			message.addRecipients(Message.RecipientType.TO, addresses);

			message.saveChanges();

			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", "", "");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException e) {
			// TODO 自動產生的 catch 區塊
			throw new AddressException("電子郵件地址設定錯誤: " + e.getMessage());
		} catch (MessagingException e) {
			// TODO 自動產生的 catch 區塊
			throw new MessagingException("電子郵件設定錯誤: " + e.getMessage());
		}
	}

	/**
	 * 
	 * 寄送 email。
	 * 
	 * @param To
	 *            傳送到To的電子郵件地址
	 * @param Subject
	 *            email的 標題(或稱主旨)
	 * @param html
	 *            email的html內容
	 * @throws AddressException
	 *             電子郵件地址形式錯誤。
	 * @throws MessagingException
	 *             email 設定錯誤
	 */
	public void sendHtmlMail(String To, String Subject, String html) throws AddressException, MessagingException {
		this.Subject = Subject;
		try {
			Message message = this.getHTMLMessage(html);
			// 送到哪個email
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(To));

			MailThread mt = new MailThread();
			mt.addMessage(message);
			if (!mt.isStart())
				mt.start();

		} catch (AddressException e) {
			// TODO 自動產生的 catch 區塊
			throw new AddressException("電子郵件地址設定錯誤: " + e.getMessage());
		} catch (MessagingException e) {
			// TODO 自動產生的 catch 區塊
			throw new MessagingException("電子郵件設定錯誤: " + e.getMessage());
		}
	}

	/**
	 * 
	 * 寄送 email。(多人)
	 * 
	 * @param To
	 *            傳送到To的電子郵件地址
	 * @param Subject
	 *            email的 標題(或稱主旨)
	 * @param html
	 *            email的html內容
	 * @throws AddressException
	 *             電子郵件地址形式錯誤。
	 * @throws MessagingException
	 *             email 設定錯誤
	 */
	public void sendHtmlMail(String[] To, String Subject, String html) throws AddressException, MessagingException {
		InternetAddress[] addresses = new InternetAddress[To.length];
		for (int i = 0; i < To.length; i++) {
			addresses[i] = new InternetAddress(To[i]);
		}
		this.Subject = Subject;
		try {
			Message message = this.getHTMLMessage(html);
			message.addRecipients(Message.RecipientType.TO, addresses);

			sendMailByThread(message);

		} catch (AddressException e) {
			// TODO 自動產生的 catch 區塊
			throw new AddressException("電子郵件地址設定錯誤: " + e.getMessage());
		} catch (MessagingException e) {
			// TODO 自動產生的 catch 區塊
			throw new MessagingException("電子郵件設定錯誤: " + e.getMessage());
		}
	}

	private void sendMailByThread(final Message message) {
		Thread emailThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Transport transport;
					message.saveChanges();

					transport = session.getTransport("smtp");
					transport.connect("smtp.gmail.com", "", "");
					transport.sendMessage(message, message.getAllRecipients());
					transport.close();
				} catch (MessagingException e) {
					// TODO 自動產生的 catch 區塊
					e.printStackTrace();
				}

			}
		}, "寄信緒");

		emailThread.start();
	}

	public static void main(String[] args) {
		EmailService service = new EmailService();

		StringBuilder Text = new StringBuilder();
		Text.append("我是第一行\n");
		Text.append("我是地2行");
		String[] emails = { "test402170518@gamil.com"};
		try {
			service.sendHtmlMail(emails, "sdfd", Text.toString());
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

}

class MailThread {
	private static List<Message> messages;
	private static boolean flag = false;

	protected void addMessage(Message message) {
		if (messages == null) {
			messages = new ArrayList<Message>();
			messages.add(message);
		} else {
			messages.add(message);
		}
	}

	protected int getMessageSize() {
		return messages.size();
	}

	protected void start() {
		flag = true;
		Thread emailThread = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (messages) {
					while (flag) {

						if (!messages.isEmpty()) {
							int count;
							count = (messages.size() > 20) ? 20 : messages.size();
							System.out.println("/**尚有" + messages.size() + "需寄送，開始寄送" + count + "筆**/");
							for (int i = 0; i < count; i++) {
								try {
									Transport.send(messages.get(0));
									messages.remove(0);
								} catch (MessagingException e) {
									// TODO 自動產生的 catch 區塊
									e.printStackTrace();
								}
							}
							System.out.println("/**寄送" + count + "筆完成，尚有" + messages.size() + "需寄送**/");
							try {
								Thread.sleep(5 * 1000);
							} catch (InterruptedException e) {
								// TODO 自動產生的 catch 區塊
								System.err.println("緒 暫停錯誤:" + e.getMessage());
							}
						}
						try {
							Thread.sleep(5 * 1000);
						} catch (InterruptedException e) {
							// TODO 自動產生的 catch 區塊
							System.err.println("緒 暫停錯誤:" + e.getMessage());
						}
					}
				}
			}
		}, "寄信緒");

		emailThread.start();
	}

	protected void stop() {
		flag = false;
	}

	protected boolean isStart() {
		return flag;
	}
}

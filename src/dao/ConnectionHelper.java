package dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionHelper {
		public static Connection getConnection(){
			Connection conn = null;
			try {
				Context initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				DataSource ds = (DataSource) envContext.lookup("jdbc/worktime");
				conn = ds.getConnection();

			} catch (Exception e) {
				// TODO 自動產生的 catch 區塊
				throw new RuntimeException("取得Connection錯誤: " + e.getMessage());
			}
			return conn;
		}
}


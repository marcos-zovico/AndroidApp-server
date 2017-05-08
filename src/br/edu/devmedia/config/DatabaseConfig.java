package br.edu.devmedia.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {
	
	private static DatabaseConfig config;
	

	public static DatabaseConfig getInstance() {
		if (config == null) {
			config = new DatabaseConfig();
		}
		
		return config;
	}
	
	
	public Connection getConnection() throws Exception{
		
		Class.forName("com.mysql.jdbc.Driver");
				
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/android_webapp?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
	}

	
}

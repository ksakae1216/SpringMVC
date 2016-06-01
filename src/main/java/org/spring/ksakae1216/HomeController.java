package org.spring.ksakae1216;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		getDB();
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}

	//AWSのEC2のSQLserver接続サンプル
	private void getDB() {
		try { 
		    Driver d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		    	//IPアドレスはEC2再起動の度に変わります
		        String connUrl = 
		          "jdbc:sqlserver://54.173.251.222:1433;database=testdb;" 
		            + "user=testuser;password=password"; 
		        Connection con = d.connect(connUrl, new Properties());

		        String sql = "select * from Table_1;"; 
		        Statement stmt = con.createStatement(); 
		        ResultSet rs = stmt.executeQuery(sql);

		        while (rs.next()) { 
		        	System.out.println(rs.getString("col1"));
		            System.out.println(rs.getString("col2")); 
		        }

		        rs.close(); 
		        stmt.close(); 
		      } 
		      catch (Exception e) { 
		        e.printStackTrace(); 
		      } 
	}
	
}

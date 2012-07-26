package com.figo.testh2database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String url = "jdbc:h2:/data/data/" +
        	    "com.figo.testh2database" +
        	    "/data/hello" +
        	    ";FILE_LOCK=FS" +
        	    ";PAGE_SIZE=1024" +
        	    ";CACHE_SIZE=8192";
        	try {
				Class.forName("org.h2.Driver");
				Connection conn = DriverManager.getConnection(url);
				if ( null != conn ) {
					Statement stat = conn.createStatement();  
		            // insert data  
		            //stat.execute("DROP TABLE IF EXISTS TEST");  
					stat.execute("DROP TABLE if EXISTS TEST");  
		            stat.execute("CREATE TABLE IF NOT EXISTS TEST(NAME VARCHAR)");  
		            stat.execute("INSERT INTO TEST VALUES('Hello World')");
		            // use data  
		            ResultSet result = stat.executeQuery("select name from test ");  
		            int i = 1;  
		            while (result.next()) {  
		                System.out.println(i++ + ":" + result.getString("name"));  
		            }  
		            result.close();  
		            stat.close();  
		            conn.close();  
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
	}
}

package com.figo.testh2database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private static final String DB_NAME = "encrypted.h2.db";
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (!isExist()) {
			copyDbToFileDir();
		}
        String url = "jdbc:h2:/data/data/" +
        	    "com.figo.testh2database" +
        	    "/files/encrypted" +
        	    ";FILE_LOCK=FS" +
        	    ";PAGE_SIZE=1024" +
        	    ";CACHE_SIZE=8192" +
        	    ";CIPHER=AES";
        String user = "sa";  
        String key = "filepwd  ";  
        	try {
				Class.forName("org.h2.Driver");
				Connection conn = DriverManager.getConnection(url, user, key);
				if ( null != conn ) {
					Statement stat = conn.createStatement();  
		            // insert data  
		            //stat.execute("DROP TABLE IF EXISTS TEST");  
					//stat.execute("DROP TABLE if EXISTS TEST");  
		            //stat.execute("CREATE TABLE IF NOT EXISTS TEST(NAME VARCHAR)");  
		            //stat.execute("INSERT INTO TEST VALUES('Hello World')");
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
	
	/**
	 * 判断数据库是否存在
	 * 
	 * @return
	 */
	public boolean isExist() {
		File file = new File(getFilesDir(), DB_NAME);
		return file.exists();
	}

	/**
	 * 拷贝数据库到系统的/data/data/<包名>下
	 * 
	 * @throws IOException
	 */
	public void copyDbToFileDir() {
		InputStream in = null;
		FileOutputStream fos = null;

		try {
			in = getAssets().open(DB_NAME);
			File file = new File(getFilesDir(), DB_NAME);

			fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			// int len = 0;
			int len = -1;

			while ((len = in.read(buffer)) != -1) {
				// while((len = in.read()) > 0){
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}

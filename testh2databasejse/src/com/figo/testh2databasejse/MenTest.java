package com.figo.testh2databasejse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenTest {

	public void runInsertDelete() {  
        try {  
            String sourceURL = "jdbc:h2:~/encrypted;CIPHER=AES";// H2DB  
                                                                        // mem  
                                                                        // mode  
            String user = "sa";  
            String key = "filepwd  ";  
            try {  
                Class.forName("org.h2.Driver");// HSQLDB Driver  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            Connection conn = DriverManager.getConnection(sourceURL, user, key);// 把驱动放入连接  
            Statement stmt = conn.createStatement();  
            // 创建一个 Statement 对象来将 SQL 语句发送到数据库。  
            // stmt.executeUpdate("DELETE FROM mytable WHERE name=/'NO.2/'");  
            // 执行方法找到一个与 methodName 属性同名的方法，并在目标上调用该方法。  
            // stmt.execute("CREATE TABLE idtable(id INT,name VARCHAR(100));");  
            stmt.execute("DROP TABLE IF EXISTS TEST");  
            stmt.execute("CREATE TABLE TEST(NAME VARCHAR)");  
            stmt.execute("INSERT INTO test VALUES('MuSoft')");  
            stmt.execute("INSERT INTO test VALUES('StevenStander')");  
            stmt.close();  
            conn.close();  
        } catch (SQLException sqle) {  
            System.out.println("SQL ERROR!");  
        }  
    }  
    public void query(String SQL) {  
        try {  
            String sourceURL = "jdbc:h2:~/encrypted;CIPHER=AES";  
            String user = "sa";  
            String key = "filepwd1  ";  
            try {  
                Class.forName("org.h2.Driver");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            Connection conn = DriverManager.getConnection(sourceURL, user, key);// 把驱动放入连接  
            Statement stmt = conn.createStatement();// 创建一个 Statement 对象来将 SQL  
                                                    // 语句发送到数据库。  
            ResultSet rset = stmt.executeQuery(SQL);// 执行方法找到一个与 methodName  
                                                    // 属性同名的方法，并在目标上调用该方法。  
            while (rset.next()) {  
                System.out.println("  " + rset.getString("name"));  
            }  
            rset.close();  
            stmt.close();  
            conn.close();  
        } catch (SQLException sqle) {  
            System.err.println(sqle);  
        }  
    }  
    public static void main(String[] args) {  
        MenTest mt = new MenTest();  
        mt.runInsertDelete();  
        mt.query("SELECT * FROM test");  
    }  
}

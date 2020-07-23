package com.arcelikglobal.hackbot.turkalman.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import com.arcelikglobal.hackbot.turkalman.bot.hackbot;

public class DBConnection {
	private static final String Driver = "org.hsqldb.jdbcDriver";
    private static final String user = "DBArcelikSorular";
    private static final String pwd = "temp";
    private static final String dbPath = "~/ArcelikHackbotDB";
    //private static final String dbPath = "~/dbImplicaspection";
    private static final String url = "jdbc:hsqldb:file:" + dbPath;
    
    public static Connection connect() {
		try {
			System.out.println("Veritabanı'na bağlanmaya çalışılınıyor");
			
			Class.forName(Driver);
			Connection con = DriverManager.getConnection(url, user, pwd);
			return con;
			
		}catch(ClassNotFoundException e) {
			System.out.println("E002: Veritabanı klası bulunamadı");
			e.printStackTrace();
			return null;
		}catch(SQLException e) {
			System.out.println("E002: Veritabanı klası bulundu, ama bağlantı kurulamadı");
		
			return null;
		}
			
	}
	public static boolean addEntry(int messageID, String question, String answer) {
		// Arcelik yetkilisinin alintiladigi yazi 'question', cevaben yazdigi sey ise 'answer'
		
		
		Connection con = connect();
		
		PreparedStatement ps;
		
		
		
		
		try {
			ps = con.prepareStatement("INSERT INTO QUESTIONS VALUES(?, ?)");
			ps.setInt(1, messageID);
			ps.setString(2, question);
			ps.executeUpdate();
			con.commit();
			con.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Soru veritabanina eklenemedi");
			e.printStackTrace();
			return false;
		}
	
	}

}

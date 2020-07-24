package com.arcelikglobal.hackbot.turkalman.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import com.arcelikglobal.hackbot.turkalman.bot.hackbot;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
    
    public static boolean addQuestion(int messageID, String question) {
    	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

    }
    
	public static boolean addAnswer(int messageID, String answer) {
	
		
		
		Connection con = connect();
		
		PreparedStatement ps;
		
		
		
		
		try {

			ps = con.prepareStatement("INSERT INTO ANSWERS(QUESTIONID, ANSWER) VALUES(?, ?)");
			
			ps.setInt(1, messageID);
			ps.setString(2, answer);
			ps.executeUpdate();
			con.commit();
			con.close();
			return true;
			
		} catch (SQLException e) {
			System.out.println("Cevap veritabanina eklenemedi"); ///< Normalde loglanir, henuz logging ile ugrasmadik
			e.printStackTrace();
			return false;
		}
	
	}
	
	public static boolean addTag(int messageID, String tag) {
		
		
		Connection con = connect();
		
		PreparedStatement ps;
		
		
		
		
		try {

			ps = con.prepareStatement("INSERT INTO TAGS(QUESTIONID, TAG) VALUES(?, ?)");
			
			ps.setInt(1, messageID);
			ps.setString(2, tag);
			ps.executeUpdate();
			con.commit();
			con.close();
			
			return true;
			
		} catch (SQLException e) {
			System.out.println("Tag veritabanina eklenemedi"); ///< Normalde loglanir, henuz logging ile ugrasmadik
			e.printStackTrace();
			return false;
		}
	}
	
	public static HashMap<String, Integer> getTags() {
		Connection con = connect();
		

		ResultSet rs;
		
		

		
		try {
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM TAGS");
	       
			HashMap<String, Integer> map = new HashMap<String, Integer>();

			while (rs.next()) {
				map.put(rs.getString("TAG"), rs.getInt("QUESTIONID"));

	        }
			
			;
			
			con.close();
			
			return map;
			
		} catch (SQLException e) {
			System.out.println("Tag veritabanina eklenemedi"); ///< Normalde loglanir, henuz logging ile ugrasmadik
			e.printStackTrace();
			
		}
		return null;
	}
	
	public static ArrayList<String> getAnswerFromQuestionId(int qid){
		Connection con = connect();
		

		ResultSet rs;
		PreparedStatement ps;
		

		
		try {
			ps = con.prepareStatement("SELECT ANSWER FROM ANSWERS WHERE QUESTIONID = ?");
			ps.setInt(1, qid);
			rs = ps.executeQuery();
	       
			ArrayList<String> ans = new ArrayList<String>();

			while (rs.next()) {
				ans.add(rs.getString("ANSWER"));

	        }
		
			con.close();
			
			return ans;
			
		} catch (SQLException e) {
			System.out.println("Tag veritabanina eklenemedi"); ///< Normalde loglanir, henuz logging ile ugrasmadik
			e.printStackTrace();
			
		}
		return null;
	}
	
	

}

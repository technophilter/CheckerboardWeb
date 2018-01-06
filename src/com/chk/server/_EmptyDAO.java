//
//
//package com.chk.server;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.apache.log4j.Logger;
//
//import com.chk.shared.UserDTO;
//import com.chk.shared.SharedConstants;
//
//public class _EmptyDAO {
//
//	private static Logger logger = Logger.getLogger(_EmptyDAO.class);
//	
//	  public Connection getConnect() throws Exception{
//
//	      Class.forName("com.mysql.jdbc.Driver"); 
//	      Connection connect = DriverManager
//	          .getConnection("jdbc:mysql://localhost?"
//	        		  + "user=chkapp&password=Matrix99!PooPoo441223!!@");
//	     // logger.debug("CONNECT OPEN");
//	      return connect;
//	  }
//	  
//	  public RETURNTYPE METHODNAME(TYPE INPUT) {
//		  Connection connect = null;
//		  PreparedStatement preparedStatement = null;
//		  ResultSet rs = null;
//		  
//		  try {
//			connect = getConnect();
//		      preparedStatement = connect
//		              .prepareStatement("select password_hash from jhm.user_password where user_login = ?;");
//		      preparedStatement.setString(1, email);
//		      rs = preparedStatement.executeQuery();
//		      while (rs.next()){
//		    	  hash = rs.getString("password_hash");
//		    	  return hash;
//		      }
//		      return SharedConstants.USER_NOT_FOUND;
//	
//			} catch (Exception e) {
//				e.printStackTrace();
//				return SharedConstants.SYSTEM_ERROR;
//			} finally {
//			    try {
//			      if (rs != null) {
//			        rs.close();
//			      }
//			      if (preparedStatement != null) {
//			    	  preparedStatement.close();
//			      }
//			      if (connect != null) {
//			        connect.close();
//			      }
//			    } catch (Exception e) {
//			    		// shat
//			    }
//		    } 
//	  }
//	
//
//	  
//
//	  
//
//}
//

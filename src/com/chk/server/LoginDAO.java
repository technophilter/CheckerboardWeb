

package com.chk.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.chk.shared.UserDTO;
import com.chk.shared.SharedConstants;

public class LoginDAO {

	private static Logger logger = Logger.getLogger(LoginDAO.class);
	
	
	  public Connection getConnect() throws Exception{

	      Class.forName("com.mysql.jdbc.Driver");
	      // Setup the connection with the DB
	      Connection connect = DriverManager
	          .getConnection("jdbc:mysql://nsd.cyhkvr2zultd.us-east-1.rds.amazonaws.com?"
	        		  + "user=checkerapp&password=Matrix99!PooPoo441223!!@");
	     // logger.debug("CONNECT OPEN");
	      return connect;
	  }
	  
	  public String getHash(String email) {
		  Connection connect = null;
		  PreparedStatement preparedStatement = null;
		  ResultSet rs = null;
		  
		  String hash;	
		  // take the pwd, gen a salt value, store username and salt, not password
		  try {
			connect = getConnect();
		      preparedStatement = connect
		              .prepareStatement("select password_hash from chkbd.user_password where user_login = ?;");
		      preparedStatement.setString(1, email);
		      rs = preparedStatement.executeQuery();
		      while (rs.next()){
			    	  hash = rs.getString("password_hash");
			    	  return hash;
		      }
		      return SharedConstants.USER_NOT_FOUND;
	
			} catch (Exception e) {
				e.printStackTrace();
				return SharedConstants.SYSTEM_ERROR;
			} finally {
			    try {
				      if (rs != null) {
				        rs.close();
				      }
				      if (preparedStatement != null) {
				    	  preparedStatement.close();
				      }
				      if (connect != null) {
				        connect.close();
				      }
				    } catch (Exception e) {
			     }
		    } 
	  }
	

	  
	  public void storeSessionId(String sessionId, String userId) {
		  Connection connect = null;
		  PreparedStatement preparedStatement = null;
		  ResultSet rs = null;
		  
		  try {
			connect = getConnect();
		      preparedStatement = connect
		              .prepareStatement("update chkbd.user_password set session_id = ?, last_login = ? where user_login = ?");
		      preparedStatement.setString(1, sessionId);
		      preparedStatement.setString(2, getTimestamp());		      
		      preparedStatement.setString(3, userId); 
		      preparedStatement.executeUpdate();
		      
		      return;
	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			    try {
				      if (rs != null) {
				        rs.close();
				      }
				      if (preparedStatement != null) {
				    	  preparedStatement.close();
				      }
				      if (connect != null) {
				        connect.close();
				      }
			    } catch (Exception e) {
			    }
		    } 
	  }

	  
	  
	  public String getTimestamp(){
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   //get current date time with Date()
		   Date date = new Date();
		   return dateFormat.format(date).toString();
	  }
//
//	public String resetPassword(String user) {
//		// delete the user password
//		// return the registration hash to eamil to the user
//
//		  Connection connect = null;
//		  PreparedStatement preparedStatement = null;
//		  ResultSet rs = null;
//		  
//		  try {
//			connect = getConnect();
//		      preparedStatement = connect
//		              .prepareStatement("select register_hash from jhm.user_password where user_login = ?");
//		      preparedStatement.setString(1, user);
//		      rs = preparedStatement.executeQuery();
//		      while (rs.next()){
//		    	  String hash = rs.getString("register_hash");
//		    	  return hash;
//		      }
//		      return SharedConstants.USER_NOT_FOUND;
//	
//			} catch (Exception e) {
//				e.printStackTrace();
//				return SharedConstants.USER_NOT_FOUND;
//			} finally {
//			    try {
//				      if (rs != null) {
//				        rs.close();
//				      }
//				      if (preparedStatement != null) {
//				    	  preparedStatement.close();
//				      }
//				      if (connect != null) {
//				        connect.close();
//				      }
//				    } catch (Exception e) {
//			     }
//		    } 
//	}
//
//	public String changePassword(String user, String pass, String auth) {
//		// check auth token for given user, if it matches, we're good, if not - die
//		// if auth is correct, store 
//		  Connection connect = null;
//		  PreparedStatement preparedStatement = null;
//		  ResultSet rs = null;
//		  
//		  boolean validInfo = false;
//		  
//		  try {
//			connect = getConnect();
//		      preparedStatement = connect
//		              .prepareStatement("select register_hash from jhm.user_password where user_login = ?");
//		      preparedStatement.setString(1, user);
//		      rs = preparedStatement.executeQuery();
//		      while (rs.next()){
//		    	  String authhash = rs.getString("register_hash");
//		    	  if (authhash != null && authhash.equalsIgnoreCase(auth)){
//		    		  // ok, we've found them and they match
//		    		  validInfo = true;
//		    	  } else {
//		    		  // couldn't find them or bad info
//				      return SharedConstants.USER_NOT_FOUND;
//		    	  }
//		      }
//
//		      preparedStatement.close();
//		      rs.close();
//		      
//		      if (validInfo){
//			      preparedStatement = connect
//			              .prepareStatement("update jhm.user_password set password_hash = ? where user_login = ?");
//			      preparedStatement.setString(1, pass);
//			      preparedStatement.setString(2, user);
//			      preparedStatement.execute();
//			      return SharedConstants.SUCCESS;
//		      }
//		      return SharedConstants.USER_NOT_FOUND;
//			} catch (Exception e) {
//				e.printStackTrace();
//				return SharedConstants.USER_NOT_FOUND;
//			} finally {
//			    try {
//				      if (rs != null) {
//				        rs.close();
//				      }
//				      if (preparedStatement != null) {
//				    	  preparedStatement.close();
//				      }
//				      if (connect != null) {
//				        connect.close();
//				      }
//				    } catch (Exception e) {
//			     }
//		    } 
//		
//	}

	public boolean validateSession(String sessionId, String userId) {
		  Connection connect = null;
		  PreparedStatement preparedStatement = null;
		  ResultSet rs = null;	  
		  
		  logger.debug("calling validate session in DAO");
		  logger.debug("userId: "+ userId);
		  
		  boolean isValid = false;
		  try {
			connect = getConnect();
		      preparedStatement = connect
		              .prepareStatement("select session_id from chkbd.user_password where user_login = ?");
		      preparedStatement.setString(1, userId);
		      rs = preparedStatement.executeQuery();

		      logger.debug(preparedStatement);
		      
		      while (rs.next()){
		    	  
			    	  String dbSessionId = rs.getString("session_id");
			    	  
			    	  logger.debug(">>>>>>>>>>>>>>>>>>>>>>>> SESSION_ID_FROM_DB >>>>>>>>>>>>" + dbSessionId);
			    	  logger.debug(">>>>>>>>>>>>>>>>>>>>>>>> SESSION_ID >>>>>>>>>>>>" + sessionId);
			    	  logger.debug(">>>>>>>>>>>>>>>>>>>>>>>> USER_ID >>>>>>>>>>>>" + userId);
			    	  
			    	  // test to see if they've ever logged in, if not, this is their first login... :-)
			    	  if (dbSessionId == null){
			    		  // they've never logged in.  :-) 
			    		  // we should update their db session id to be their session id and return is valid
			    		  PreparedStatement ps2 = connect.prepareStatement("insert into chkbd.user_password(session_id) values(?);");
			    		  ps2.setString(1, sessionId);
			    		  isValid = true;
			    		  break;
			    	  } else if (dbSessionId.equals(sessionId)){
			    		  isValid = true;
			    		  break;
			    	  }
		      }
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			    try {
				      if (rs != null) {
				        rs.close();
				      }
				      if (preparedStatement != null) {
				    	  preparedStatement.close();
				      }
				      if (connect != null) {
				        connect.close();
				      }
				    } catch (Exception e) {
			     }
		    } 
		  
		  return isValid;
	}
 

}


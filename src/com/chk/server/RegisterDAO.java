package com.chk.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.chk.shared.SharedConstants;
import com.chk.shared.UserDTO;

public class RegisterDAO {

	  public Connection getConnect() throws Exception{

	      Class.forName("com.mysql.jdbc.Driver");
	      // Setup the connection with the DB
	      Connection connect = DriverManager
	          .getConnection("jdbc:mysql://nsd.cyhkvr2zultd.us-east-1.rds.amazonaws.com?"
	        		  + "user=checkerapp&password=Matrix99!PooPoo441223!!@");
	     // logger.debug("CONNECT OPEN");
	      return connect;
	  }
	  
	

		public String storeUserRegisterInfo(UserDTO userDTO, String registerHash) {
			
			Connection connect = null;
			Statement statement = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			try{
				
				  connect = getConnect();
				  
				  preparedStatement  = connect.prepareStatement("select count(1) from chkbd.user_password where user_login = ?; ");
				  preparedStatement.setString(1, userDTO.getEmail());
			      rs = preparedStatement.executeQuery();
			      while (rs.next()){
			    	  // examine the count
				    	  if (rs.getInt(1) > 0){
				    		  // oops, we found them already
				    		  return SharedConstants.USER_ALREADY_EXISTS;
				    	  }
			      }
				  
			      // ok, didn't find them, press on
			      preparedStatement.close();
			      rs.close();
				  
			      // create a user_password record 
				  preparedStatement = connect.prepareStatement("insert into chkbd.user_password(user_login, password_hash, register_hash) values(?,?, ?); ");
				  preparedStatement.setString(1, userDTO.getEmail());
				  preparedStatement.setString(2, userDTO.getPass());
				  preparedStatement.setString(3, registerHash);
				  preparedStatement.execute();
				  preparedStatement.close();
				 			  
				  return SharedConstants.SUCCESS;  
			    
			} catch (Exception e) {
				e.printStackTrace();
				return SharedConstants.SYSTEM_ERROR;	
			} finally {
			    try {
				      if (rs != null) {
				        rs.close();
				      }
				      if (statement != null) {
				        statement.close();
				      }
				      if (connect != null) {
				        connect.close();
				      }
				    } catch (Exception e) {
			     }
		    } 
		}

}

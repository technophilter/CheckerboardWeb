package com.chk.server;

import java.util.Date;

import org.apache.log4j.Logger;

import com.chk.client.serviceasync.RegisterService;
import com.chk.shared.SharedConstants;
import com.chk.shared.UserDTO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet; 

public class RegisterServiceImpl extends RemoteServiceServlet implements RegisterService{

	private static final long serialVersionUID = 1L;
	String authToken;
	String passwordHash;
	RegisterDAO registerDAO = new RegisterDAO();
	MailSender mailSender = new MailSender();
	private static Logger logger = Logger.getLogger(RegisterServiceImpl.class);
	
 
	public String checkDupeAndRegister(UserDTO user) {
		// generate an auth token
		try {
			// this auth token will be if we want to send them an email and validate it by making them use this access code
			authToken = getRegisterEmailHash();
			// create a hash of thier password and stick in the UserDTO
			passwordHash = getHashForPassword(user.getPass());
			// stick passwordHash in userDTO
			user.setPass(passwordHash);
		} catch (Exception e) {
			authToken = "12345";
			e.printStackTrace();
		}
		
		// store their info in the DB
		String result = registerDAO.storeUserRegisterInfo(user, authToken);
		
		// check to see if they already existed
		if (result.equals(SharedConstants.USER_ALREADY_EXISTS)){
			return result;
		}
		
		return SharedConstants.SUCCESS;
	}
	
	  public String getRegisterEmailHash() throws Exception {
		  Date date = new Date();
		  String val = date.toString();
		  String hash = BCrypt.hashpw(val, BCrypt.gensalt()); 
		  hash = hash.replaceAll("\\W+", "");
		  return hash.substring(2, 7);
	  }

	  public String getHashForPassword(String pass) {
			String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt());  
			return hashPass;
	  }

}

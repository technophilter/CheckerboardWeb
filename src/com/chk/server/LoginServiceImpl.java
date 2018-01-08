package com.chk.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.chk.client.serviceasync.LoginService;
import com.chk.server.BCrypt;
import com.chk.shared.SharedConstants;
import com.chk.server.LoginDAO; 

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{

	LoginDAO loginDAO = new LoginDAO();
	MailSender mailSender = new MailSender();
	private static Logger logger = Logger.getLogger(LoginServiceImpl.class);
	
	/***
	 * Logs you in
	 */
	public String login(String user, String pass) {
		
		HttpServletRequest request = this.getThreadLocalRequest();
		request.getSession().invalidate();
		HttpSession session = request.getSession();
		
	    String hashFromDB = loginDAO.getHash(user);
	    
	    boolean valid = false;
	    
	    if (!hashFromDB.equals(SharedConstants.USER_NOT_FOUND)){
	    	valid = BCrypt.checkpw(pass, hashFromDB);
	    }
	    
	    if ( valid ) {
	    	
	    	String sessionId = session.getId();
	    	
	    	// store session id for subsequent lookups to validate user
	    	loginDAO.storeSessionId(sessionId, user);
	    	
	    	// stick the userId in the session for lookups
	    	// we need this in the session so we can query with the id and validate session id on every page
	    	session.setAttribute("userId", user);

	    	return SharedConstants.LOGIN_SUCCESS;
	    	
	    } else {
	    	// either we didn't find them or they put in the wrong info
	    	return SharedConstants.USER_NOT_FOUND;
	    }
		
		

	}

	/***
	 * This method blanks out their current password and emails auth token
	 */
	public String resetPassword(String user) {

		// check for success and send an email from here
		String resetToken = loginDAO.resetPassword(user);
		
		if (!resetToken.equalsIgnoreCase(SharedConstants.USER_NOT_FOUND)){
			// email out the token
			// email their auth token
			mailSender.resendToken(user, resetToken);
			return resetToken;
		} else {
			// something is fucky if we couldn't find them
			return SharedConstants.USER_NOT_FOUND;
		}

		
	}
	
	

	/***
	 * Updates your pwd if auth token is correct
	 */
	public String changePassword(String user, String pass, String auth) {
		// hash the password before we send it
		String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt()); 
		return loginDAO.changePassword(user, hashPass, auth);
	}
	
	/**
	 * Validates session for us so we know they successfully loged in
	 * Used by every page
	 * @param userId
	 * @return
	 */
	public boolean validateSession(String userId){
	    	// we pass the user id to this mehtod, it looks up the session from
			// the gwt server side stuffs, then uses the dao to lookup 
			// the session stored when they logged in, those should match
			logger.debug("validate login session method");
			
			
			
			HttpServletRequest request = this.getThreadLocalRequest();
	    	HttpSession session = request.getSession();
	    	String sessionId = session.getId();
	    	
	    	logger.debug("sessionId: " + sessionId);
	    	
	    	return loginDAO.validateSession(sessionId, userId);
    	
	}
	

}

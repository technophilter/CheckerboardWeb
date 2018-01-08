package com.chk.client.serviceasync;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService{

	String login(String user, String pass);
	
//	String resetPassword(String user);

//	String changePassword(String user, String pass, String auth);
	
	boolean validateSession(String userId);
	
}

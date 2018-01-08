package com.chk.client.serviceasync;

import com.chk.shared.UserDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("register")
public interface RegisterService extends RemoteService{

	String checkDupeAndRegister(UserDTO user);
	
}

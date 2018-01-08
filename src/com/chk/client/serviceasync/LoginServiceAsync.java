package com.chk.client.serviceasync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

//	void changePassword(String user, String pass, String auth, AsyncCallback<String> callback);

	void login(String user, String pass, AsyncCallback<String> callback);

//	void resetPassword(String user, AsyncCallback<String> callback);

	void validateSession(String userId, AsyncCallback<Boolean> callback);

}

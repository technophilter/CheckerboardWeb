package com.chk.client.serviceasync;

import com.chk.shared.UserDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RegisterServiceAsync {

	void checkDupeAndRegister(UserDTO user, AsyncCallback<String> callback);

}

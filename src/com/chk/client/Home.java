package com.chk.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel; 
// import com.chk.client.widgees.navigation.NavigationLoggedIn;
import com.chk.shared.SharedConstants;
import com.chk.client.serviceasync.LoginService;
import com.chk.client.serviceasync.LoginServiceAsync;



public class Home implements EntryPoint {
	
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	String userId = Cookies.getCookie("uid"); 

	@Override
	public void onModuleLoad() {
		
		// read userId from cookie and validate session info

		loginService.validateSession(userId, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				// something bad happened, redirect
				RootPanel.get("errors").add(new Label(SharedConstants.PRETTY_SYSTEM_ERROR));
				caught.printStackTrace();
			}
			public void onSuccess(Boolean result) {
				// got a response back, now check it
				if (result){
					onLoggedIn();
				} else {
					// says not logged in, redirect
					Window.Location.assign(GWT.getHostPageBaseURL() + "./login.jsp");
				}
			}
		});

	}
	
	public void onLoggedIn(){
		// nav top
//		NavigationLoggedIn nav = new NavigationLoggedIn();
//		RootPanel.get("nav").add(nav);


	}

}

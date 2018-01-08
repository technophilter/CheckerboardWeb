package com.chk.client;

import com.chk.client.serviceasync.LoginService;
import com.chk.client.serviceasync.LoginServiceAsync;
import com.chk.client.widgees.navigation.NavigationLoggedInWidget;
import com.chk.shared.SharedConstants;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


public class Home implements EntryPoint {
	
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	String userId = Cookies.getCookie("uid"); 

 
	public void onModuleLoad() {

		loginService.validateSession(userId, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				// something bad happened
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
		NavigationLoggedInWidget nav = new NavigationLoggedInWidget();
		RootPanel.get("nav").add(nav);

	}

}

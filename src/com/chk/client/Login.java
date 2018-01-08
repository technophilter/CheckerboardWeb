package com.chk.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.chk.client.widgees.loginregister.LoginBox;
import com.chk.client.widgees.navigation.NavigationWidget;

public class Login implements EntryPoint {

 
	public void onModuleLoad() {


		// nav top
		NavigationWidget nav = new NavigationWidget();
		RootPanel.get("nav").add(nav);

		// login box
		LoginBox lb = new LoginBox();
		RootPanel.get("lb").add(lb);

		
	}

}

package com.chk.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.chk.client.widgees.loginregister.RegisterBox;
import com.chk.client.widgees.navigation.NavigationWidget;

public class Register implements EntryPoint  {

 
	public void onModuleLoad() {


		// nav top
		NavigationWidget nav = new NavigationWidget();
		RootPanel.get("nav").add(nav);

		// login box
		RegisterBox rb = new RegisterBox();
		RootPanel.get("rb").add(rb);

		
	}

}

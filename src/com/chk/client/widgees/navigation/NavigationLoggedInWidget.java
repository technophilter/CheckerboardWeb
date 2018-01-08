package com.chk.client.widgees.navigation;

import org.gwtbootstrap3.client.ui.AnchorListItem;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NavigationLoggedInWidget extends Composite {

	private static NavigationLoggedInWidgetUiBinder uiBinder = GWT.create(NavigationLoggedInWidgetUiBinder.class);

	interface NavigationLoggedInWidgetUiBinder extends UiBinder<Widget, NavigationLoggedInWidget> {
	}
	
	@UiField AnchorListItem ancLogout; 
	

	public NavigationLoggedInWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		// logout link
		ancLogout.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Window.Location.assign(GWT.getHostPageBaseURL() + "./logout.jsp");
			}
		});
		 
	}



}

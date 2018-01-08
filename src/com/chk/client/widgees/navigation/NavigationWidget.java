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

public class NavigationWidget extends Composite {

	private static NavigationWidgetUiBinder uiBinder = GWT.create(NavigationWidgetUiBinder.class);

	interface NavigationWidgetUiBinder extends UiBinder<Widget, NavigationWidget> {
	}
	
	@UiField AnchorListItem ancLogin;
	@UiField AnchorListItem ancJoin;
	

	public NavigationWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		
		
		// login link
		ancLogin.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Window.Location.assign(GWT.getHostPageBaseURL() + "./login.jsp");
			}
		});
		
		// register link
		ancJoin.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Window.Location.assign(GWT.getHostPageBaseURL() + "./register.jsp");
			}
		});
		
	}



}

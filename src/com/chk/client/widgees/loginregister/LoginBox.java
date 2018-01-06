package com.chk.client.widgees.loginregister;

import java.util.Date;

import org.apache.log4j.Logger;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.constants.ValidationState;
import org.gwtbootstrap3.extras.animate.client.ui.Animate;
import org.gwtbootstrap3.extras.animate.client.ui.constants.Animation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.chk.client.serviceasync.LoginService;
import com.chk.client.serviceasync.LoginServiceAsync;
import com.chk.shared.SharedConstants; 

public class LoginBox extends Composite {

	private static LoginBoxUiBinder uiBinder = GWT.create(LoginBoxUiBinder.class);

	interface LoginBoxUiBinder extends UiBinder<Widget, LoginBox> {
	}
	
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	@UiField Anchor forgotLink;
	@UiField Button btnLogin;
	@UiField Input txtEmail;
	@UiField Input txtPass;
	@UiField FormGroup loginFormGroup;
	@UiField Modal wrongPass;
	
	public LoginBox() {
		initWidget(uiBinder.createAndBindUi(this));
		
		Logger logger = Logger.getLogger(LoginBox.class);
		logger.debug("Login DEBUG LOG Msg");
		
		// forgot password link
		forgotLink.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Window.Location.assign(GWT.getHostPageBaseURL() + "./passwordreset.jsp");
			}
		});
		
		// login button
		btnLogin.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Date today = new Date();
				
				// conditional check to see if a "UID" cookie exists, this can happen
				// when multiple people log into the same machine
				// just try and remove it.
				Cookies.removeCookie("uid");
				Cookies.removeCookie("uid","/");
				Cookies.removeCookie("uid","/sugar");
				Cookies.removeCookie("uid","/sugar/");// causes issues if we don't do this when multiple people use the same pc
				Cookies.setCookie("uid", txtEmail.getText(),  new Date(today.getTime() + (1000 * 60 * 60 * 24 * 365 * 4500 ))); 

				// validate they put stuff in before calling to check
				if (txtEmail!=null && txtEmail.getText().length() > 3 && 
						txtPass!=null && txtPass.getText().length() > 3 ){
					// ok, see if it's valid
								    	
					loginService.login(txtEmail.getText(), txtPass.getText(), new AsyncCallback<String>(){
						public void onFailure(Throwable caught) {
							// something shitty happened
							caught.printStackTrace();
						}
						public void onSuccess(String result) {
							// ok, got a result, check to see if the pwd is correct
							if (result.equals(SharedConstants.LOGIN_SUCCESS)){
								// ok, log in is good... write the cookie that apparently is used by a bunch of shit
								
								Window.Location.assign(GWT.getHostPageBaseURL() + "./home.jsp");
							} else {
								// modal and make them try again
								wrongPass.show();
							}
						}
					});
							
				} else {
					// they didn't put in a username or a password, dick bags
					loginFormGroup.setValidationState(ValidationState.ERROR);
					Animate.animate(btnLogin, Animation.RUBBER_BAND);
				}

			}
		});
		
		txtPass.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
		        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		            btnLogin.click();
		        }
			}

		});
	}

}

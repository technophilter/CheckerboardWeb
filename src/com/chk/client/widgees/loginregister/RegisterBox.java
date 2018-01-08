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

import com.chk.client.serviceasync.RegisterService;
import com.chk.client.serviceasync.RegisterServiceAsync;
import com.chk.shared.SharedConstants;
import com.chk.shared.UserDTO;
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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget; 

public class RegisterBox extends Composite {

	private static RegisterBoxUiBinder uiBinder = GWT.create(RegisterBoxUiBinder.class);

	interface RegisterBoxUiBinder extends UiBinder<Widget, RegisterBox> {
	}
	
	private final RegisterServiceAsync registerService = GWT.create(RegisterService.class);

	@UiField Button btnRegister;
	@UiField Button btnLogin;
	@UiField Button btnLogin2;
	@UiField Button btnDiffEmail;
	@UiField Input txtEmail;
	@UiField Input txtPass;
	@UiField FormGroup registerFormGroup;
	@UiField Modal userExists; 
	@UiField Modal congrats; 
	
	public RegisterBox() {
		initWidget(uiBinder.createAndBindUi(this));
		
		Logger logger = Logger.getLogger(RegisterBox.class);
		logger.debug("register DEBUG LOG Msg");
 		
		// register button
		btnRegister.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Date today = new Date();
				
				// conditional check to see if a "UID" cookie exists, this can happen
				// when multiple people log into the same machine
				// just try and remove it.
				Cookies.removeCookie("uid");
				Cookies.removeCookie("uid","/");
				Cookies.removeCookie("uid","/checker");
				Cookies.removeCookie("uid","/checker/");// causes issues if we don't do this when multiple people use the same pc
				Cookies.setCookie("uid", txtEmail.getText(),  new Date(today.getTime() + (1000 * 60 * 60 * 24 * 365 * 4500 ))); 

				// validate they put stuff in before calling to check
				if (txtEmail!=null && txtEmail.getText().length() > 3 && 
						txtPass!=null && txtPass.getText().length() > 3 ){
					
					// ok, see if they've already registered
					UserDTO user = new UserDTO();
					user.setEmail(txtEmail.getText());
					user.setPass(txtPass.getText());
					registerService.checkDupeAndRegister(user, new AsyncCallback<String>(){
						public void onFailure(Throwable caught) {
							// if we fail, redirect them anyway
							// Do something here, not sure what
							caught.printStackTrace();
						}
						public void onSuccess(String result) {
							// check to see if the user exists...
							if (result.equals(SharedConstants.USER_ALREADY_EXISTS)){
								userExists.show();
							} else {
								// ?? Redirect to login?
								congrats.show(); 
							}
						}
					});			    	
 
							
				} else {
					// they didn't put in a username or a password, dick bags
					registerFormGroup.setValidationState(ValidationState.ERROR);
					Animate.animate(btnRegister, Animation.RUBBER_BAND);
				}

			}
		});
		
		txtPass.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
		        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
		            btnRegister.click();
		        }
			}
		});
		
		
		btnDiffEmail.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) { 
				userExists.hide();
			}
		});
		
		btnLogin.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) { 
				Window.Location.assign(GWT.getHostPageBaseURL() + "./login.jsp");
			}
		});
		
		btnLogin2.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) { 
				Window.Location.assign(GWT.getHostPageBaseURL() + "./login.jsp");
			}
		});
	}

}

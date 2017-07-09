package myApp.client;

import com.google.gwt.core.client.EntryPoint;

import myApp.frame.Login;

public class PAMS implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Login login = new Login();
		login.open(); //로그인 오픈 
	}
}

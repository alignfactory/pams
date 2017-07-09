package myApp.frame;

import java.util.Date;

import myApp.client.sys.model.CompanyModel;
import myApp.client.sys.model.UserModel;
import com.google.gwt.i18n.client.DateTimeFormat;

public class LoginUser {

	private static UserModel loginUser;
 	private static CompanyModel loginCompany; 
	
	public static UserModel getLoginUser() {
		return loginUser;
	}

	public static void setLoginUser(UserModel loginUser) {
		LoginUser.loginUser = loginUser;
	}
	
	public static void setLoginCompany(CompanyModel companyModel){
		loginCompany = companyModel; 
	}
	
	public static CompanyModel getLoginCompany(){
		return loginCompany; 
	}
	
	public static Boolean isAdmin(){
		
		return "true".equals(loginUser.getAdminYn()); 
	}
	
	public static String getYear(){
		return DateTimeFormat.getFormat( "yyyy" ).format( new Date()) ; 
	}
	
	public static String getToday(){
		return DateTimeFormat.getFormat( "yyyy-mm-dd" ).format(new Date()) ; 
	}
}

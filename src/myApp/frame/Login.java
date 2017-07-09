package myApp.frame;

import myApp.client.sys.model.CompanyUserModel;
import myApp.client.sys.model.UserModel;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.InterfaceLookupResult;
import myApp.frame.ui.SimpleMessage;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CenterLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.Viewport;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;

public class Login implements InterfaceServiceCall {
	
//	private final Dialog loginDialog = new Dialog();
	private TextField firstName = new TextField();
	private PasswordField password= new PasswordField();
    private CenterLayoutContainer container = new CenterLayoutContainer();
    Viewport viewport = new Viewport();
    
	public void open() {
		
		FieldLabel loginFieldLabel = new FieldLabel(firstName, "로그인ID ");
		loginFieldLabel.setLabelWidth(60);
		firstName.setText("alignfactory@gmail.com");
		
		FieldLabel passwdFieldLabel = new FieldLabel(password, "패스워드 ");  
		passwdFieldLabel.setLabelWidth(60);
		passwdFieldLabel.setWidth(264);
		password.setText("1234");

		VerticalLayoutContainer vlc = new VerticalLayoutContainer();
		HTML image = new HTML("<center><div><img src='img/Login.jpg' width='300' height='150'></center></div>"); 
		vlc.add(image, new VerticalLayoutData(300, -1, new Margins(0, 0, 10, 0)));
		
		vlc.add(loginFieldLabel, new VerticalLayoutData(280, -1, new Margins(0, 0, 5, 15)));
		
		HBoxLayoutContainer hBoxLayout = new HBoxLayoutContainer(); 
		hBoxLayout.add(passwdFieldLabel, new BoxLayoutData(new Margins(0, 0, 0, 0))); 
		
		TextButton okButton = new TextButton("OK"); 
		okButton.setWidth(40);
		okButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				getService(); // 함수로 빼서 호출한다. 
			}
		});

		hBoxLayout.add(okButton, new BoxLayoutData(new Margins(0, 0, 0, 6)));
		vlc.add(hBoxLayout, new VerticalLayoutData(350, -1, new Margins(0, 0, 0, 15)));
		
		Label loginDesc = new HTML("<font size='2'>※ Login ID는 등록된 E-Mail ID를 사용 바랍니다. <br>※ 오류 발생시 담당자에게 문의 바랍니다.<br></font>");
		vlc.add(loginDesc, new VerticalLayoutData(350, -1, new Margins(20, 0, 0, 15)));
		 
		FormPanel formPanel = new FormPanel();
    	formPanel.setBorders(false);
//		formPanel.setLabelWidth(60); // 모든 field 적용 후 설정한다.
    	formPanel.setWidth(330);
    	formPanel.setHeight(400);
		
	    formPanel.setWidget(vlc);
	    //formPanel.setBorders(true);
	    
		container.add(formPanel); //, new MarginData(30));
		viewport.add(container);

		RootPanel.get().add(viewport);
	}
	


	public void getService(){
		ServiceRequest request = new ServiceRequest("sys.User.getLoginInfo");
		request.add("loginId", firstName.getText());
		request.add("passwd", password.getText());

		ServiceCall service = new ServiceCall(); 
		service.execute(request, this);
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() > 0){
			
			UserModel user = (UserModel) result.getResult(0); 
			LoginUser.setLoginUser(user); 

			if(LoginUser.isAdmin()) {
				// 관리자이다. 로그인할 회사를 선택한다. 
				Lookup_LoginCompany loginCompany = new Lookup_LoginCompany();
				loginCompany.setCallback(new InterfaceLookupResult(){
					@Override
					public void setLookupResult(Object result) {
						
						
						// 유치원 선택화면에서 선택한 유치원을 로그인한 유치원으로 설정한다.  
						CompanyUserModel userCompanyModel = (CompanyUserModel)result; 
						Info.display("Login company is", "" + userCompanyModel.getCompanyId());
						
						LoginUser.setLoginCompany(userCompanyModel.getCompanyModel());
						
						// 유치원 세팅하고 메인 윈도우를 오픈한다.
						openFrame(); 
					}
				});
				
				loginCompany.show();
			}
			else {
				// 일반사용자는 본인의 회사를 로그인 회사로 설정한다. 
				LoginUser.setLoginCompany(user.getCompanyModel());
				openFrame(); 
			}
		}
		else {
			new SimpleMessage("로그인 실패", result.getMessage()); 
		}
	}
	
	private void openFrame(){
		// 일반 사용자이다. 회사 선택없이 로드인한다. 
		this.viewport.remove(container);
		
		Main_Window window = new Main_Window(); 
		viewport.add(window.getMainWindow());
		RootPanel.get().add(viewport);
	}
}


/*
public void open1(){
	
	firstName.setText("alignfactory@gmail.com");
	password.setText("1234");
	 
	loginDialog.setBodyBorder(false);
	loginDialog.getHeader().setIcon(ResourceIcon.INSTANCE.dBButton() ); //(+) 이미지를 가져온다. ;
	loginDialog.setResizable(false);
	loginDialog.setHeading("PSmis login");
	loginDialog.setHeaderVisible(true);
	loginDialog.setWidth(400);
	loginDialog.setHeight(400);
	
	loginDialog.getButton(PredefinedButton.OK).setWidth(60);
	loginDialog.getButton(PredefinedButton.OK).addSelectHandler(new SelectHandler() {
		@Override
		public void onSelect(SelectEvent event) {
			getService(); // 함수로 빼서 호출한다. 
		}
	});
	
	FormPanel panel = new FormPanel();
	panel.setHeight(260);
	
	VerticalLayoutContainer vlc = new VerticalLayoutContainer();
	panel.add(vlc, new MarginData(30));
	
	VerticalLayoutData vld = new VerticalLayoutData(); 
	vlc.add(new HTML("<center><div><img src='img/Login.jpg' width='300' height='150'></center></div>"));
	panel.setLayoutData(new Margins(0, 0, 30, 30));
	loginDialog.add(panel); 
	loginDialog.show();

	FieldLabel firstNameLabel = new FieldLabel(firstName, "Login ID ");
	firstNameLabel.setLabelWidth(70);
	firstNameLabel.setWidth(280);
	vlc.add(firstNameLabel, vld);
	
	FieldLabel passwordLabel = new FieldLabel(password, "Password ");  
	passwordLabel.setLabelWidth(70); 
	passwordLabel.setWidth(280); 
	vlc.add(passwordLabel, vld);

	Label loginDesc = new HTML("<font size='2'><br>※ Login ID는 등록된 E-Mail ID를 사용 바랍니다. <br>※ 오류 발생시 담당자에게 문의 바랍니다.<br></font>");
	vlc.add(loginDesc);
}
*/
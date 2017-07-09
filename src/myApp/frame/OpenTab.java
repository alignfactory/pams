package myApp.frame;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel;
import com.sencha.gxt.widget.core.client.info.Info;

public class OpenTab {

	private Widget createTab(String className){

		if("Tab_Company".equals(className)) {
            return (Widget) GWT.create(myApp.client.sys.Tab_Company.class) ;
        }

		if("Tab_DailyAccount".equals(className)) {
			return (Widget) GWT.create(myApp.client.rpt.Tab_DailyAccount.class) ;
		}
		
		if("Tab_CashBook".equals(className)) {
			return (Widget) GWT.create(myApp.client.rpt.Tab_CashBook.class) ;
		}
		
		if("Tab_TrialBalance".equals(className)) {
			return (Widget) GWT.create(myApp.client.rpt.Tab_TrialBalance.class) ;
		}
		
		if("Tab_GeneralLedger".equals(className)) {
			return (Widget) GWT.create(myApp.client.rpt.Tab_GeneralLedger.class) ;
		}
		
		if("Tab_AdminTable".equals(className)) {
			return (Widget) GWT.create(myApp.client.sys.Tab_TabComments.class) ;
		}

		if("Tab_Menu".equals(className)) {
			return (Widget) GWT.create(myApp.client.sys.Tab_Menu.class) ;
		}
		
		if("Tab_RoleMenu".equals(className)) {
			return (Widget) GWT.create(myApp.client.sys.Tab_RoleMenu.class) ;
		}

		// 검사결과 등록 
		if("Tab_Checkup".equals(className)) {
			return (Widget) GWT.create(myApp.client.tmc.Tab_Checkup.class) ;
		}
		
		if("Tab_Admin".equals(className)) {
			return (Widget) GWT.create(myApp.client.sys.Tab_Admin.class) ;
		}

		if("Tab_Role".equals(className)) {
			return (Widget) GWT.create(myApp.client.sys.Tab_Role.class) ;
		}
		
		if("Tab_CodeKind".equals(className)) {
			return (Widget) GWT.create(myApp.client.code.Tab_CodeKind.class) ;
		}
		
		if("Tab_LicenseCode".equals(className)) {
			return (Widget) GWT.create(myApp.client.code.Tab_LicenseCode.class) ;
		}
		
		if("Tab_User".equals(className)) {
			return (Widget) GWT.create(myApp.client.sys.Tab_User.class) ;
		}

		if("Tab_Patient".equals(className)) {
			return (Widget) GWT.create(myApp.client.tmc.Tab_Patient.class) ;
		}

		if("Tab_Payday".equals(className)) {
			return (Widget) GWT.create(myApp.client.pay.Tab_Payday.class) ;
		}

		if("Tab_LicenseList".equals(className)) {
			return (Widget) GWT.create(myApp.client.emp.Tab_LicenseList.class) ;
		}

		if("Tab_StudyClass".equals(className)) {
			return (Widget) GWT.create(myApp.client.psc.Tab_StudyClass.class) ;
		}
		if("Tab_Teacher".equals(className)) {
			return (Widget) GWT.create(myApp.client.psc.Tab_Teacher.class) ;
		}
		if("Tab_Student".equals(className)) {
			return (Widget) GWT.create(myApp.client.psc.Tab_Student.class) ;
		}
		if("Tab_Board".equals(className)) {
			return (Widget) GWT.create(myApp.client.bbs.Tab_Board.class) ;
		}
		if("Tab_Season.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.acc.Tab_Season.class) ;
		}
		if("Tab_AccountCommon.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.acc.Tab_CommonAccount.class) ;
		}
		if("Tab_CompanyAccount.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.acc.Tab_CompanyAccount.class) ;
		}

		if("Tab_BankUpload".equals(className)) {
			return (Widget) GWT.create(myApp.client.acc.Tab_BankUpload.class) ;
		}

		if("Tab_Client.class".equals(className)) {
			return (Widget) GWT.create(myApp.client.acc.Tab_Client.class) ;
		}

		if("Tab_Request".equals(className)) {
			return (Widget) GWT.create(myApp.client.tmc.Tab_Request.class) ;
		}

		if("Tab_RequestList".equals(className)) {
			return (Widget) GWT.create(myApp.client.tmc.Tab_RequestList.class) ;
		}

		if("Tab_CheckupList".equals(className)) {
			return (Widget) GWT.create(myApp.client.tmc.Tab_CheckupList.class) ;
		}

		if("Tab_TreatList".equals(className)) {
			return (Widget) GWT.create(myApp.client.tmc.Tab_TreatList.class) ;
		}

		if("Tab_TreatRequest".equals(className)) {
			return (Widget) GWT.create(myApp.client.tmc.Tab_TreatRequest.class) ;
		}

		if("Tab_Prescribe".equals(className)) {
			return (Widget) GWT.create(myApp.client.tmc.Tab_Prescribe.class) ;
		}
		
		if("Tab_TreatResult".equals(className)) {
			return (Widget) GWT.create(myApp.client.tmc.Tab_TreatResult.class) ;
		}
		
		if("Tab_Stats".equals(className)) {
			return (Widget) GWT.create(myApp.client.tmc.Tab_Stats.class) ;
		}
		
		
		return null; 
	}
	
	public void openTab(TabPanel tabPanel, String className, String pageName){

		TabItemConfig tabItemConfig = new TabItemConfig(pageName, true);
		Widget tabPage = tabPanel.findItem(pageName, true); 

		if(tabPage != null) {
			tabPanel.setActiveWidget(tabPage);
			return ; 
		}

		// not found tab page  
		tabPage = createTab(className);
				
		if(tabPage != null){
			tabPanel.add(tabPage, tabItemConfig);
			tabPanel.setActiveWidget(tabPage);
		} 
		else {
			Info.display(pageName, "해당 오브젝트(" + className + ")가 시스템에 등록되어 있지 않습니다."); 
		}
	}
}

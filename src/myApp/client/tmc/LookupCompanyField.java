package myApp.client.tmc;

import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import myApp.client.sys.Lookup_Company;
import myApp.client.sys.model.CompanyModel;
import myApp.frame.LoginUser;
import myApp.frame.ui.InterfaceLookupResult;
import myApp.frame.ui.field.LookupTriggerField;

public class LookupCompanyField extends LookupTriggerField {
	
	private CompanyModel companyModel = new CompanyModel();
	
	public LookupCompanyField() {
		
		this.setEditable(false);
		
		this.addTriggerClickHandler(new TriggerClickHandler(){
   	 		@Override
			public void onTriggerClick(TriggerClickEvent event) {
   	 			
   	 			Lookup_Company lookupCompany = new Lookup_Company();
   	 			lookupCompany.setCallback(new InterfaceLookupResult(){
	   				@Override
	   				public void setLookupResult(Object result) {
	   					companyModel = (CompanyModel)result;
	   					setText(companyModel.getCompanyName());
	   				}
	   			});	
   	 			lookupCompany.show();
			}
   	 	}); 

		this.setCompanyModel(LoginUser.getLoginUser().getCompanyModel());
		this.setText(companyModel.getCompanyName());

	}

	public CompanyModel getCompanyModel(){
		return companyModel; 
	}
	
	public void setCompanyModel(CompanyModel companyModel){
		this.companyModel = companyModel; 
	}
	
}

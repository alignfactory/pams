package myApp.client.sys.model;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface CompanyUserModelProperties extends PropertyAccess<UserRoleModel>{

	ModelKeyProvider<CompanyUserModel> keyId();
	
	ValueProvider<CompanyUserModel, Long> companyUserId();
	ValueProvider<CompanyUserModel, Long> companyId();
	ValueProvider<CompanyUserModel, Long> userId();
	ValueProvider<CompanyUserModel, String> note();
		
	@Path("companyModel.companyName")
	ValueProvider<CompanyUserModel,String> companyName();

	@Path("companyModel.bizNo")
	ValueProvider<CompanyUserModel,String> bizNo();
	
	@Path("companyModel.telNo01")
	ValueProvider<CompanyUserModel,String> telNo01();
	

}

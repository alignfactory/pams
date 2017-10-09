package myApp.client.acc;

import java.util.HashMap;
import java.util.Map;

import myApp.client.acc.model.AccountModel;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

public class AccountComboBoxField extends StringComboBox implements InterfaceServiceCall {

	private Map<String, AccountModel> codeList = new HashMap<String, AccountModel>();
	
	public void setComboBoxField(Long companyId, String baseMonth, String inOutCode){
		
		for(String key : codeList.keySet()){
			this.remove(key);
		}
		
		ServiceRequest request = new ServiceRequest("acc.Account.selectByComboBox");
		request.add("companyId", companyId);
		
		String month = baseMonth.replace("-", ""); 
		request.add("baseMonth", month);
		request.add("inOutCode", inOutCode);
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		this.setTriggerAction(TriggerAction.ALL);
  	}  	

	public Long getCode(){
  		AccountModel code = codeList.get(this.getCurrentValue()); 
  		if(code != null){
  			return code.getAccountId(); 
  		}
  		else {
  			return null; 
  		}
  	}
  	
	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
			return ; 
		}
		for (AbstractDataModel model: result.getResult()) {
			AccountModel account = (AccountModel)model ;
			codeList.put(account.getAccountName(), account);
			this.add(account.getAccountName());
		}
	}
}



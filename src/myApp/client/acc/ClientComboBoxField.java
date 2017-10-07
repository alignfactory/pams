package myApp.client.acc;

import java.util.HashMap;
import java.util.Map;

import myApp.client.acc.model.ClientModel;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

public class ClientComboBoxField extends StringComboBox implements InterfaceServiceCall {

	private Map<String, ClientModel> codeList = new HashMap<String, ClientModel>();
	
	public void setComboBoxField(Long companyId){
		
		ServiceRequest request = new ServiceRequest("acc.Client.selectByCompanyId");
		request.add("companyId", companyId);
		
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		this.setTriggerAction(TriggerAction.ALL);
  	}  	

	public Long getCode(){
		ClientModel code = codeList.get(this.getCurrentValue()); 
  		if(code != null){
  			return code.getClientId(); 
  		}
  		else {
  			return null; 
  		}
  	}
	
	public ClientModel getClient(){
		ClientModel client = codeList.get(this.getCurrentValue()); 
  		if(client != null){
  			return client ; 
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
		
		Info.display("client count is", result.getResult().size() + "");
		
		for (AbstractDataModel model: result.getResult()) {
			ClientModel client = (ClientModel)model ;
			codeList.put(client.getClientName() + "(" + client.getBizNo() + ")", client);
			this.add(client.getClientName()+ "(" + client.getBizNo() + ")");
		}
	}
}



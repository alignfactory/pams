package myApp.frame.ui.builder;

import java.util.HashMap;
import java.util.Map;

import myApp.client.sys.model.CodeModel;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

public class ComboBoxField extends StringComboBox implements InterfaceServiceCall {

	private Map<String, CodeModel> codeList = new HashMap<String, CodeModel>();
	
	public ComboBoxField(String codeKind){
		ServiceRequest request = new ServiceRequest("sys.Code.selectByCodeKind");
		request.add("codeKind", codeKind);
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
		this.setTriggerAction(TriggerAction.ALL);
  	}  	

//	public void addAllString(String str){
//		this.add(str);
//	}
	
	public String getCode(){
  		CodeModel code = codeList.get(this.getCurrentValue()); 
  		if(code != null){
  			return code.getCode(); 
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
			CodeModel code = (CodeModel)model ;
			codeList.put(code.getName(), code);
			this.add(code.getName());
		}
	}
	
}



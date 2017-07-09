package myApp.client.acc.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface BankUploadModelProperties extends PropertyAccess<BankUploadModel> {
	
	ModelKeyProvider<BankUploadModel> keyId();
	
	ValueProvider<BankUploadModel, Long>  bankUploadId(); 
	ValueProvider<BankUploadModel, Long> companyId(); 
	ValueProvider<BankUploadModel, String> bankInOutCode(); 
	ValueProvider<BankUploadModel, String> bankInOutName(); 
	ValueProvider<BankUploadModel, Long> transNo(); 
	ValueProvider<BankUploadModel, Date> transDate(); 
	ValueProvider<BankUploadModel, String> transTime(); 
	ValueProvider<BankUploadModel, String> transName(); 
	ValueProvider<BankUploadModel, Long> transAmount();
	ValueProvider<BankUploadModel, Long> balance();
	ValueProvider<BankUploadModel, String> bigo();
	ValueProvider<BankUploadModel, String> memo();
	ValueProvider<BankUploadModel, String> note();
}

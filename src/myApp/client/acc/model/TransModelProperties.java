package myApp.client.acc.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface TransModelProperties extends PropertyAccess<TransModel> {
	
	ModelKeyProvider<TransModel> keyId();
	ValueProvider<TransModel, Long>  transId(); 
	ValueProvider<TransModel, Long> companyId();
	ValueProvider<TransModel, String> inExpCode();
	ValueProvider<TransModel, String> inExpName();
	ValueProvider<TransModel, Date> transDate();
	ValueProvider<TransModel, String> transName();
	ValueProvider<TransModel, Long> accountId(); // 계정정보 
	ValueProvider<TransModel, String> gmokCode();
	ValueProvider<TransModel, String> smokCode();
	ValueProvider<TransModel, String> accountName();
	ValueProvider<TransModel, Long> clientId(); // 거래처 정보 
	ValueProvider<TransModel, String> bizNo();
	ValueProvider<TransModel, String> clientName();
	ValueProvider<TransModel, Long> transAmount();
	ValueProvider<TransModel, String> bankCode();
	ValueProvider<TransModel, String> accountNo();
	ValueProvider<TransModel, String> accountOwner();
	ValueProvider<TransModel, Date> accountDate();
	ValueProvider<TransModel, String> taxApplyYn();
	ValueProvider<TransModel, Long> supplyAmount();
	ValueProvider<TransModel, Long> taxAmount();
	ValueProvider<TransModel, String> descript();
	ValueProvider<TransModel, Date> chargeDate();
	ValueProvider<TransModel, String> note();
	ValueProvider<TransModel, String> ceoName();
	ValueProvider<TransModel, String> gwanName();
	ValueProvider<TransModel, String> hangName();
	ValueProvider<TransModel, String> gmokName();
	ValueProvider<TransModel, String> zipCode();
	ValueProvider<TransModel, String> zipAddr();
	ValueProvider<TransModel, String> zipDetail();
	
}

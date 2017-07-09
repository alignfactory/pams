package myApp.client.rpt.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface DailyAccountModelProperties extends PropertyAccess<DailyAccountModel> {
	
	ModelKeyProvider<DailyAccountModel> keyId();
	
	ValueProvider<DailyAccountModel, Long>		rowNo();
	ValueProvider<DailyAccountModel, Long>		companyId();
	ValueProvider<DailyAccountModel, String>	yearMonth();
	ValueProvider<DailyAccountModel, Date>		transDate();
	ValueProvider<DailyAccountModel, String>	accountName();
	ValueProvider<DailyAccountModel, Long>		inAmount();
	ValueProvider<DailyAccountModel, Long>		outAmonut();
	ValueProvider<DailyAccountModel, Long>		sumAmount();
	ValueProvider<DailyAccountModel, Long>		ordNo();

}

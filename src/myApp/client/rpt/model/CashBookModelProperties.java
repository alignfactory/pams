package myApp.client.rpt.model;

import java.util.Date;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface CashBookModelProperties extends PropertyAccess<CashBookModel> {
	
	ModelKeyProvider<CashBookModel> keyId();
	
	ValueProvider<CashBookModel, Long>		rowNo();
	ValueProvider<CashBookModel, Long>		companyId();
	ValueProvider<CashBookModel, String>	yearMonth();
	ValueProvider<CashBookModel, Date>		transDate();
	ValueProvider<CashBookModel, String>	accountName();
	ValueProvider<CashBookModel, String>	transDescript();
	ValueProvider<CashBookModel, Long>		inAmount();
	ValueProvider<CashBookModel, Long>		outAmonut();
	ValueProvider<CashBookModel, Long>		sumAmount();

}

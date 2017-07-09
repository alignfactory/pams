package myApp.client.rpt.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface GeneralLedgerModelProperties extends PropertyAccess<GeneralLedgerModel> {
	
	ModelKeyProvider<GeneralLedgerModel> keyId();
	
	ValueProvider<GeneralLedgerModel, Long>		rowNo();
	ValueProvider<GeneralLedgerModel, String>	gwonCode();
	ValueProvider<GeneralLedgerModel, String>	gwonName();
	ValueProvider<GeneralLedgerModel, String>	hangCode();
	ValueProvider<GeneralLedgerModel, String>	hangName();
	ValueProvider<GeneralLedgerModel, String>	gmokCode();
	ValueProvider<GeneralLedgerModel, String>	gmokName();
	ValueProvider<GeneralLedgerModel, String>	yearMonth();
	ValueProvider<GeneralLedgerModel, Long>		transAmount();

}

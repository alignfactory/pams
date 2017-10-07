package myApp.client.acc.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface FilterModelProperties extends PropertyAccess<FilterModel> {
	
	ModelKeyProvider<FilterModel> keyId();

		ValueProvider<FilterModel, Long>		filterId();
		ValueProvider<FilterModel, Long>		companyId();
		ValueProvider<FilterModel, String>		replaceDscr();
		ValueProvider<FilterModel, String>		seqOrder();


}

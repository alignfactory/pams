package myApp.client.acc.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface FillerStringModelProperties extends PropertyAccess<FillerStringModel> {
	
	ModelKeyProvider<FillerStringModel> keyId();

		ValueProvider<FillerStringModel, Long>		fillerId();
		ValueProvider<FillerStringModel, Long>		companyId();
		ValueProvider<FillerStringModel, String>		replaceDscr();
		ValueProvider<FillerStringModel, String>		seqOrder();


}

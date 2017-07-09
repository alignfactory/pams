package myApp.client.sys.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface ColCommentsModelProperties extends PropertyAccess<ColCommentsModel> {
	
	ModelKeyProvider<ColCommentsModel> keyId();
	
	ValueProvider<ColCommentsModel, Long>	columnId();
	ValueProvider<ColCommentsModel, String>	columnName();
	ValueProvider<ColCommentsModel, String>	dataType();
	ValueProvider<ColCommentsModel, String>	columnComment();
	ValueProvider<ColCommentsModel, String>	nullAble();
	ValueProvider<ColCommentsModel, String>	columnLength();

}

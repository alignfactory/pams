package myApp.client.sys.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface MenuModelProperties extends PropertyAccess<MenuModel> {
	
	ModelKeyProvider<MenuModel> keyId();	
	ValueProvider<MenuModel, Long> 		menuId();
	ValueProvider<MenuModel, Long> 		parentId();
	ValueProvider<MenuModel, String> 	menuName();
	ValueProvider<MenuModel, String> 	className();
	ValueProvider<MenuModel, Boolean> 	hiddenYnFlag();
	ValueProvider<MenuModel, String> 	seq();
	ValueProvider<MenuModel, String> 	note();
}




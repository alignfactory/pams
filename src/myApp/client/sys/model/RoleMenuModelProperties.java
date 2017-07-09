package myApp.client.sys.model;

//import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface RoleMenuModelProperties extends PropertyAccess<MenuModel> {
	
	ModelKeyProvider<RoleMenuModel> keyId();	
	ValueProvider<RoleMenuModel, Long> 		menuId();
	ValueProvider<RoleMenuModel, Long> 		parentId();
	ValueProvider<RoleMenuModel, String> 	menuName();
	ValueProvider<RoleMenuModel, String> 	className();
	ValueProvider<RoleMenuModel, Boolean> 	hiddenYnFlag();
	ValueProvider<RoleMenuModel, String> 	seq();
	ValueProvider<RoleMenuModel, String> 	note();
	
	
	// sys07_role_menu 칼럼들....
	ValueProvider<RoleMenuModel, Long> 		roleMenuId();
	ValueProvider<RoleMenuModel, Long> 		roleId();
	ValueProvider<RoleMenuModel, Boolean> 	useYnFlag();
}




package myApp.frame;

import java.util.List;

import myApp.client.sys.model.RoleMenuModel;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.frame.ui.img.ResourceIcon;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.tree.Tree;

public class Main_TreeMenu implements InterfaceServiceCall{
	
	private Tree<RoleMenuModel, String> menuTree; // = this.getMenuTree();  
	private PlainTabPanel tabPanel;  
	
	public Main_TreeMenu(PlainTabPanel tabPanel){
		this.tabPanel = tabPanel; 
		this.menuTree = this.buildMenuTree(); 
		this.retrieveByUserId();
	} 

	public Tree<RoleMenuModel, String> getMenuTree(){
		return this.menuTree; 
	}
	
	private Tree<RoleMenuModel, String> buildMenuTree(){
		
		TreeStore<RoleMenuModel> menuTreeStore = new TreeStore<RoleMenuModel>(new ModelKeyProvider<RoleMenuModel> () {
			@Override
			public String getKey(RoleMenuModel roleObject) {
				return roleObject.getMenuId() + "";
			}
		});
		
		ValueProvider<RoleMenuModel, String> treeMenuValueProvider = new ValueProvider<RoleMenuModel, String>() {
			@Override
			public String getValue(RoleMenuModel object) {
				return object.getMenuName();
			}
			@Override
			public void setValue(RoleMenuModel object, String value) {
			}
			@Override
			public String getPath() {
				return "path";
			}
		} ; 

		menuTree = new Tree<RoleMenuModel, String>(menuTreeStore, treeMenuValueProvider) {
			@Override
			protected void onClick(Event event) { // onDoubleClick event도 있으나...
				TreeNode<RoleMenuModel> node = findNode(event.getEventTarget().<Element> cast());

				if(node == null) {
					return; // 선택된 메뉴가 없다. 
				}
		        
				if(node.getModel().getMenuId() != null && node.getModel().getChildList().size() == 0 ){
					String className = node.getModel().getClassName();  
					String pageName = node.getModel().getMenuName();
					
					OpenTab openTab = new OpenTab();
					openTab.openTab(tabPanel, className, pageName);
				}
				
		        super.onDoubleClick(event); // tree node를 one-click으로 열기위해 사용한다. 
			}
		};

		menuTree.getStyle().setLeafIcon(ResourceIcon.INSTANCE.textButton());
		return menuTree; 
	}
	
	
	public void retrieveByUserId(){
		ServiceRequest request = new ServiceRequest("sys.RoleMenu.selectByUserId");
		request.add("userId", LoginUser.getLoginUser().getUserId());
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void addChild(RoleMenuModel parentMenu) {
		if(parentMenu.getChildList() != null){
			List<AbstractDataModel> childList = parentMenu.getChildList(); 
			for(AbstractDataModel child : childList){
				RoleMenuModel childObject = (RoleMenuModel)child;
				menuTree.getStore().add(parentMenu, childObject);
				this.addChild(childObject);
			}
		}
	}
	
	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0){
			Info.display("메뉴조회 오류", result.getMessage());
		}
		else { 
			menuTree.getStore().clear(); // 깨끗이 비운다. 
			
			for (AbstractDataModel model: result.getResult()) {
				// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
				RoleMenuModel roleMenuModel = (RoleMenuModel)model;   
				menuTree.getStore().add(roleMenuModel);
				this.addChild(roleMenuModel); // child menu & object setting  
			}
		} 
	}
}

package myApp.client.sys;

import myApp.client.sys.model.RoleMenuModel;
import myApp.client.sys.model.RoleMenuModelProperties;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.service.TreeGridUpdate;
import myApp.frame.ui.AbstractDataModel;
import myApp.frame.ui.builder.GridBuilder;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

public class Page_RoleMenu extends VerticalLayoutContainer implements InterfaceServiceCall {
	
	private TreeGrid<RoleMenuModel> treeGrid = this.buildRoleMenu();
	private Long roleId;

	public Page_RoleMenu(){

		ButtonBar buttonBar = new ButtonBar();

		TextButton refreshButton = new TextButton("조회");
		refreshButton.setWidth(60);
		refreshButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				 refresh(); 
			}
		}); 
		buttonBar.add(refreshButton);
		
		TextButton expandAll = new TextButton("펼치기"); 
		expandAll.setWidth(60);
		expandAll.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				 treeGrid.expandAll();
			}
		}); 
		buttonBar.add(expandAll);
		
		TextButton collapseAll = new TextButton("감추기");
		collapseAll.setWidth(60);
		collapseAll.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				 treeGrid.collapseAll();
			}
		}); 
		buttonBar.add(collapseAll);
		
		TextButton updateButton = new TextButton("저장");
		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update();  
			}
		}); 
		buttonBar.add(updateButton);
		
		this.add(buttonBar, new VerticalLayoutData(1, 40));
		this.add(this.treeGrid, new VerticalLayoutData(1, 1));
	}

	public TreeGrid<RoleMenuModel> buildRoleMenu(){
		
		RoleMenuModelProperties properties = GWT.create(RoleMenuModelProperties.class);
		final GridBuilder<RoleMenuModel> gridBuilder = new GridBuilder<RoleMenuModel>(properties.keyId());  
		gridBuilder.addText(properties.menuName(), 300, "메뉴명"); ;
		gridBuilder.addBoolean(properties.useYnFlag(), 50, "권한") ;
		gridBuilder.addText(properties.note(), 600, "상세설명"); 
		return gridBuilder.getTreeGrid(1);  
	}

	public void refresh(){
		if(this.roleId != null){
			retrieve(this.roleId);
		}
	}
	
	public void retrieve(Long roleId){
		this.roleId = roleId;
//		Page_RoleMenuRetrieve retrieve = new Page_RoleMenuRetrieve(treeGrid.getTreeStore());
//		retrieve.retrieve(roleId);
		
		// 서버에서 전체 트리를 한번에 가져온다.  
		ServiceRequest request = new ServiceRequest("sys.RoleMenu.selectByCheckedRoot");
		request.add("roleId", roleId);
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void addChild(RoleMenuModel parentMenu) {
		if(parentMenu.getChildList() != null){
			List<AbstractDataModel> childList = parentMenu.getChildList(); 
			for(AbstractDataModel child : childList){
				RoleMenuModel childObject = (RoleMenuModel)child;
				this.treeGrid.getTreeStore().add(parentMenu, childObject);
				this.addChild(childObject);
			}
		}
	}
	
	private void update(){
		TreeGridUpdate<RoleMenuModel> service = new TreeGridUpdate<RoleMenuModel>();
		service.addParam("roleId", this.roleId);
		service.update(treeGrid.getTreeStore(), "sys.RoleMenu.update"); 
	}

	@Override
	public void getServiceResult(ServiceResult result) {
		if(result.getStatus() < 0){
			Info.display("error", result.getMessage());
		}
		else { 
			this.treeGrid.getTreeStore().clear(); // 깨끗이 비운다. 
			
			for (AbstractDataModel model: result.getResult()) {
				// 서버에서 전체 트리를 한번에 가져온 후 트리를 구성한다.  
				RoleMenuModel roleObject = (RoleMenuModel)model;   
				this.treeGrid.getTreeStore().add(roleObject);
				this.addChild(roleObject); // child menu & object setting  
			}
		} 

		
	}
}

package myApp.client.sys;

import java.util.List;

import myApp.client.sys.model.MenuModel;
import myApp.client.sys.model.MenuModelProperties;
import myApp.frame.service.InterfaceServiceCall;
import myApp.frame.service.ServiceCall;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.service.TreeGridDeleteRow;
import myApp.frame.service.TreeGridInsertRow;
import myApp.frame.service.TreeGridUpdate;
import myApp.frame.ui.AbstractDataModel;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.GridBuilder;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.treegrid.TreeGrid;

public class Tab_Menu extends VerticalLayoutContainer implements InterfaceServiceCall {
	
	private TreeGrid<MenuModel> treeGrid = this.buildTreeGrid();
	
	public Tab_Menu(){

		ButtonBar buttonBar = new ButtonBar();

		TextButton retrieveButton = new TextButton("메뉴조회"); 
		retrieveButton.setWidth(70);
		retrieveButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				retrieve(); 
			}
		}); 
		buttonBar.add(retrieveButton);
		
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

		TextButton createRoot = new TextButton("루트등록"); 
		createRoot.setWidth(70);
		createRoot.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				addRootMenu(); 
			}
		}); 
		buttonBar.add(createRoot);

		TextButton addSubMenu = new TextButton("하위등록");
		addSubMenu.setWidth(70);
		addSubMenu.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				addSubMenu(); 
			}
		}); 
		buttonBar.add(addSubMenu);

		TextButton updateButton = new TextButton("저장");
		updateButton.setWidth(60);
		updateButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				update();  
			}
		}); 
		buttonBar.add(updateButton);

		TextButton deleteButton = new TextButton("삭제");  
		deleteButton.setWidth(60);
		deleteButton.addSelectHandler(new SelectHandler(){
			@Override
			public void onSelect(SelectEvent event) {
				deleteRow();  
			}
		}); 
		buttonBar.add(deleteButton);  
		
		this.add(buttonBar, new VerticalLayoutData(1, 40));
		this.add(this.treeGrid, new VerticalLayoutData(1, 1));
	}
	
	public TreeGrid<MenuModel> buildTreeGrid(){
		
		MenuModelProperties properties = GWT.create(MenuModelProperties.class);
		final GridBuilder<MenuModel> gridBuilder = new GridBuilder<MenuModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.menuName(), 300, "메뉴명", new TextField());
		gridBuilder.addText(properties.seq(), 50, "순서", new TextField()) ;
		gridBuilder.addText(properties.className(), 200, "클래스명", new TextField()) ;
		
		gridBuilder.addBoolean(properties.hiddenYnFlag(), 50, "숨김") ;
		gridBuilder.addText(properties.note(), 300, "상세설명", new TextField());
	
		return gridBuilder.getTreeGrid(2);  
	}

	public void retrieve(){
		// 서버에서 전체 트리를 한번에 가져온다.  
		ServiceRequest request = new ServiceRequest("sys.Menu.selectByAll");
		ServiceCall service = new ServiceCall();
		service.execute(request, this);
	}

	private void addChild(MenuModel parentMenu) {
		if(parentMenu.getChildList() != null){
			List<AbstractDataModel> childList = parentMenu.getChildList(); 
			for(AbstractDataModel child : childList){
				MenuModel childObject = (MenuModel)child;
				this.treeGrid.getTreeStore().add(parentMenu, childObject);
				this.addChild(childObject);
			}
		}
	}
	
	private void addRootMenu(){
	
		MenuModel roleObject = new MenuModel(); 
		roleObject.setParentId(Long.parseLong("0"));
		TreeGridInsertRow<MenuModel> treeGridInsert = new TreeGridInsertRow<MenuModel>(); 
		treeGridInsert.addRoot(treeGrid.getTreeStore(), roleObject);  
	}

	private void addSubMenu(){
		MenuModel parentModel = treeGrid.getSelectionModel().getSelectedItem(); // 선택된 Menu를 가져온다.
		
		if(parentModel == null){
			new SimpleMessage("선택된 상위 메뉴가 없습니다. "); 
			return ; 
		}
		
		if(parentModel.getClassName() != null){
			// objectID가 있으면 하위메뉴를 등록할 수 없다. 
			new SimpleMessage("오브젝트에는 하위 메뉴를 등록할 수 없습니다."); 
			return ; 
		}

		treeGrid.setExpanded(parentModel, true);
		MenuModel subRoleObject = new MenuModel();
		subRoleObject.setParentId(parentModel.getMenuId());
		
		TreeGridInsertRow<MenuModel> service = new TreeGridInsertRow<MenuModel>(); 
		service.addItem(treeGrid.getTreeStore(), parentModel, subRoleObject);  
	}
	
	private void update(){
		TreeGridUpdate<MenuModel> service = new TreeGridUpdate<MenuModel>(); 
		service.update(treeGrid.getTreeStore(), "sys.Menu.update"); 
	}
	
	private void deleteRow(){
		TreeGridDeleteRow<MenuModel> service = new TreeGridDeleteRow<MenuModel>();
		List<MenuModel> checkedList = treeGrid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(treeGrid.getTreeStore(), checkedList, "sys.Menu.delete");
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
				MenuModel roleObject = (MenuModel)model;   
				this.treeGrid.getTreeStore().add(roleObject);
				this.addChild(roleObject); // child menu & object setting  
			}
		} 
		
	}
}

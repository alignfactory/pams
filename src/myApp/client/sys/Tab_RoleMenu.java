package myApp.client.sys;

import myApp.client.sys.model.RoleModel;
import myApp.client.sys.model.RoleModelProperties;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

public class Tab_RoleMenu extends BorderLayoutContainer implements InterfaceGridOperate {
	// Role별 사용할 수 있는 메뉴를 선택한다. 
	// 좌측의 Role을 선택하면 우측에 트리메뉴가 조회된다. 
	// 해당 Role권한으로 사용할 수 있는 메뉴를 세팅하면 된다. 
	
	private RoleModelProperties properties = GWT.create(RoleModelProperties.class);
	private Grid<RoleModel> grid = this.buildGrid();
	private TextField roleName = new TextField();
	private Page_RoleMenu roleMenu = new Page_RoleMenu();
	
	public Tab_RoleMenu() {
		
		this.setBorders(false);
		
		// 조회조건 
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(roleName, "권한명", 150, 50, true); 
		searchBarBuilder.addRetrieveButton(); 
		
		// Role 조회 
		VerticalLayoutContainer roleContainer  = new VerticalLayoutContainer(); 
		
		roleContainer.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		roleContainer.add(this.grid, new VerticalLayoutData(1, 1));
		
		BorderLayoutData westLayoutData = new BorderLayoutData(0.3);
		westLayoutData.setMargins(new Margins(0, 2, 0, 0)); 
		westLayoutData.setSplit(true);
		westLayoutData.setMaxSize(1000); // TODO: BorderLayoutContainer set max size
		
		this.setWestWidget(roleContainer, westLayoutData);

		this.grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<RoleModel>(){
			@Override
			public void onSelectionChanged(SelectionChangedEvent<RoleModel> event) {
				RoleModel role = grid.getSelectionModel().getSelectedItem();   
				roleMenu.retrieve(role.getRoleId());
			} 
		}); 
		
		// 트리메뉴
		this.setCenterWidget(roleMenu);
	}
	
	public Grid<RoleModel> buildGrid(){
		
		GridBuilder<RoleModel> gridBuilder = new GridBuilder<RoleModel>(properties.keyId());  
		// gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.roleName(), 150, "권한명"); //, new TextField());
		gridBuilder.addText(properties.seq(), 50, "순서"); //, new TextField()) ;
		//gridBuilder.addBoolean(properties.managerYnBoolean(), 70, "ADMIN") ;
		gridBuilder.addText(properties.note(), 500, "비고"); //, new TextField());
	
		return gridBuilder.getGrid(); 
	}
	
	@Override
	public void retrieve() {
		GridRetrieveData<RoleModel> service = new GridRetrieveData<RoleModel>(grid.getStore()); 
		service.retrieveAll("sys.Role.selectByAll");
	}

	@Override
	public void update() {
	}

	@Override
	public void insertRow() {
	}

	@Override
	public void deleteRow() {
	}
}
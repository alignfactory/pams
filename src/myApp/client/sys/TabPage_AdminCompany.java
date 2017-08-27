package myApp.client.sys;

import java.util.List;
import java.util.Map;

import myApp.client.sys.model.CompanyModel;
import myApp.client.sys.model.CompanyUserModel;
import myApp.client.sys.model.CompanyUserModelProperties;
import myApp.client.sys.model.UserModel;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.service.GridUpdateData;
import myApp.frame.service.InterfaceCallback;
import myApp.frame.ui.InterfaceLookupResult;
import myApp.frame.ui.InterfaceTabPage;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class TabPage_AdminCompany extends ContentPanel implements InterfaceTabPage, InterfaceGridOperate, InterfaceLookupResult {
	
	private CompanyUserModelProperties properties = GWT.create(CompanyUserModelProperties.class);
	private Grid<CompanyUserModel> grid = this.buildGrid();
	private Long userId = null;
	
	private TabPage_AdminCompany getThis(){
		return this; 
	}
	
	public TabPage_AdminCompany() {
		
		this.setHeaderVisible(false); 
		this.add(this.grid);
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addDeleteButton();

		this.getButtonBar().add(searchBarBuilder.getSearchBar()); 
		this.setButtonAlign(BoxLayoutPack.CENTER);
	}
	
	public Grid<CompanyUserModel> buildGrid(){
		
		GridBuilder<CompanyUserModel> gridBuilder = new GridBuilder<CompanyUserModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

		gridBuilder.addText(properties.companyName(), 250, "고객명") ;
		gridBuilder.addText(properties.bizNo(), 100, "사업자번호") ;
		gridBuilder.addText(properties.telNo01(), 100, "대표전화번호") ;
		gridBuilder.addText(properties.note(), 400, "비고", new TextField());

		return gridBuilder.getGrid(); 
	}
	
	@Override
	public void retrieve(Map<String, Object> param) {
		this.grid.getStore().clear();
		
		if(param != null){
			UserModel userModel = (UserModel)param.get("UserModel"); 
			this.userId = userModel.getUserId(); 
			this.retrieve();
		}
		else {
			this.userId = null; 
		}
	}

	@Override
	public void retrieve() {
		if(this.userId != null){
			GridRetrieveData<CompanyUserModel> service = new GridRetrieveData<CompanyUserModel>(grid.getStore());
			service.addParam("userId", this.userId);
			service.retrieve("sys.CompanyUser.selectByUserId");
		}
	}
	

	@Override
	public void update() {
		GridUpdateData<CompanyUserModel> service = new GridUpdateData<CompanyUserModel>(); 
		service.update(grid.getStore(), "sys.CompanyUser.update"); 
	}

	@Override
	public void insertRow() {
		if(userId != null){
			new Lookup_AdminCompany(this.getThis(), this.userId).show();
		}
	}

	@Override
	public void deleteRow() {
		GridDeleteData<CompanyUserModel> service = new GridDeleteData<CompanyUserModel>();
		List<CompanyUserModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(grid.getStore(), checkedList, "sys.CompanyUser.delete");
	}

	@Override
	public void setLookupResult(Object result) {
		
		@SuppressWarnings("unchecked")
		List<CompanyModel> companyList = (List<CompanyModel>)result;  
		
		for(final CompanyModel companyModel:companyList){

			final CompanyUserModel userCompanyModel = new CompanyUserModel();
			userCompanyModel.setCompanyId(companyModel.getCompanyId());
			userCompanyModel.setUserId(this.userId);
			
			GridInsertRow<CompanyUserModel> service = new GridInsertRow<CompanyUserModel>();
			
			service.addCallback(new InterfaceCallback(){
				@Override
				public void callback() {
					String companyName = companyModel.getCompanyName() ;  
					String bizNo = companyModel.getBizNo() ; 
					String telNo = companyModel.getTelNo01() ; 
					
					grid.getStore().getRecord(userCompanyModel).addChange(properties.companyName(), companyName);
					grid.getStore().getRecord(userCompanyModel).addChange(properties.bizNo(), bizNo);
					grid.getStore().getRecord(userCompanyModel).addChange(properties.telNo01(), telNo);
				}
				
			});
			service.insertRow(grid, userCompanyModel);
		}
	}
}
package myApp.client.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import myApp.client.sys.model.CompanyModel;
import myApp.client.sys.model.CompanyModelProperties;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.service.GridUpdateData;
import myApp.frame.ui.builder.ComboBoxField;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class Tab_Company extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private CompanyModelProperties properties = GWT.create(CompanyModelProperties.class);
	private Grid<CompanyModel> grid = this.buildGrid();
	private TextField companyName = new TextField(); 
	private ComboBoxField companyTypeName = new ComboBoxField("CompanyTypeCode");
	
	public Tab_Company() {
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addComboBox(companyTypeName, "기관구분", 200, 60); 

		companyTypeName.add("전체");
		companyTypeName.setText("전체");
		
		searchBarBuilder.addTextField(companyName, "기관명", 150, 50, true); 

		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();

		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(grid, new VerticalLayoutData(1, 1));
		
	}
	
	private Grid<CompanyModel> buildGrid(){

		GridBuilder<CompanyModel> gridBuilder = new GridBuilder<CompanyModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		final ComboBoxField companyTypeCodeComboBox = new ComboBoxField("CompanyTypeCode");  
		companyTypeCodeComboBox.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				CompanyModel data = grid.getSelectionModel().getSelectedItem(); 
				grid.getStore().getRecord(data).addChange(properties.companyTypeCode(), companyTypeCodeComboBox.getCode());
			}
		}); 
		
		gridBuilder.addText(properties.companyName(), 150, "고객명", new TextField());
		gridBuilder.addText(properties.companyTypeName(), 80, "기관구분", companyTypeCodeComboBox);
		gridBuilder.addText(properties.bizNo(), 120, "사업자번호", new TextField());
		gridBuilder.addText(properties.telNo01(), 100, "전화1", new TextField());
		gridBuilder.addText(properties.telNo02(), 100, "전화2", new TextField());
		gridBuilder.addText(properties.faxNo01(), 100, "팩스번호", new TextField());
		gridBuilder.addText(properties.zipCode(), 100, "우편번호", new TextField());
		gridBuilder.addText(properties.zipAddress(), 300, "주소", new TextField());;
		gridBuilder.addText(properties.note(), 400, "비고", new TextField());;
	
		return gridBuilder.getGrid(); 
	}
	
	public void retrieveTabPage(){

		CompanyModel companyModel = this.grid.getSelectionModel().getSelectedItem(); 
		
		if(companyModel != null){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("companyModel", companyModel); 
		}
	}

	@Override
	public void retrieve() {
		
		String companyTypeCode = companyTypeName.getCode();
		if(companyTypeCode == null){
			companyTypeCode = "%"; 
		}
		String name = companyName.getText();
		
		
		GridRetrieveData<CompanyModel> service = new GridRetrieveData<CompanyModel>(grid.getStore()); 
		service.addParam("companyName", name);
		service.addParam("companyTypeCode", companyTypeCode);
		service.retrieve("sys.Company.selectByName");
	}

	@Override
	public void update(){
		GridUpdateData<CompanyModel> service = new GridUpdateData<CompanyModel>(); 
		service.update(grid.getStore(), "sys.Company.update"); 
	}
	
	@Override
	public void insertRow(){
		GridInsertRow<CompanyModel> service = new GridInsertRow<CompanyModel>(); 
		CompanyModel companyModel = new CompanyModel();
		service.insertRow(grid, companyModel);
	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<CompanyModel> service = new GridDeleteData<CompanyModel>();
		List<CompanyModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(grid.getStore(), checkedList, "sys.Company.delete");
	}
}
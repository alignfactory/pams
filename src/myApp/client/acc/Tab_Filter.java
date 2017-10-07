package myApp.client.acc;

import java.util.List;

import myApp.client.acc.model.FilterModel;
import myApp.client.acc.model.FilterModelProperties;
import myApp.client.acc.model.MemoCodeModel;
import myApp.client.sys.Lookup_Company;
import myApp.client.sys.model.CompanyModel;
import myApp.frame.LoginUser;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.service.GridUpdateData;
import myApp.frame.ui.InterfaceLookupResult;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;
import myApp.frame.ui.field.LookupTriggerField;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class Tab_Filter extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private FilterModelProperties properties = GWT.create(FilterModelProperties.class);
	private Grid<FilterModel> grid = this.buildGrid();
//	private TextField className = new TextField();
	private CompanyModel companyModel = LoginUser.getLoginCompany();
	private LookupTriggerField lookupCompanyField = this.getLookupCompanyField();
	
	public Tab_Filter() {
		
		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addLookupTriggerField(lookupCompanyField, "유치원", 250, 50);

//		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
//		searchBarBuilder.addTextField(className, "유치원명", 250, 150, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();
		
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(grid, new VerticalLayoutData(1, 1));
	}
	
	private LookupTriggerField getLookupCompanyField(){
		
		final Lookup_Company lookupCompany = new Lookup_Company();
		lookupCompany.setCallback(new InterfaceLookupResult(){
			@Override
			public void setLookupResult(Object result) {
				companyModel = (CompanyModel)result;// userCompanyModel.getCompanyModel(); 
				lookupCompanyField.setValue(companyModel.getCompanyName());
			}
		});
		
		LookupTriggerField lookupCompanyField = new LookupTriggerField(); 
		lookupCompanyField.setEditable(false);
		lookupCompanyField.setText(this.companyModel.getCompanyName());
		lookupCompanyField.addTriggerClickHandler(new TriggerClickHandler(){
   	 		@Override
			public void onTriggerClick(TriggerClickEvent event) {
   	 			lookupCompany.show();
			}
   	 	}); 
		return lookupCompanyField; 
	}
	
	@Override
	public void retrieve(){
		GridRetrieveData<FilterModel> service = new GridRetrieveData<FilterModel>(grid.getStore());
	//	service.addParam("companyId", LoginUser.getLoginUser().getCompanyModel().getCompanyId());
		
		service.addParam("companyId", LoginUser.getLoginCompany().getCompanyId());
//		System.out.println("Login CompanyID : "+LoginUser.getLoginUser().getCompanyId());
//		System.out.println("Login CompanyID : "+"******");

//		Info.display("companyID","" + LoginUser.getLoginUser().getCompanyId());
		
		System.out.println("Tab_Filter Strart 1 : " + companyModel ); 

		Long companyId = this.companyModel.getCompanyId();
		if(companyId  == null){
			new SimpleMessage("조회할 유치원이 먼저 선택하여야 합니다.");
			return ; 
		}
		
		service.retrieve("acc.Filter.selectByCompanyId");
	}
	
	@Override
	public void update(){
		GridUpdateData<FilterModel> service = new GridUpdateData<FilterModel>(); 
		service.update(grid.getStore(), "acc.Filter.update"); 
	}
	
	@Override
	public void insertRow(){
		GridInsertRow<FilterModel> service = new GridInsertRow<FilterModel>(); 
		FilterModel FillerStringModel = new FilterModel();
		FillerStringModel.setCompanyId(LoginUser.getLoginCompany().getCompanyId());	//	LoginUser.getLoginUser().getCompanyId());
		service.insertRow(grid, FillerStringModel);
	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<FilterModel> service = new GridDeleteData<FilterModel>();
		List<FilterModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(grid.getStore(), checkedList, "acc.Filter.delete");
	}
	
	public Grid<FilterModel> buildGrid(){
			
		GridBuilder<FilterModel> gridBuilder = new GridBuilder<FilterModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
//		final ComboBoxField eduOfficeComboBox = new ComboBoxField("EduOfficeCode");  
//		eduOfficeComboBox.addCollapseHandler(new CollapseHandler(){
//			@Override
//			public void onCollapse(CollapseEvent event) {
//				FillerStringModel data = grid.getSelectionModel().getSelectedItem(); 
//				grid.getStore().getRecord(data).addChange(properties.eduOfficeCode(), eduOfficeComboBox.getCode());
//			}
//		}); 
//		gridBuilder.addText(properties.eduOfficeName(), 150, "교육청구분", eduOfficeComboBox) ;
//		<result	column="acc09_filler_id"		property="fillerId"/>
//		<result	column="acc09_company_id"		property="companyId"/>
//		<result	column="acc09_replace_dscr"		property="replaceDscr"/>
//		<result	column="acc09_seq_order"		property="seqOrder"/>
		
		gridBuilder.addText(properties.replaceDscr(), 400, "비고", new TextField());
		gridBuilder.addText(properties.seqOrder(), 100, "순서", new TextField()) ;

		return gridBuilder.getGrid(); 
	}

}
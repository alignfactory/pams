package myApp.client.acc;

import java.util.List;

import myApp.client.acc.model.FillerStringModel;
import myApp.client.acc.model.FillerStringModelProperties;
import myApp.client.sys.Lookup_Company;
import myApp.client.sys.model.CompanyModel;
import myApp.frame.LoginUser;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.service.GridUpdateData;
import myApp.frame.ui.InterfaceLookupResult;
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

public class Tab_FillerString extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private FillerStringModelProperties properties = GWT.create(FillerStringModelProperties.class);
	private Grid<FillerStringModel> grid = this.buildGrid();
//	private TextField className = new TextField();
	private CompanyModel companyModel = LoginUser.getLoginCompany();
	private LookupTriggerField lookupCompanyField = this.getLookupCompanyField();
	
	public Tab_FillerString() {
		
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
		GridRetrieveData<FillerStringModel> service = new GridRetrieveData<FillerStringModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getLoginUser().getCompanyModel().getCompanyId());

//		System.out.println("Login CompanyID : "+LoginUser.getLoginUser().getCompanyId());
//		System.out.println("Login CompanyID : "+"******");

//		Info.display("companyID","" + LoginUser.getLoginUser().getCompanyId());
		
		service.retrieve("acc.FillerString.selectByCompanyId");
	}
	
	@Override
	public void update(){
		GridUpdateData<FillerStringModel> service = new GridUpdateData<FillerStringModel>(); 
		service.update(grid.getStore(), "acc.FillerString.update"); 
	}
	
	@Override
	public void insertRow(){
		GridInsertRow<FillerStringModel> service = new GridInsertRow<FillerStringModel>(); 
		FillerStringModel FillerStringModel = new FillerStringModel();
		FillerStringModel.setCompanyId(LoginUser.getLoginUser().getCompanyId());
		service.insertRow(grid, FillerStringModel);
	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<FillerStringModel> service = new GridDeleteData<FillerStringModel>();
		List<FillerStringModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(grid.getStore(), checkedList, "acc.FillerString.delete");
	}
	
	public Grid<FillerStringModel> buildGrid(){
			
		GridBuilder<FillerStringModel> gridBuilder = new GridBuilder<FillerStringModel>(properties.keyId());  
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
package myApp.client.acc;

import java.util.List;

import myApp.client.acc.model.MemoCodeModel;
import myApp.client.acc.model.MemoCodeModelProperties;
import myApp.client.rpt.model.CashBookModel;
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

public class Tab_Memo extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private MemoCodeModelProperties properties = GWT.create(MemoCodeModelProperties.class);
	private Grid<MemoCodeModel> grid = this.buildGrid();
//	private TextField className = new TextField();
	private CompanyModel companyModel = LoginUser.getLoginCompany();
	private LookupTriggerField lookupCompanyField = this.getLookupCompanyField();
	
	public Tab_Memo() {
		
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
		GridRetrieveData<MemoCodeModel> service = new GridRetrieveData<MemoCodeModel>(grid.getStore());
	//	service.addParam("companyId", LoginUser.getLoginUser().getCompanyModel().getCompanyId());
		
		service.addParam("companyId", LoginUser.getLoginCompany().getCompanyId());
		
//		System.out.println("Login CompanyID : "+LoginUser.getLoginUser().getCompanyId());
//		System.out.println("Login CompanyID : "+"******");

//		Info.display("companyID","" + LoginUser.getLoginUser().getCompanyId());
		
		System.out.println("Tab_Memo Strart 1 : " + companyModel ); 

		Long companyId = this.companyModel.getCompanyId();
		if(companyId  == null){
			new SimpleMessage("조회할 유치원이 먼저 선택하여야 합니다.");
			return ; 
		}
		
		service.retrieve("acc.Memo.selectByCompanyId");
	}
	
	@Override
	public void update(){
		GridUpdateData<MemoCodeModel> service = new GridUpdateData<MemoCodeModel>(); 
		service.update(grid.getStore(), "acc.Memo.update"); 
	}
	
	@Override
	public void insertRow(){
		GridInsertRow<MemoCodeModel> service = new GridInsertRow<MemoCodeModel>(); 
		MemoCodeModel MemoCodeModel = new MemoCodeModel();
		MemoCodeModel.setCompanyId(LoginUser.getLoginCompany().getCompanyId());	//	LoginUser.getLoginUser().getCompanyId());
		service.insertRow(grid, MemoCodeModel);
	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<MemoCodeModel> service = new GridDeleteData<MemoCodeModel>();
		List<MemoCodeModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(grid.getStore(), checkedList, "acc.Memo.delete");
	}
	
	public Grid<MemoCodeModel> buildGrid(){
			
		GridBuilder<MemoCodeModel> gridBuilder = new GridBuilder<MemoCodeModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
//		final ComboBoxField eduOfficeComboBox = new ComboBoxField("EduOfficeCode");  
//		eduOfficeComboBox.addCollapseHandler(new CollapseHandler(){
//			@Override
//			public void onCollapse(CollapseEvent event) {
//				MemoCodeModel data = grid.getSelectionModel().getSelectedItem(); 
//				grid.getStore().getRecord(data).addChange(properties.eduOfficeCode(), eduOfficeComboBox.getCode());
//			}
//		}); 
//		<result	column="acc05_memo_id"		property="memoId"/>
//		<result	column="acc05_company_id"		property="companyId"/>
//		<result	column="acc05_memo_cd"		property="memoCode"/>
//		<result	column="acc05_acct_cd"		property="acctCode"/>
//		<result	column="acc05_sub_cd"		property="subCode"/>
//		<result	column="acc05_memo_dscr"		property="memoDscr"/>
//		gridBuilder.addText(properties.eduOfficeName(), 150, "교육청구분", eduOfficeComboBox) ;
		
		gridBuilder.addText(properties.memoCode(), 300, "메모명", new TextField()) ;
		gridBuilder.addText(properties.acctCode(), 100, "계정코드", new TextField()) ;
		gridBuilder.addText(properties.subCode(), 100, "세목코드", new TextField()) ;
		gridBuilder.addText(properties.acctName(), 300, "계정명" );
		gridBuilder.addText(properties.memoDscr(), 500, "비고", new TextField());

		return gridBuilder.getGrid(); 
	}

}
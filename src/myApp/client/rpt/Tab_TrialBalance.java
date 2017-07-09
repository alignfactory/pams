package myApp.client.rpt;

import myApp.client.rpt.model.TrialBalanceModel;
import myApp.client.rpt.model.TrialBalanceModelProperties;
import myApp.client.sys.Lookup_Company;
import myApp.client.sys.model.CompanyModel;
import myApp.frame.LoginUser;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.ui.InterfaceLookupResult;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;
import myApp.frame.ui.field.LookupTriggerField;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class Tab_TrialBalance extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private TrialBalanceModelProperties properties = GWT.create(TrialBalanceModelProperties.class);
	private Grid<TrialBalanceModel> grid = this.buildGrid();
	
	private CompanyModel companyModel = LoginUser.getLoginCompany();
	private LookupTriggerField lookupCompanyField = this.getLookupCompanyField();
	private TextField yearMonth = new TextField(); 
	private DateField beginDate = new DateField(); 
	private DateField endDate = new DateField(); 
	
	public Tab_TrialBalance() {
		
		this.setBorders(false); 

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addLookupTriggerField(lookupCompanyField, "유치원", 250, 50);
		searchBarBuilder.addTextField(yearMonth, "해당월", 150, 80, true); 
		searchBarBuilder.addDateField(beginDate, "From Date", 200, 80, true); 
		searchBarBuilder.addDateField(endDate, "To Date", 200, 80, true); 
		
		searchBarBuilder.addRetrieveButton(); 
//		searchBarBuilder.addUpdateButton();
//		searchBarBuilder.addDeleteButton();

		//	초기값 변경
		Date today = new Date();
		DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM");
		yearMonth.setValue(fmt.format(today));
		yearMonth.setValue("2015-03");

		Date bDate =  DateTimeFormat.getFormat("yyyy-MM-dd").parse("2015-03-01");
	    beginDate.setValue(bDate);
		Date eDate =  DateTimeFormat.getFormat("yyyy-MM-dd").parse("2015-03-31");
		endDate.setValue(eDate);

		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(grid, new VerticalLayoutData(1, 1));
	}
	
	private LookupTriggerField getLookupCompanyField(){
		
		final Lookup_Company lookupCompany = new Lookup_Company();
		lookupCompany.setCallback(new InterfaceLookupResult(){
			@Override
			public void setLookupResult(Object result) {
				companyModel = (CompanyModel)result;
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
		
		System.out.println("Tab_TrialBalance Strart 1 : " + companyModel ); 

		Long companyId = this.companyModel.getCompanyId();
		if(companyId  == null){
			new SimpleMessage("조회할 유치원이 먼저 선택하여야 합니다.");
			return ; 
		}
		
		GridRetrieveData<TrialBalanceModel> service = new GridRetrieveData<TrialBalanceModel>(grid.getStore());
		service.addParam("companyId", companyId);
		service.addParam("yearMonth", yearMonth.getValue());
		service.addParam("beginDate", beginDate.getValue());
		service.addParam("endDate", endDate.getValue());
		service.retrieve("rpt.TrialBalance.selectByCompanyId");
	}
	
	@Override
	public void update(){
		//
	}
	
	@Override
	public void insertRow(){
		//
	}
	
	@Override
	public void deleteRow(){
		//
	}
	
	public Grid<TrialBalanceModel> buildGrid(){
			
		GridBuilder<TrialBalanceModel> gridBuilder = new GridBuilder<TrialBalanceModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
//		gridBuilder.addLong(properties.RowNo()				,	120	,	"잔액"			,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 	
		gridBuilder.addLong(properties.inBalanceSum()		,	120	,	"잔액"			,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.inAccumulatedSum()	,	120	,	"누계"			,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.inAmounts()			,	120	,	"월계"			,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.inBudgetAmount()		,	120	,	"예산"			,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
//		gridBuilder.addText(properties.boldGb()				,	120	,	"진하게"		,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
//		gridBuilder.addText(properties.accountCd()			,	120	,	"과목코드"		,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
//		gridBuilder.addText(properties.subCd()				,	120	,	"세목코드"		,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addText(properties.accountPrtNm()		,	120	,	"계정과목"		,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.outBudgetAmount()	,	120	,	"예산"			,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.outAmonut()			,	120	,	"월계"			,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.outAccumulatedSum()	,	120	,	"누계"			,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.outAalanceSum()		,	120	,	"잔액"			,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 

		return gridBuilder.getGrid();  
	}
}
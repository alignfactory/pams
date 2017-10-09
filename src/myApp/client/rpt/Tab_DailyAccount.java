package myApp.client.rpt;

import myApp.client.rpt.model.DailyAccountModel;
import myApp.client.rpt.model.DailyAccountModelProperties;
import myApp.client.sys.Lookup_Company;
import myApp.client.sys.model.CompanyModel;
import myApp.frame.LoginUser;
import myApp.frame.PDFViewer;
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
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

public class Tab_DailyAccount extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private DailyAccountModelProperties properties = GWT.create(DailyAccountModelProperties.class);
	private Grid<DailyAccountModel> grid = this.buildGrid();
	
	private CompanyModel companyModel = LoginUser.getLoginCompany();
	private LookupTriggerField lookupCompanyField = this.getLookupCompanyField();
	private DateField beginDate = new DateField(); 
	private DateField endDate = new DateField(); 
	
	public Tab_DailyAccount() {
		
		this.setBorders(false); 

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addLookupTriggerField(lookupCompanyField, "유치원 ", 250, 50);
		searchBarBuilder.addDateField(beginDate, "시작일자 ", 165, 60, true); 
		searchBarBuilder.addDateField(endDate, "종료일자 ", 165, 60, true); 
		
		searchBarBuilder.addRetrieveButton(); 
//		searchBarBuilder.addUpdateButton();
//		searchBarBuilder.addDeleteButton();

		//	초기값 변경
	    Date bDate =  DateTimeFormat.getFormat("yyyy-MM-dd").parse("2015-03-01");
	    beginDate.setValue(bDate);
		Date eDate =  DateTimeFormat.getFormat("yyyy-MM-dd").parse("2015-03-05");
		endDate.setValue(eDate);

	    TextButton retrievePDFButton = new TextButton("PDF출력");
	    retrievePDFButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				PDFViewer viewer = new PDFViewer(); 
				// 호출하려면 className과 기타 Parameter를 String으로 붙여서 넘겨주어야 한다. 
				String requestString = "className=rpt.DailyAccountPDF"; 
				requestString = requestString + "&companyId=" + companyModel.getCompanyId(); 
				requestString = requestString + "&beginDate=" + beginDate.getText(); 
				requestString = requestString + "&endDate=" + endDate.getText();
				
				Info.display("param", requestString); 
				
				viewer.open(requestString);
				
			}
		});
	    searchBarBuilder.getSearchBar().add(retrievePDFButton); 

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
		
		System.out.println("Tab_DailyAccount Strart 1 : " + companyModel ); 

		Long companyId = this.companyModel.getCompanyId();
		if(companyId  == null){
			new SimpleMessage("조회할 유치원이 먼저 선택하여야 합니다.");
			return ; 
		}
		
		GridRetrieveData<DailyAccountModel> service = new GridRetrieveData<DailyAccountModel>(grid.getStore());
		service.addParam("companyId", companyId);
		service.addParam("beginDate", beginDate.getValue());
		service.addParam("endDate", endDate.getValue());
		service.retrieve("rpt.DailyAccount.selectByCompanyIdProc");
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
	
	public Grid<DailyAccountModel> buildGrid(){
			
		GridBuilder<DailyAccountModel> gridBuilder = new GridBuilder<DailyAccountModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
//		gridBuilder.addLong(properties.companyId()		,	100	,	"회사ID"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new TextField());
//		gridBuilder.addText(properties.yearMonth()		,	80	,	"해당월"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	); // not editable 
		gridBuilder.addDate(properties.transDate()		,	100	,	"일자"			,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new DateField()); 
		gridBuilder.addText(properties.accountName()	,	300	,	"계정과목"		,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.inAmount()		,	120	,	"입금액"		,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.outAmonut()		,	120	,	"출금액"		,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.sumAmount()		,	120	,	"잔액"			,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
//		gridBuilder.addLong(properties.ordNo()			,	80	,	"순서"			,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new TextField()); 

		return gridBuilder.getGrid();  
	}
}
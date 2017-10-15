package myApp.client.acc;

import myApp.client.acc.model.TransModel;
import myApp.client.acc.model.TransModelProperties;
import myApp.client.sys.Lookup_Company;
import myApp.client.sys.model.CompanyModel;
import myApp.frame.LoginUser;
import myApp.frame.PDFViewer;
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

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

public class Tab_In_Expense extends VerticalLayoutContainer implements InterfaceGridOperate {
	
//	private static AccountComboBoxField accountComboBox = new AccountComboBoxField();
	private TransModelProperties properties = GWT.create(TransModelProperties.class);
	private Grid<TransModel> grid = this.buildGrid();
	
	private CompanyModel companyModel = LoginUser.getLoginCompany();
	private LookupTriggerField lookupCompanyField = this.getLookupCompanyField();
	private TextField baseMonth= new TextField();
	
	public Tab_In_Expense() {
		
		this.setBorders(false); 

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addLookupTriggerField(lookupCompanyField, "유치원", 250, 50);
		searchBarBuilder.addTextField(baseMonth, "기준월", 130, 50, true);

		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addDeleteButton();

		Date today = new Date();
		DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM");
		baseMonth.setValue(fmt.format(today));
//		baseMonth.addChangeHandler(new ChangeHandler(){
//			@Override
//			public void onChange(ChangeEvent arg0) {
//				// 해당월이 바뀌면 계정콤보박스도 변경해야 한다. 
//				Info.display("ch", baseMonth.getText());
//				// TODO Auto-generated method stub
//				accountComboBox.reset();
//				accountComboBox.setComboBoxField(LoginUser.getLoginCompany().getCompanyId(), baseMonth.getText(), "OUT");
//			}
//		});

	    TextButton retrievePDFButton = new TextButton("지출결의서 출력");
	    retrievePDFButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				PDFViewer viewer = new PDFViewer(); 
				// 호출하려면 className과 기타 Parameter를 String으로 붙여서 넘겨주어야 한다. 
				viewer.open("className=acc.ExpensePDF&companyId=" + LoginUser.getLoginCompany().getCompanyId()
						+ "&baseMonth=" + baseMonth.getText() + "&companyName=" + LoginUser.getLoginCompany().getCompanyName());
				
			}
		});
	    searchBarBuilder.getSearchBar().add(retrievePDFButton); 
		
//	    TextButton retrievePDFButton = new TextButton("PDF출력");
//	    retrievePDFButton.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//				PDFViewer viewer = new PDFViewer(); 
//				// 호출하려면 className과 기타 Parameter를 String으로 붙여서 넘겨주어야 한다. 
//				String requestString = "className=rpt.DailyAccountPDF"; 
//				requestString = requestString + "&companyId=" + companyModel.getCompanyId(); 
////				requestString = requestString + "&startDate=" + startDate.getText(); 
////				requestString = requestString + "&endDate=" + endDate.getText();
//				
//				Info.display("param", requestString); 
//				
//				viewer.open(requestString);
//				
//			}
//		});
//	    searchBarBuilder.getSearchBar().add(retrievePDFButton); 

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
		
		GridRetrieveData<TransModel> service = new GridRetrieveData<TransModel>(grid.getStore());
//		service.addParam("companyId", companyId);
		service.addParam("companyId", LoginUser.getLoginCompany().getCompanyId());
		service.addParam("baseMonth", baseMonth.getText());
		service.addParam("inOutCode", "OUT");
		service.retrieve("acc.Trans.selectByTransDate");
	}
	
	@Override
	public void update(){
		GridUpdateData<TransModel> service = new GridUpdateData<TransModel>(); 
		service.update(grid.getStore(), "acc.Trans.update"); 
	}
	
	@Override
	public void insertRow(){
		GridInsertRow<TransModel> service = new GridInsertRow<TransModel>(); 
		TransModel FillerStringModel = new TransModel();
		FillerStringModel.setCompanyId(LoginUser.getLoginCompany().getCompanyId());	//	LoginUser.getLoginUser().getCompanyId());
		service.insertRow(grid, FillerStringModel);
	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<TransModel> service = new GridDeleteData<TransModel>();
		List<TransModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(grid.getStore(), checkedList, "acc.Trans.delete");
	}
		public Grid<TransModel> buildGrid(){
			
		GridBuilder<TransModel> gridBuilder = new GridBuilder<TransModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

//		gridBuilder.addLong(properties.companyId()		,	100	,	"회사ID"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new TextField());
//		gridBuilder.addText(properties.yearMonth()		,	80	,	"해당월"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	); // not editable 
		gridBuilder.addDate(properties.transDate()		,	90	,	"일자"			,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new DateField()); 
		gridBuilder.addText(properties.transName()		,	200	,	"거래내역"		,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new TextField()); 
//		gridBuilder.addLong(properties.accountId()		,	80	,	"계정"			,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new TextField()); 
		gridBuilder.addText(properties.accountName()	,	200	,	"계정명"		,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.transAmount()	,	100	,	"출금액"		,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
//		gridBuilder.addLong(properties.clientId()		,	100	,	"거래처코드"	,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new TextField()); 
		gridBuilder.addText(properties.bizNo()			,	100	,	"법인번호"		,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new TextField()); 
		gridBuilder.addText(properties.clientName()		,	200	,	"거래처명"		,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new TextField()); 
		gridBuilder.addText(properties.taxApplyYn()		,	40	,	"여부"			,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.supplyAmount()	,	100	,	"공급가"		,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addLong(properties.taxAmount()		,	100	,	"부가세"		,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new TextField()); 
		gridBuilder.addDate(properties.accountDate()	,	90	,	"회계일자"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new DateField()); 
		gridBuilder.addText(properties.descript()		,	300	,	"적요"			,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new TextField()); 
		gridBuilder.addText(properties.note()			,	200	,	"비고"			,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new TextField()); 

		return gridBuilder.getGrid();  
	}
}
package myApp.client.rpt;

import myApp.client.rpt.model.DailyAccountModel;
import myApp.client.rpt.model.DailyAccountModelProperties;
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

import java.text.ParseException;
<<<<<<< Upstream, based on branch 'master' of https://github.com/alignfactory/pams.git
import java.text.SimpleDateFormat;
import java.util.Date;
=======
import java.util.Date;  
>>>>>>> b1226b6 

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class Tab_DailyAccount extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private DailyAccountModelProperties properties = GWT.create(DailyAccountModelProperties.class);
	private Grid<DailyAccountModel> grid = this.buildGrid();
	
	private CompanyModel companyModel = LoginUser.getLoginCompany();
	private LookupTriggerField lookupCompanyField = this.getLookupCompanyField();
	private DateField fromDate = new DateField(); 
	private DateField toDate = new DateField(); 
	
	public Tab_DailyAccount() throws ParseException {
		
		this.setBorders(false); 

		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addLookupTriggerField(lookupCompanyField, "유치원", 250, 50);
		searchBarBuilder.addDateField(fromDate, "From Date", 200, 80, true);
		searchBarBuilder.addDateField(toDate, "To Date", 200, 80, true);
		searchBarBuilder.addRetrieveButton();
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addDeleteButton();
		
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(grid, new VerticalLayoutData(1, 1));

// 일자 초기값 설정 
//		Date setFromDate = new Date(); 
//		CalendarUtil.addMonthsToDate(setFromDate, -2);
		
		
		 //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate =  DateTimeFormat.getFormat("yyyy-MM-dd").parse("2015-01-01");
		fromDate.setValue(beginDate);
		toDate.setValue(new Date());
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
		
		Long companyId = this.companyModel.getCompanyId();
		if(companyId  == null){
			new SimpleMessage("조회할 유치원이 먼저 선택하여야 합니다.");
			return ; 
		}
		GridRetrieveData<DailyAccountModel> service = new GridRetrieveData<DailyAccountModel>(grid.getStore());
		service.addParam("companyId", companyId);
		service.addParam("fromDate", fromDate.getValue());
		service.addParam("toDate", toDate.getValue());
		service.retrieve("rpt.DailyAccount.selectByCompanyId");
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
		
		gridBuilder.addLong(properties.companyId(), 60, "회사ID", new TextField());
		gridBuilder.addText(properties.yearMonth(), 60, "해당월"); // not editable 
//		gridBuilder.addDate(properties.transDate(), 100, "일자", new TextField()); 
		gridBuilder.addText(properties.accountName(), 300, "계정과목", new TextField()); 
		gridBuilder.addLong(properties.inAmount(), 100, "입금액", new TextField()); 
		gridBuilder.addLong(properties.outAmonut(), 100, "출금액", new TextField()); 
		gridBuilder.addLong(properties.sumAmount(), 100, "잔액", new TextField()); 
//		gridBuilder.addLong(properties.ordNo(), 80, "순서", new TextField()); 

		return gridBuilder.getGrid();  
	}
}
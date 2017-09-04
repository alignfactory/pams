package myApp.client.acc;

import java.util.Date;
import java.util.List;

import myApp.client.acc.model.TransModel;
import myApp.client.acc.model.TransModelProperties;
import myApp.frame.LoginUser;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.service.GridUpdateData;
import myApp.frame.ui.builder.ComboBoxField;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent.SubmitCompleteHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.FormPanel.Encoding;
import com.sencha.gxt.widget.core.client.form.FormPanel.Method;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

public class Tab_PaymentSlip extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private TransModelProperties properties = GWT.create(TransModelProperties.class);
	private Grid<TransModel> grid = this.buildGrid();
	private TextField baseMonth= new TextField();
	
	public Tab_PaymentSlip() {
		
		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(baseMonth, "기준월", 130, 50, true);
		
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();
		
		Date today = new Date();
		DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM");
		baseMonth.setValue(fmt.format(today));
		
		TextButton importButton = new TextButton("불러오기");
		importButton.setWidth(100);
//		uploadButton.addSelectHandler(new SelectHandler() {
//			@Override
//			public void onSelect(SelectEvent event) {
//			}
//			}
//		});
		searchBarBuilder.getSearchBar().add(importButton);
		
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(grid, new VerticalLayoutData(1, 1));
	}
	
	@Override
	public void retrieve(){
		GridRetrieveData<TransModel> service = new GridRetrieveData<TransModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getLoginCompany().getCompanyId());
		service.addParam("baseMonth", baseMonth.getText());
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
		TransModel transModel = new TransModel();
		transModel.setCompanyId(LoginUser.getLoginCompany().getCompanyId());
		transModel.setInExpCode("OUT"); // 무조건 출금으로 설정된다. 
		transModel.setInExpName("출금");
		service.insertRow(grid, transModel);
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
		
		gridBuilder.addText(properties.inExpName(), 60, "구분"); 
		gridBuilder.addDate(properties.transDate(), 80, "거래일자", new DateField()) ;
		gridBuilder.addText(properties.transName(), 120, "거래명", new TextField()) ;
		
		gridBuilder.addText(properties.gmokCode(), 100, "목코드") ;
		gridBuilder.addText(properties.smokCode(),	80, "세목코드") ;
		
		gridBuilder.addText(properties.accountName(), 150, "계정명", new TextField()) ;
		
		gridBuilder.addLong(properties.transAmount(), 120, "거래금액", new LongField()) ;
		gridBuilder.addText(properties.descript(), 500, "적요", new TextField());
		
		gridBuilder.addText(properties.bizNo(), 100, "거래처번호"); 
		gridBuilder.addText(properties.clientName(), 150, "거래처명", new TextField()) ;
		gridBuilder.addLong(properties.supplyAmount(), 100, "공급가액", new LongField()) ;
		gridBuilder.addText(properties.taxApplyYn(), 60, "부가세", new TextField()) ;
		gridBuilder.addLong(properties.taxAmount(), 100, "부가세액", new LongField()) ;
		gridBuilder.addDate(properties.chargeDate(), 80, "청구일자", new DateField()) ;

		gridBuilder.addDate(properties.accountDate(), 80, "회계일자", new TextField()) ;
		

		gridBuilder.addText(properties.note(), 200, "비고", new TextField());
		
		//gridBuilder.addText(properties.note(), 400, "비고", new TextField());

		return gridBuilder.getGrid(); 
	}

}
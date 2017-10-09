package myApp.client.acc;

import java.util.Date;
import java.util.List;

import myApp.client.acc.model.ClientModel;
import myApp.client.acc.model.TransModel;
import myApp.client.acc.model.TransModelProperties;
import myApp.frame.LoginUser;
import myApp.frame.service.CallBatch;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.service.GridUpdateData;
import myApp.frame.service.InterfaceCallback;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

/*
 * 입금전표 처리 : inOutCode = IN 
 */

public class Tab_ReceiptSlip extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private static AccountComboBoxField accountComboBox = new AccountComboBoxField();
	private TextField baseMonth= new TextField();
	private TransModelProperties properties = GWT.create(TransModelProperties.class);
	private Grid<TransModel> grid = this.buildGrid();

	public Tab_ReceiptSlip() {
		
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
		baseMonth.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent arg0) {
				// 해당월이 바뀌면 계정콤보박스도 변경해야 한다. 
				//Info.display("ch", baseMonth.getText());
				accountComboBox.reset();
				accountComboBox.setComboBoxField(LoginUser.getLoginCompany().getCompanyId(), baseMonth.getText(), "IN");
			}
		});
		
		TextButton importButton = new TextButton("입금 불러오기");
		importButton.setWidth(100);
		importButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				CallBatch service = new CallBatch(); 
				service.addCallback(new InterfaceCallback(){

					@Override
					public void callback() {
						Info.display("불러오기 완료", "입금내역을 재조회합니다.");
						retrieve(); 
					}
				});
				service.addParam("companyId", LoginUser.getLoginCompany().getCompanyId());
				service.addParam("baseMonth", baseMonth.getText());
				service.addParam("inOutCode", "IN");
				
				service.execute("acc.Trans.loadTrans");
			}
		});
		searchBarBuilder.getSearchBar().add(importButton);
		
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(grid, new VerticalLayoutData(1, 1));
	}
	
	@Override
	public void retrieve(){
		GridRetrieveData<TransModel> service = new GridRetrieveData<TransModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getLoginCompany().getCompanyId());
		service.addParam("baseMonth", baseMonth.getText());
		service.addParam("inOutCode", "IN");
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
		transModel.setInExpCode("IN"); // 무조건 출금으로 설정된다. 
		transModel.setInExpName("입금");
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
		gridBuilder.addDate(properties.transDate(), 100, "입금일자", new DateField()) ;
		gridBuilder.addText(properties.transName(), 150, "거래명", new TextField()) ;
		
		accountComboBox.setComboBoxField(LoginUser.getLoginCompany().getCompanyId(), baseMonth.getText(), "IN"); 
		
		accountComboBox.addValueChangeHandler(new ValueChangeHandler<String>(){
			@Override
			public void onValueChange(ValueChangeEvent<String> arg0) {
				TransModel data = grid.getSelectionModel().getSelectedItem(); 
				Long accountId = accountComboBox.getCode();
				if(accountId == null){
					new SimpleMessage("해당 계정코드를 찾을 수 없습니다.");
					//grid.getStore().getRecord(data).addChange(properties.accountName(), "");
				}
				grid.getStore().getRecord(data).addChange(properties.accountId(), accountId);
			}
		});
		
		
//		accountComboBox.addCollapseHandler(new CollapseHandler(){
//			@Override
//			public void onCollapse(CollapseEvent event) {
//				TransModel data = grid.getSelectionModel().getSelectedItem(); 
//				Long accountId = accountComboBox.getCode();
////				if(accountId == null){
////					new SimpleMessage("해당 계정코드를 찾을 수 없습니다.");
////					grid.getStore().getRecord(data).addChange(properties.accountName(), "");
////				}
//				grid.getStore().getRecord(data).addChange(properties.accountId(), accountId);
//			}
//		}); 

		gridBuilder.addText(properties.accountName(), 200, "계정명", accountComboBox) ;
		//gridBuilder.addLong(properties.accountId(), 150, "accountId") ;
		
		gridBuilder.addLong(properties.transAmount(), 120, "입금금액", new LongField()) ;
		gridBuilder.addText(properties.descript(), 300, "적요", new TextField());
//		gridBuilder.addText(properties.bizNo(), 100, "거래처번호"); 
//		
//		final ClientComboBoxField clientComboBox = new ClientComboBoxField();
//		clientComboBox.setComboBoxField(LoginUser.getLoginCompany().getCompanyId()); 
//		clientComboBox.addValueChangeHandler(new ValueChangeHandler<String>(){
//			@Override
//			public void onValueChange(ValueChangeEvent<String> arg0) {
//				TransModel data = grid.getSelectionModel().getSelectedItem(); 
//				ClientModel client = clientComboBox.getClient();
//				if(client == null){
//					new SimpleMessage("해당 거래처 코드를 찾을 수 없습니다.");
//					//grid.getStore().getRecord(data).addChange(properties.accountName(), "");
//				}
//				grid.getStore().getRecord(data).addChange(properties.clientId(), client.getClientId());
//				grid.getStore().getRecord(data).addChange(properties.bizNo(), client.getBizNo());
//			}
//		});
//		gridBuilder.addText(properties.clientName(), 150, "거래처명", clientComboBox) ;
//		gridBuilder.addLong(properties.supplyAmount(), 100, "공급가액", new LongField()) ;
//		gridBuilder.addText(properties.taxApplyYn(), 60, "부가세", new TextField()) ;
//		gridBuilder.addLong(properties.taxAmount(), 100, "부가세액", new LongField()) ;
//		gridBuilder.addDate(properties.chargeDate(), 100, "청구일자", new DateField()) ;
//		gridBuilder.addDate(properties.accountDate(), 100, "회계일자", new TextField()) ;
		gridBuilder.addText(properties.note(), 200, "비고", new TextField());
		return gridBuilder.getGrid(); 
	}

}
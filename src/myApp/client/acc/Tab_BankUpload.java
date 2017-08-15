package myApp.client.acc;

import java.util.Date;
import java.util.List;

import myApp.client.acc.model.BankUploadModel;
import myApp.client.acc.model.BankUploadModelProperties;
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

public class Tab_BankUpload extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private BankUploadModelProperties properties = GWT.create(BankUploadModelProperties.class);
	private Grid<BankUploadModel> grid = this.buildGrid();
	private TextField baseMonth= new TextField();
	FormPanel fileUploadForm = new FormPanel(); // file upload form.
	ComboBoxField bankInOutCode = new ComboBoxField("BankInOutCode");
	
	public Tab_BankUpload() {
		
		bankInOutCode.add("전체");
		bankInOutCode.setText("전체");
		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(baseMonth, "기준월", 130, 50, true);
		searchBarBuilder.addComboBox(bankInOutCode, "입출구분", 150, 60);
		
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();
		
		Date today = new Date();
		DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM");
		baseMonth.setValue(fmt.format(today));
		
		final FileUploadField fileUploadFiled = new FileUploadField();
		fileUploadFiled.setWidth(300);
		
		fileUploadForm.add(new FieldLabel(fileUploadFiled, "파일"));
		fileUploadForm.setEncoding(Encoding.MULTIPART);
		fileUploadForm.setMethod(Method.POST);
		fileUploadForm.addSubmitCompleteHandler(new SubmitCompleteHandler(){
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				
				
				Info.display("upload", event.getResults().toString()); 
				
				if(event.getResults().indexOf("OK") < 0) { 
					Info.display("upload", event.toDebugString()); //event.getResults());
				}
				else {
					Info.display("upload", "정상적으로 업로드되었습니다."); 
				}
			}
		});
		fileUploadForm.setLabelWidth(40);
		searchBarBuilder.getSearchBar().add(fileUploadForm);
		
		TextButton uploadButton = new TextButton("업로드");
		uploadButton.setWidth(80);
		uploadButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if (!fileUploadForm.isValid()) {
					Info.display("form is invalid", ""); 
					return;
				}
				
				if("".equals(fileUploadFiled.getValue().trim())) { 
					Info.display("파일확인", "먼저 업로드할 파일을 선택하여 주세요.");
				}
				else {
					String actionUrl 
							= "FileUpload?uploadType=bankExcel" 
							+ "&companyId=" + LoginUser.getLoginCompany().getCompanyId() 
							+ "&baseMonth=" + baseMonth.getText();
					
//					Info.display("url", actionUrl);
					
					fileUploadForm.setAction(actionUrl); // File upload servelt call - web.xml 참조
					fileUploadForm.submit();
					//fileUploadFiled.reset();
				}
			}
		});
		searchBarBuilder.getSearchBar().add(uploadButton);
		
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(grid, new VerticalLayoutData(1, 1));
	}
	
	@Override
	public void retrieve(){
		GridRetrieveData<BankUploadModel> service = new GridRetrieveData<BankUploadModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getLoginCompany().getCompanyId());
		service.addParam("baseMonth", baseMonth.getText());
		service.addParam("bankInOutCode", bankInOutCode.getCode());
		service.retrieve("acc.BankUpload.selectByBaseMonth");
	}
	
	@Override
	public void update(){
		GridUpdateData<BankUploadModel> service = new GridUpdateData<BankUploadModel>(); 
		service.update(grid.getStore(), "acc.BankUpload.update"); 
	}
	
	@Override
	public void insertRow(){
		GridInsertRow<BankUploadModel> service = new GridInsertRow<BankUploadModel>(); 
		BankUploadModel bankUploadModel = new BankUploadModel();
		bankUploadModel.setCompanyId(LoginUser.getLoginCompany().getCompanyId());
		bankUploadModel.setBankInOutCode("IN");
		bankUploadModel.setBankInOutName("입급");
		service.insertRow(grid, bankUploadModel);
	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<BankUploadModel> service = new GridDeleteData<BankUploadModel>();
		List<BankUploadModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(grid.getStore(), checkedList, "acc.BankUpload.delete");
	}
	
	public Grid<BankUploadModel> buildGrid(){
			
		GridBuilder<BankUploadModel> gridBuilder = new GridBuilder<BankUploadModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		final ComboBoxField bankInOutComboBox  = new ComboBoxField("BankInOutCode");  
		bankInOutComboBox.setEditable(false);
		
		bankInOutComboBox.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				BankUploadModel data = grid.getSelectionModel().getSelectedItem(); 
				grid.getStore().getRecord(data).addChange(properties.bankInOutCode(), bankInOutComboBox.getCode());
			}
		}); 
		
		gridBuilder.addLong(properties.transNo(), 60, "번호", new LongField()) ;
		gridBuilder.addDate(properties.transDate(), 100, "거래일", new DateField()) ;
		gridBuilder.addText(properties.transTime(), 80, "거래시간", new TextField()) ;
		gridBuilder.addText(properties.bankInOutName(), 100, "입출구분", bankInOutComboBox) ;
		gridBuilder.addText(properties.transName(), 100, "거래종류", new TextField()) ;
		gridBuilder.addLong(properties.transAmount(), 100, "거래금액", new LongField()) ;
		gridBuilder.addLong(properties.balance(), 120, "잔액", new LongField()) ;
		gridBuilder.addText(properties.bigo(), 200, "비고", new TextField());
		gridBuilder.addText(properties.memo(), 500, "메모(적요사항)", new TextField());
		//gridBuilder.addText(properties.note(), 400, "비고", new TextField());

		return gridBuilder.getGrid(); 
	}

}
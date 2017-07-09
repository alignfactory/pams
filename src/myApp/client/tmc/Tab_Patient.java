package myApp.client.tmc;

import java.util.List;

import myApp.client.sys.Lookup_Company;
import myApp.client.sys.model.CompanyModel;
import myApp.client.tmc.model.PatientModel;
import myApp.client.tmc.model.PatientModelProperties;
import myApp.frame.LoginUser;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.service.GridUpdateData;
import myApp.frame.ui.InterfaceLookupResult;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.ComboBoxField;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;
import myApp.frame.ui.field.LookupTriggerField;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class Tab_Patient extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private PatientModelProperties properties = GWT.create(PatientModelProperties.class);
	private Grid<PatientModel> grid = this.buildGrid();
	
	private CompanyModel companyModel = LoginUser.getLoginUser().getCompanyModel(); 
	private Lookup_Company lookupCompany = new Lookup_Company();
	private LookupTriggerField lookupCompanyField = new LookupTriggerField(); 
	private TextField patientNameField = new TextField();
	
	public Tab_Patient() {

		this.lookupCompanyField.setEditable(false);
		this.lookupCompanyField.setText(this.companyModel.getCompanyName());
		this.lookupCompanyField.addTriggerClickHandler(new TriggerClickHandler(){
   	 		@Override
			public void onTriggerClick(TriggerClickEvent event) {
   	 			lookupCompany.show();
			}
   	 	}); 

		this.lookupCompany.setCallback(new InterfaceLookupResult(){
			@Override
			public void setLookupResult(Object result) {
				companyModel = (CompanyModel)result;// userCompanyModel.getCompanyModel(); 
				lookupCompanyField.setValue(companyModel.getCompanyName());
			}
		});
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addLookupTriggerField(this.lookupCompanyField, "기관명", 250, 48); 
		searchBarBuilder.addTextField(patientNameField, "환자명", 170, 48, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();

		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 48));
		this.add(grid, new VerticalLayoutData(1, 1));
	}
	
	public Grid<PatientModel> buildGrid(){

		GridBuilder<PatientModel> gridBuilder = new GridBuilder<PatientModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addText(properties.insNo(), 80, "보험번호", new TextField()) ;
		gridBuilder.addText(properties.korName(), 80, "환자명", new TextField()) ;
		gridBuilder.addDate(properties.birthday(), 85, "생년월일", new DateField()) ;
		gridBuilder.addDate(properties.firstDate(), 90, "최초등록일", new DateField()) ;
		
		final ComboBoxField genderComboBox = new ComboBoxField("GenderCode");  
		genderComboBox.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				PatientModel data = grid.getSelectionModel().getSelectedItem(); 
				grid.getStore().getRecord(data).addChange(properties.genderCode(), genderComboBox.getCode());
			}
		}); 
		gridBuilder.addText(properties.genderName(), 50, "성별", genderComboBox) ;
		gridBuilder.addText(properties.tel1(), 110, "전화번호1", new TextField()) ;
		gridBuilder.addText(properties.tel2(), 110, "전화번호2", new TextField()) ;
		gridBuilder.addText(properties.viewPoint(), 120, "병력", new TextField()) ;
		gridBuilder.addText(properties.zipCode(), 75, "우편No", new TextField()) ;
		gridBuilder.addText(properties.address(), 300, "주소", new TextField()) ;
		gridBuilder.addText(properties.guardianName(), 80, "보호자명", new TextField()) ;
		gridBuilder.addText(properties.guardianRelationName(), 60, "관계", new TextField()) ;
		gridBuilder.addText(properties.guardianTel1(), 110, "보호자전화1", new TextField()) ;
		gridBuilder.addText(properties.guardianTel2(), 110, "보호자번호2", new TextField()) ;
		gridBuilder.addText(properties.note(), 400, "비고", new TextField()) ;
		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve() {
		
		if(this.companyModel.getCompanyId() == null){
			new SimpleMessage("기관명 확인", "조회조건의 기관명은 반드시 입력하세요. ");
			return ; 
		} 
		
		GridRetrieveData<PatientModel> service = new GridRetrieveData<PatientModel>(grid.getStore());
		service.addParam("companyId", this.companyModel.getCompanyId());
		service.addParam("patientName", patientNameField.getValue());
		service.retrieve("tmc.Patient.selectByName");
	}
	
	@Override
	public void update(){
		GridUpdateData<PatientModel> service = new GridUpdateData<PatientModel>(); 
		service.update(grid.getStore(), "tmc.Patient.update"); 
	}
	
	@Override
	public void insertRow(){
		if(this.companyModel.getCompanyId() == null){
			new SimpleMessage("기관선택", "등록하고자 하는 담당자의 기관을 먼저 선택하여 주세요"); 
			return ; 
		}
		
		GridInsertRow<PatientModel> service = new GridInsertRow<PatientModel>(); 
		PatientModel patientModel = new PatientModel();
		patientModel.setCompanyId(this.companyModel.getCompanyId());
		service.insertRow(grid, patientModel);
	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<PatientModel> service = new GridDeleteData<PatientModel>();
		List<PatientModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(grid.getStore(), checkedList, "tmc.Patient.delete");
	}
}
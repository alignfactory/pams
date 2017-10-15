package myApp.client.acc;

import java.util.List;
import myApp.client.acc.model.TransModel;
import myApp.frame.LoginUser;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridUpdateData;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer.HorizontalLayoutData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.LongField;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
 
public class Edit_PaymentSlip extends ContentPanel implements Editor<TransModel>, InterfaceGridOperate {

	interface EditDriver extends SimpleBeanEditorDriver<TransModel, Edit_PaymentSlip> {}
	EditDriver editDriver = GWT.create(EditDriver.class);
	Grid<TransModel> grid; 
	TransModel transModel ; 
	
	DateField transDate 	= new DateField(); // 거래일자
	TextField transName		= new TextField(); // 거래명
	LongField transAmount 	= new LongField();  // 거래금액
	
	TextField accountName	= new TextField(); // 계정명(콤보박스)
	
	TextField descript 		= new TextField(); // 적요
	
	TextField bizNo 		= new TextField(); // 거래처 사업자번호
	TextField clientName 	= new TextField(); // 거래처명
	LongField supplyAmount 	= new LongField(); //공급가액
	TextField taxApplyYn 	= new TextField(); // 부가세여부
	LongField taxAmount 	= new LongField(); // 부가세액
	DateField chargeDate 	= new DateField(); // 청구일자
	DateField accountDate	= new DateField(); // 회계일자
	TextArea note 			= new TextArea(); // 비고
	
	private Edit_PaymentSlip getThis(){
		return this;
	}
	
	public Edit_PaymentSlip(Grid<TransModel> grid){

		this.grid = grid;
		editDriver.initialize(this);
		this.setHeaderVisible(false);
	    
	    // button bar setting 
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addDeleteButton();
		
		this.getButtonBar().add(searchBarBuilder.getSearchBar());
		this.setButtonAlign(BoxLayoutPack.CENTER);
		this.add(this.getEditor());
	}

	private FormPanel getEditor(){
		
	    HorizontalLayoutData rowLayout = new HorizontalLayoutData(250, -1); // 컬럼크기 
	    rowLayout.setMargins(new Margins(0, 20, 5, 0)); // 컬럼간의 간격조정 
	    
    	HorizontalLayoutContainer row10 = new HorizontalLayoutContainer();
    	row10.add(new FieldLabel(transDate, "거래일자"), rowLayout);
    	row10.add(new FieldLabel(transName, "거래명"), rowLayout);
    	row10.add(new FieldLabel(transAmount, "거래금액"), rowLayout);
    	row10.add(new FieldLabel(descript, "적요"), new HorizontalLayoutData(500, -1));
    	
	    HorizontalLayoutContainer row20 = new HorizontalLayoutContainer();
    	row20.add(new FieldLabel(accountName, "계정과목"), rowLayout);

    	HorizontalLayoutContainer row40 = new HorizontalLayoutContainer();
    	row40.add(new FieldLabel(bizNo, "사업자번호"), rowLayout);
	    row40.add(new FieldLabel(clientName, "거래처명"), rowLayout);
    	//row40.add(new FieldLabel(clientName, "대표자명"), rowLayout);

    	HorizontalLayoutContainer row50 = new HorizontalLayoutContainer();
    	row50.add(new FieldLabel(taxApplyYn, "부가세여부"), rowLayout);
	    row50.add(new FieldLabel(supplyAmount, "공급가액"), rowLayout);
    	row50.add(new FieldLabel(taxAmount, "부가세액"), rowLayout);

    	HorizontalLayoutContainer row60 = new HorizontalLayoutContainer();
    	row60.add(new FieldLabel(chargeDate, "청구일자"), rowLayout);
	    row60.add(new FieldLabel(accountDate, "회계일자"), rowLayout);

	    HorizontalLayoutContainer row70 = new HorizontalLayoutContainer();
	    row70.add(new FieldLabel(note, "비고"), new HorizontalLayoutData(900, -1)); // new HorizontalLayoutData(800, 80, new Margins(0, 50, 0, 0)));
	    
	    VerticalLayoutContainer layout = new VerticalLayoutContainer(); 
	    layout.setScrollMode(ScrollSupport.ScrollMode.AUTOY);
	    
	    layout.add(row10, new VerticalLayoutData(1, -1, new Margins(15)));
	    layout.add(row20, new VerticalLayoutData(1, -1, new Margins(15))); 
//	    layout.add(row30, new VerticalLayoutData(1, -1, new Margins(15)));
	    layout.add(row40, new VerticalLayoutData(1, -1, new Margins(15)));
	    layout.add(row50, new VerticalLayoutData(1, -1, new Margins(15)));
	    layout.add(row60, new VerticalLayoutData(1, -1, new Margins(15)));
	    layout.add(row70, new VerticalLayoutData(1, -1, new Margins(15)));
	    
//	    layout.add(row70, new VerticalLayoutData(1, 82, new Margins(15))); // 특기사항 
	    
		// form setting 
		FormPanel form = new FormPanel(); 
    	form.setBorders(false);
	    form.setWidget(layout);
	    form.setLabelWidth(80); // 모든 field 적용 후 설정한다. 
	    return form;
	}	
	
	public void edit(TransModel transModel) {
		this.transModel = transModel; 
		editDriver.edit(this.transModel);
	}
	


	@Override
	public void retrieve() {
//		this.grid.getStore().clear();
//		GridRetrieveData<TransModel> service = new GridRetrieveData<TransModel>(this.grid.getStore());
//		service.addParam("companyId", LoginUser.getLoginUser().getCompanyId());
//		service.retrieve("psc.Student.selectByCompanyId");
	}

	@Override
	public void insertRow() {
		GridInsertRow<TransModel> service = new GridInsertRow<TransModel>(); 
		TransModel student = new TransModel(); 
//		student.setCompanyId(LoginUser.getLoginUser().getCompanyId());
		student.setCompanyId(LoginUser.getLoginUser().getCompanyModel().getCompanyId());
		service.insertRow(grid, student);
	}

	@Override
	public void update(){
		
//		if(assignDate.getCurrentValue() == null){
//			new SimpleMessage("배정일은 반드시 등록하여야 합니다."); 
//			return; 
//		}
//		
//		if(studyClassName.getCurrentValue() == null ){
//			new SimpleMessage("반이름은 반드시 등록하여야 합니다."); 
//			return ; 
//		}
		
		grid.getStore().update(editDriver.flush());
		GridUpdateData<TransModel> service = new GridUpdateData<TransModel>(); 
		service.update(grid.getStore(), editDriver.flush(), "psc.Student.update");
	}

	@Override
	public void deleteRow() {
		GridDeleteData<TransModel> service = new GridDeleteData<TransModel>();
		List<TransModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(this.grid.getStore(), checkedList, "psc.Student.delete");
	}
}
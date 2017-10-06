package myApp.client.acc;

import java.util.List;

import myApp.client.acc.model.MemoCodeModel;
import myApp.client.acc.model.MemoCodeModelProperties;
import myApp.frame.LoginUser;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.service.GridUpdateData;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class Tab_MemoCode extends VerticalLayoutContainer implements InterfaceGridOperate {
	
	private MemoCodeModelProperties properties = GWT.create(MemoCodeModelProperties.class);
	private Grid<MemoCodeModel> grid = this.buildGrid();
	private TextField className = new TextField();
	
	public Tab_MemoCode() {
		
		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(className, "유치원명", 250, 150, true); 
		searchBarBuilder.addRetrieveButton(); 
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addDeleteButton();
		
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(grid, new VerticalLayoutData(1, 1));
	}
	
	@Override
	public void retrieve(){
		GridRetrieveData<MemoCodeModel> service = new GridRetrieveData<MemoCodeModel>(grid.getStore());
		service.addParam("companyId", LoginUser.getLoginUser().getCompanyModel().getCompanyId());

//		System.out.println("Login CompanyID : "+LoginUser.getLoginUser().getCompanyId());
//		System.out.println("Login CompanyID : "+"******");

//		Info.display("companyID","" + LoginUser.getLoginUser().getCompanyId());
		
		service.retrieve("acc.MemoCode.selectByCompanyId");
	}
	
	@Override
	public void update(){
		GridUpdateData<MemoCodeModel> service = new GridUpdateData<MemoCodeModel>(); 
		service.update(grid.getStore(), "acc.MemoCode.update"); 
	}
	
	@Override
	public void insertRow(){
		GridInsertRow<MemoCodeModel> service = new GridInsertRow<MemoCodeModel>(); 
		MemoCodeModel MemoCodeModel = new MemoCodeModel();
		MemoCodeModel.setCompanyId(LoginUser.getLoginUser().getCompanyId());
		service.insertRow(grid, MemoCodeModel);
	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<MemoCodeModel> service = new GridDeleteData<MemoCodeModel>();
		List<MemoCodeModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(grid.getStore(), checkedList, "acc.MemoCode.delete");
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
		
		gridBuilder.addText(properties.acctCode(), 100, "순서", new TextField()) ;
		gridBuilder.addText(properties.subCode(), 100, "순서", new TextField()) ;
		gridBuilder.addText(properties.memoDscr(), 400, "비고", new TextField());

		return gridBuilder.getGrid(); 
	}

}
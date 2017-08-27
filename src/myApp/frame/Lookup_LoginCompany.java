package myApp.frame;

import myApp.client.sys.model.CompanyUserModel;
import myApp.client.sys.model.CompanyUserModelProperties;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.ui.SimpleMessage;
import myApp.frame.ui.builder.AbstractLookupWindow;
import myApp.frame.ui.builder.GridBuilder;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent.RowDoubleClickHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class Lookup_LoginCompany extends AbstractLookupWindow {

	private CompanyUserModelProperties properties = GWT.create(CompanyUserModelProperties.class); // 계약정보로 대체되어야 한다.
 
	private Grid<CompanyUserModel> grid = this.buildGrid(); 
	
	private Grid<CompanyUserModel> buildGrid(){
		GridBuilder<CompanyUserModel> gridBuilder = new GridBuilder<CompanyUserModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		gridBuilder.addText(properties.companyName(), 150, "고객명") ;
		gridBuilder.addText(properties.bizNo(), 100, "사업자번호") ;
		gridBuilder.addText(properties.telNo01(), 100, "대표전화") ;
		gridBuilder.addText(properties.note(), 400, "비고");
		
		return gridBuilder.getGrid(); 
	}
	
	public Lookup_LoginCompany(){
		
		this.setInit("로그인할 고객을 선택하여 주세요.", 600, 350);
		this.grid.addRowDoubleClickHandler(new RowDoubleClickHandler(){
			@Override
			public void onRowDoubleClick(RowDoubleClickEvent event) {
				confirm();
			}
		}); 
		this.add(this.grid); 
		this.retrieve(); 
	}
	
	@Override
	public void retrieve(){
		// login user로부터 사용자 정보를 가져온다. 
		Long userId = LoginUser.getLoginUser().getUserId(); 
		GridRetrieveData<CompanyUserModel> service = new GridRetrieveData<CompanyUserModel>(grid.getStore());
		service.addParam("userId", userId);
		service.retrieve("sys.CompanyUser.selectByUserId");
	}
	
	@Override
	public void confirm() {
		CompanyUserModel userCompanyModel = this.grid.getSelectionModel().getSelectedItem();
		if(userCompanyModel != null){
			this.getCallback().setLookupResult(userCompanyModel);
			this.hide();
		}
		else {
			new SimpleMessage("선택된 유치원이 없습니다.");
		}
	}

	@Override
	public void cancel() {
		this.hide(); 
	}
}

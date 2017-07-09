package myApp.client.sys;

import java.util.Map;

import myApp.client.sys.model.ColCommentsModel;
import myApp.client.sys.model.ColCommentsModelProperties;
import myApp.client.sys.model.TabCommentsModel;
import myApp.frame.PDFViewer;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.ui.InterfaceLookupResult;
import myApp.frame.ui.InterfaceTabPage;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;

public class TabPage_ColComments extends ContentPanel implements InterfaceTabPage, InterfaceGridOperate, InterfaceLookupResult {
	
	 
	private ColCommentsModelProperties properties = GWT.create(ColCommentsModelProperties.class);
	private Grid<ColCommentsModel> grid = this.buildGrid();
	private String tableName;

	public TabPage_ColComments() {
		this.setHeaderVisible(false); 
		this.add(this.grid);
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);

	    TextButton retrievePDFButton = new TextButton("Table Details 출력");
	    retrievePDFButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				
				Info.display("tableName", tableName);
				
				PDFViewer viewer = new PDFViewer(); 
				
				// 호출하려면 className과 기타 Parameter를 String으로 붙여서 넘겨주어야 한다. 
				viewer.open("className=sys.ColCommentsPDF&tableName=" + tableName);
				
			}
		});
	    searchBarBuilder.getSearchBar().add(retrievePDFButton); 

	    this.getButtonBar().add(searchBarBuilder.getSearchBar()); 
		this.setButtonAlign(BoxLayoutPack.CENTER);
	}

	public Grid<ColCommentsModel> buildGrid(){
		GridBuilder<ColCommentsModel> gridBuilder = new GridBuilder<ColCommentsModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.MULTI);
		
		gridBuilder.addLong(properties.columnId()		, 	60	,	"No."			,	HasHorizontalAlignment.ALIGN_LEFT	,	null);
		gridBuilder.addText(properties.columnName()		,	200	,	"Column ID"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);
		gridBuilder.addText(properties.columnComment()	,	250	,	"Comment"		,	HasHorizontalAlignment.ALIGN_CENTER	,	new TextField());
		gridBuilder.addText(properties.dataType()		,	100	,	"Type"			,	HasHorizontalAlignment.ALIGN_CENTER	,	null);
		gridBuilder.addText(properties.nullAble()		,	80	,	"Null"			,	HasHorizontalAlignment.ALIGN_CENTER	,	null);
		gridBuilder.addText(properties.columnLength()	,	70	,	"Length"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);

		return gridBuilder.getGrid(); 
	}

	@Override
	public void retrieve(Map<String, Object> param) {
		
		
		this.grid.getStore().clear();
		
		if(param != null){
			TabCommentsModel tableModel = (TabCommentsModel)param.get("TabCommentsModel"); 
			this.tableName = tableModel.getTableName(); 
			
			this.grid.getStore().clear();
			GridRetrieveData<ColCommentsModel> service = new GridRetrieveData<ColCommentsModel>(this.grid.getStore());
			service.addParam("tableName", tableName);
			service.retrieve("sys.ColComments.selectByTableName");
		
			// this.retrieve();
		}
		else {
			this.tableName = null; 
		}
	}

	@Override
	public void setLookupResult(Object result) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void retrieve() {
		// TODO Auto-generated method stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertRow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRow() {
		// TODO Auto-generated method stub
		
	}

}
package myApp.client.sys;

import java.util.HashMap;
import java.util.Map;

import myApp.client.sys.model.TabCommentsModel;
import myApp.client.sys.model.TabCommentsModelProperties;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.ui.InterfaceTabPage;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

public class Tab_TabComments extends BorderLayoutContainer implements InterfaceGridOperate{

    private TabCommentsModelProperties properties = GWT.create(TabCommentsModelProperties.class);
	private Grid<TabCommentsModel> grid = this.buildGrid();
	private TextField tableName = new TextField(); 
	private PlainTabPanel tabPanel = new PlainTabPanel();

	public Tab_TabComments() {

		this.setBorders(false); 
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this);
		searchBarBuilder.addTextField(tableName, "Table Name", 200, 80, true); 
		searchBarBuilder.addRetrieveButton(); 
//		searchBarBuilder.addUpdateButton();
//		searchBarBuilder.addInsertButton();
//		searchBarBuilder.addDeleteButton();
		
		this.setBorders(false);
		this.setNorthWidget(searchBarBuilder.getSearchBar(), new BorderLayoutData(40)); 
		this.setCenterWidget(this.grid, new BorderLayoutData(0.4));

		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<TabCommentsModel>(){

			@Override
			public void onSelectionChanged(SelectionChangedEvent<TabCommentsModel> event) {
				// TODO Auto-generated method stub
				retrieveTabpage(); 
			}
		});
		 
		tabPanel.add(new TabPage_ColComments(), "Column Comments"); 
		tabPanel.addSelectionHandler(new SelectionHandler<Widget>(){

			@Override
			public void onSelection(SelectionEvent<Widget> arg0) {
				if(arg0 != null) {
					retrieveTabpage();
				} 
			}
		}); 

		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(tabPanel, new VerticalLayoutData(1, 1, new Margins(5, 0, 10, 1)));
		ContentPanel panel = new ContentPanel(); //setMargins이 적용되지 않아 한번 더 감싼다. 
		panel.setHeaderVisible(false);
		panel.add(vlc);
		
		BorderLayoutData southLayoutData = new BorderLayoutData(0.6);
		southLayoutData.setMargins(new Margins(2,0,0,0));
		southLayoutData.setSplit(true);
		southLayoutData.setMaxSize(1000);
		
		this.setSouthWidget(panel, southLayoutData);
		
		tableName.setValue("%");
	}

	public Grid<TabCommentsModel> buildGrid(){
		
		GridBuilder<TabCommentsModel> gridBuilder = new GridBuilder<TabCommentsModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);

//		// TODO:컬럼 가운데 정렬 기능(그리드에서), 와우..대단하십니다. 
//		ColumnConfig<TabCommentsModel, String> tableName = gridBuilder.addText(properties.tableName(), 	180	,	"Table Name"); //, new DateField()) ;
//		tableName.setCell(new AbstractCell<String>() {
//			@Override
////			public void render(com.google.gwt.cell.client.Cell.Context arg0, String arg1, SafeHtmlBuilder arg2) {
//			public void render(com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
//				sb.appendHtmlConstant(value);   
//			}
//		});
//		tableName.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		gridBuilder.addText(properties.tableName()		,	180	,	"Table Name"	,	HasHorizontalAlignment.ALIGN_LEFT	,	new	TextField());
		gridBuilder.addText(properties.comments()		,	250	,	"Comments"		,	HasHorizontalAlignment.ALIGN_LEFT	,	new	TextField());
		gridBuilder.addText(properties.tablespaceName()	,	160	,	"Tablespace"	,	HasHorizontalAlignment.ALIGN_LEFT	,	null);	//	new	TextField());
		gridBuilder.addLong(properties.pctFree()		,	80	,	"PCT Free"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new	LongField());
		gridBuilder.addLong(properties.pctUsed()		,	90	,	"PCT Used"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new	LongField());
		gridBuilder.addLong(properties.iniTrans()		,	80	,	"Ini Trans"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new	LongField());
		gridBuilder.addLong(properties.maxTrans()		,	90	,	"Max Trans"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new	LongField());
		gridBuilder.addLong(properties.pctIncrease()	,	100	,	"PCT Increase"	,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new	LongField());
		gridBuilder.addLong(properties.freeLists()		,	80	,	"Free List"		,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new	LongField());
		gridBuilder.addLong(properties.numRows()		,	100	,	"Num Rows"		,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new	LongField());
		gridBuilder.addLong(properties.blocks()			,	80	,	"Blocks"		,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new	LongField());
		gridBuilder.addLong(properties.emptyBlocks()	,	120	,	"Empty Block"	,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new	LongField());
		gridBuilder.addLong(properties.chainCnt()		,	120	,	"Chain Counts"	,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new	LongField());
		gridBuilder.addLong(properties.avgRowLen()		,	140	,	"Avg Row Length",	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new	LongField());
		gridBuilder.addText(properties.initialExtent()	,	120	,	"Initial Extent",	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new	TextField());
		gridBuilder.addText(properties.nextExtent()		,	110	,	"Next Extent"	,	HasHorizontalAlignment.ALIGN_RIGHT	,	null);	//	new	TextField());
		gridBuilder.addText(properties.cache()			,	70	,	"Cache"			,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new	TextField());
		gridBuilder.addText(properties.tableLock()		,	100	,	"Table Lock"	,	HasHorizontalAlignment.ALIGN_CENTER	,	null);	//	new	TextField());


		return gridBuilder.getGrid(); 
	}
	
	
	public void retrieveTabpage(){
		InterfaceTabPage selectedPage = (InterfaceTabPage)tabPanel.getActiveWidget();		

		TabCommentsModel tableName = grid.getSelectionModel().getSelectedItem() ;
		
		if(tableName != null){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("TabCommentsModel", tableName); 
			selectedPage.retrieve(param);
		}
		else {
			selectedPage.retrieve(null);
		}
	}

	@Override
	public void retrieve(){
		GridRetrieveData<TabCommentsModel> service = new GridRetrieveData<TabCommentsModel>(grid.getStore());
		service.addParam("tableName", "%"); //tableName.getValue());
		service.retrieve("sys.TabComments.selectByTableName");
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

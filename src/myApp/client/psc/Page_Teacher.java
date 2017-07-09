package myApp.client.psc;

import java.util.List;

import myApp.client.psc.model.StudyClassModel;
import myApp.client.psc.model.TeacherModel;
import myApp.client.psc.model.TeacherModelProperties;
import myApp.client.sys.model.UserModel;
import myApp.frame.service.GridDeleteData;
import myApp.frame.service.GridInsertRow;
import myApp.frame.service.GridRetrieveData;
import myApp.frame.service.GridUpdateData;
import myApp.frame.ui.InterfaceLookupResult;
import myApp.frame.ui.builder.ComboBoxField;
import myApp.frame.ui.builder.GridBuilder;
import myApp.frame.ui.builder.InterfaceGridOperate;
import myApp.frame.ui.builder.SearchBarBuilder;
import myApp.frame.ui.field.LookupTriggerField;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.CollapseEvent;
import com.sencha.gxt.widget.core.client.event.CollapseEvent.CollapseHandler;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent;
import com.sencha.gxt.widget.core.client.event.TriggerClickEvent.TriggerClickHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class Page_Teacher  extends VerticalLayoutContainer implements InterfaceGridOperate, InterfaceLookupResult   {
	
	private TeacherModelProperties properties = GWT.create(TeacherModelProperties.class);
	private Grid<TeacherModel> grid = this.buildGrid();
	private StudyClassModel studyClassModel; 
	
	public Page_Teacher(){
		this.setBorders(false);
		
		SearchBarBuilder searchBarBuilder = new SearchBarBuilder(this); 
		searchBarBuilder.addInsertButton();
		searchBarBuilder.addUpdateButton();
		searchBarBuilder.addDeleteButton();
		
		this.add(searchBarBuilder.getSearchBar(), new VerticalLayoutData(1, 40));
		this.add(this.grid, new VerticalLayoutData(1, 1));
	}
	
	public void setStudyClassModel(StudyClassModel studyClassModel){
		this.studyClassModel = studyClassModel; 
	}

	public Page_Teacher getThis(){
		return this; 
	}
	
	@Override
	public void retrieve(){
		if(this.studyClassModel != null){
			GridRetrieveData<TeacherModel> service = new GridRetrieveData<TeacherModel>(grid.getStore());
			service.addParam("studyClassId", studyClassModel.getStudyClassId());
			service.retrieve("psc.Teacher.selectByStudyClassId");
		}
	}
	
	@Override
	public void update(){
		GridUpdateData<TeacherModel> service = new GridUpdateData<TeacherModel>(); 
		service.update(grid.getStore(), "psc.Teacher.update"); 
	}
	
	@Override
	public void insertRow(){
		if(this.studyClassModel != null){
			GridInsertRow<TeacherModel> service = new GridInsertRow<TeacherModel>(); 
			TeacherModel teacherModel = new TeacherModel();
			teacherModel.setStudyClassId(this.studyClassModel.getStudyClassId());
			service.insertRow(grid, teacherModel);
		}
	}
	
	@Override
	public void deleteRow(){
		GridDeleteData<TeacherModel> service = new GridDeleteData<TeacherModel>();
		List<TeacherModel> checkedList = grid.getSelectionModel().getSelectedItems() ; 
		service.deleteRow(grid.getStore(), checkedList, "psc.Teacher.delete");
	}
	
	@Override
	public void setLookupResult(Object  result) {
		UserModel userModel = (UserModel)result; 
		TeacherModel data = grid.getSelectionModel().getSelectedItem(); 
		grid.getStore().getRecord(data).addChange(properties.korName(), userModel.getKorName());
		grid.getStore().getRecord(data).addChange(properties.userId(), userModel.getUserId());
		grid.getStore().getRecord(data).addChange(properties.telNo01(), userModel.getTelNo01());
	}
	
	public Grid<TeacherModel> getGrid(){
		return this.grid; 
	}
	
	public Grid<TeacherModel> buildGrid(){

		GridBuilder<TeacherModel> gridBuilder = new GridBuilder<TeacherModel>(properties.keyId());  
		gridBuilder.setChecked(SelectionMode.SINGLE);
		
		gridBuilder.addDate(properties.applyDate(), 100, "배정일", new DateField()) ;
		
		LookupTriggerField lookupUserName = new LookupTriggerField();
		lookupUserName.addTriggerClickHandler(new TriggerClickHandler(){
			@Override
			public void onTriggerClick(TriggerClickEvent event) {
				new Lookup_User(getThis()).show();
			}
			
		}); 
		gridBuilder.addText(properties.korName(), 80, "교사명", lookupUserName) ;
		gridBuilder.addText(properties.telNo01(), 120, "연락처", new TextField());
		
		final ComboBoxField teacherTypeComboBox = new ComboBoxField("TeacherTypeCode");  
		teacherTypeComboBox.addCollapseHandler(new CollapseHandler(){
			@Override
			public void onCollapse(CollapseEvent event) {
				TeacherModel data = grid.getSelectionModel().getSelectedItem(); 
				grid.getStore().getRecord(data).addChange(properties.teacherTypeCode(), teacherTypeComboBox.getCode());
			}
		}); 
		gridBuilder.addText(properties.teacherTypeName(), 120, "교사구분", teacherTypeComboBox) ;
		
		gridBuilder.addBoolean(properties.closeYnFlag(), 50, "종료") ;
		gridBuilder.addText(properties.note(), 200, "비고", new TextField() );

		return gridBuilder.getGrid(); 
	}
	
}

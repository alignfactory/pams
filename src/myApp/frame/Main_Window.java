package myApp.frame;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.Label;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.AccordionLayoutAppearance;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer.ExpandMode;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutData;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class Main_Window extends BorderLayoutContainer {
	
	private PlainTabPanel tabPanel = new PlainTabPanel();	
	private Main_TreeMenu treeMenu = new Main_TreeMenu(tabPanel); 
	
	public Main_Window getMainWindow() {
		
		// North Layout setting 
		BorderLayoutData northLayoutData = new BorderLayoutData(34);
		northLayoutData.setMargins(new Margins(0,0,3,0));
		northLayoutData.setSplit(false);
		this.setNorthWidget(this.getNorthLayout(), northLayoutData); 
		
		// West Layout setting 
		BorderLayoutData westLayoutData = new BorderLayoutData(250);
		westLayoutData.setSplit(true);
		westLayoutData.setMargins(new Margins(0, 1, 0, 0)); // 앞쪽에 보이는 가로 줄을 없애준다
		this.setWestWidget(this.getWestLayout(), westLayoutData);
		//this.setWestWidget(treeMenu.getMenuTree()); 
				
		tabPanel.setTabScroll(true);
		tabPanel.add(new Main_Tab(), "My Page"); // my page setting
		
		ContentPanel tabContent = new ContentPanel(); 
		tabContent.setHeaderVisible(false);
		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(tabPanel, new VerticalLayoutData(1, 1, new Margins(2))); //, 2, 2, 2)));
		tabContent.add(vlc);
		
		this.setCenterWidget(tabContent);

		//Viewport viewport = new Viewport();
//		viewport.add(this);
		
		//RootPanel.get().add(viewport);
		
		return this; 
	}
	
	private ContentPanel getNorthLayout(){
		String headerMessage = "반갑습니다. " + LoginUser.getLoginUser().getCompanyModel().getCompanyName()
				+ " " + LoginUser.getLoginUser().getKorName()+ "님!" ; ; 

		Label label = new Label(headerMessage); 
		label.getElement().getStyle().setProperty("color", "silver"); // font color 변경
		label.getElement().getStyle().setProperty("fontWeight", "bold"); // font color 변경
		label.getElement().getStyle().setProperty("fontSize", "14px"); // font color 변경
				
		HBoxLayoutContainer header = new HBoxLayoutContainer();
		header.add(label, new BoxLayoutData(new Margins(8, 3, 3, 20))); 
		
		ToolButton question = new ToolButton(ToolButton.QUESTION); 
		header.add(question, new BoxLayoutData(new Margins(11, 3, 3, 10))); 
	
		ToolButton close = new ToolButton(ToolButton.CLOSE); 
		header.add(close, new BoxLayoutData(new Margins(11, 3, 3, 5))); 
		
		ContentPanel cp = new ContentPanel();
		cp.setBodyStyle("backgroundColor:#000033"); // http://www.w3schools.com/colors/colors_names.asp 페이지 참조 
		
		cp.add(header);
		cp.setHeaderVisible(false);
		return cp ; 
	}
	
	private ContentPanel getWestLayout(){
		
		AccordionLayoutAppearance accordianLayout = GWT.create(AccordionLayoutAppearance.class); 
		ContentPanel treeAccordianPanel = new ContentPanel(accordianLayout); 
		treeAccordianPanel.setHeading("Navigation");			
		treeAccordianPanel.add(this.treeMenu.getMenuTree()); // tree menu setting 
		
		
		//treeAccordianPanel.setBodyStyle("style='backgroundColor:white; color:red;'");
		
		ContentPanel newsAccordianPanel = new ContentPanel(accordianLayout); 
		newsAccordianPanel.setHeading("News & Board");
		newsAccordianPanel.add(new Label("공지사항 및 게시판입니다.")); 
		
		ContentPanel tempAccordianPanel = new ContentPanel(accordianLayout); 
		tempAccordianPanel.setHeading("공사 중...");
		tempAccordianPanel.add(new Label("준비중입니다."));
		
		AccordionLayoutContainer accordianContainer = new AccordionLayoutContainer();
		
		accordianContainer.setExpandMode(ExpandMode.SINGLE_FILL);
		accordianContainer.add(treeAccordianPanel);
		accordianContainer.setActiveWidget(treeAccordianPanel);
		accordianContainer.add(newsAccordianPanel);
		accordianContainer.add(tempAccordianPanel);
		
		VerticalLayoutContainer vlc = new VerticalLayoutContainer(); 
		vlc.add(accordianContainer, new VerticalLayoutData(1, 1, new Margins(3, 0, 0, 0)));
		
		ContentPanel cp = new ContentPanel();
		cp.setHeaderVisible(false);
		cp.add(vlc);
		
		return cp ; 
		
	}
}

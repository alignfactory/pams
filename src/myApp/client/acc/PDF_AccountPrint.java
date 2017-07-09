package myApp.client.acc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer.BoxLayoutPack;
import com.sencha.gxt.widget.core.client.container.HtmlLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class PDF_AccountPrint extends Window  {
	
	public interface HWPTemplate extends XTemplates {
	    @XTemplate("<iframe id='pdf' border=0 src='{pageName}' width='100%' height='99%'/> ")
	    SafeHtml getTemplate(String pageName);
	}

//	private native void newFile() /*-{
//		$doc.getElementById("pdf").contentWindow.newFile("HHH");
//	}-*/;

	public PDF_AccountPrint(long studentId){
		
		this.setResizable(false);
		
		this.setHeading("�썝�깮移대뱶 異쒕젰");
		this.setModal(true);
		this.setPixelSize(700, 700);
		this.setLayoutData(new MarginData(0));
		
		HWPTemplate hwp = GWT.create(HWPTemplate.class);
		String pageName = "PdfDownload?pdfClassName=acc.AccountPrint" + "&" + "studentId=" + studentId; 
		
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(hwp.getTemplate(pageName));
		
		htmlLayoutContainer.setBorders(false);
		
		ContentPanel panel = new ContentPanel();
		panel.setBorders(false);
		panel.add(htmlLayoutContainer, new MarginData(0));
		this.add(htmlLayoutContainer);

		
		TextButton downButton = new TextButton("異쒕젰?");
		downButton.setWidth(50);
		downButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				newFile(); 
			}
		});
		this.addButton(downButton);
		
		TextButton cancelButton = new TextButton("�떕湲�");
		cancelButton.setWidth(50);
		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		});
		this.addButton(cancelButton);
		this.setButtonAlign(BoxLayoutPack.CENTER); // 踰꾪듉�쓣 媛��슫�뜲濡� 


	}
	
	public void retrieve(){
//		GridRetrieveData<HireModel> service = new GridRetrieveData<HireModel>(listStore);
//		service.addParam("companyId", LoginUser.getLoginCompany());
//		service.addParam("korName", userNameField.getText());
//		service.retrieve("emp.Hire.selectByKorName");
	}
	
}

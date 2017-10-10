package myApp.frame;

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

public class PDFViewer extends Window  {
	
	public interface PDFLayout extends XTemplates {
	    @XTemplate("<iframe id='pdf' border=0 src='{pageName}' width='100%' height='99%'/> ")
	    SafeHtml getTemplate(String pageName);
	}

//	private native void newFile() /*-{
//		$doc.getElementById("pdf").contentWindow.newFile("HHH");
//	}-*/;

	public PDFViewer(){
		this.setResizable(false);
		this.setHeading("PDF Viewer");
		this.setModal(true);
		this.setPixelSize(1024, 768); // popup size(1024, 768) 
		this.setLayoutData(new MarginData(0));
	}
	
	public void open(String param){
		
		PDFLayout pdfLayout = GWT.create(PDFLayout.class);
		String pageName = "PDFLoader?" + param ; 
		
		// parameter를 넘겨준다. 
		HtmlLayoutContainer htmlLayoutContainer = new HtmlLayoutContainer(pdfLayout.getTemplate(pageName));
		htmlLayoutContainer.setBorders(false);
		
		ContentPanel panel = new ContentPanel();
		panel.setBorders(false);
		panel.add(htmlLayoutContainer, new MarginData(0));
		this.add(htmlLayoutContainer);
		
		TextButton downButton = new TextButton("Print");
		downButton.setWidth(50);
		downButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
//				newFile(); 
			}
		});
		this.addButton(downButton);
		
		TextButton cancelButton = new TextButton("Close");
		cancelButton.setWidth(50);
		cancelButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				hide(); 
			}
		});
		this.addButton(cancelButton);
		this.setButtonAlign(BoxLayoutPack.CENTER); // 버튼을 가운데로 
		this.show(); 
		
	}
}

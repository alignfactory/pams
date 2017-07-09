package myApp.server.pdf;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;

public class CellLayout {

	// font 위치 확인 필요. 
	BaseFont objBaseFont = BaseFont.createFont("D://WebFiles//font//malgun.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	Font objFont = new Font(objBaseFont, 10);
	
	public CellLayout() throws DocumentException, IOException {
		//objBaseFont = BaseFont.createFont("font/malgun.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	}

	public CellLayout(float fontSize) throws DocumentException, IOException {
		objFont.setSize(fontSize);
	}

	public PdfPCell getCell(String text){
		return this.getCell(text, Element.ALIGN_MIDDLE); 
	}
	
	public PdfPCell getCell(String text, int textAlignment){
		
		//Phrase p = new Phrase(text, objFont); 
		Paragraph p = new Paragraph(text, objFont);
		p.setAlignment(textAlignment);
		p.setFont(objFont);
		
		PdfPCell cell = new PdfPCell(p);  
		cell.setFixedHeight(20f);
		
		//cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		return cell; 
	}

	public PdfPCell getCell(String text, float height, int vAlign, int hAlign){
		
		PdfPCell cell = new PdfPCell(new Phrase(text, objFont));
		
		cell.setFixedHeight(height);
		cell.setVerticalAlignment(vAlign);
		cell.setHorizontalAlignment(hAlign);
		return cell;
	}
}

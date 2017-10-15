package myApp.server.pdf;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;

public class CellLayout {
//						  0			   1			 2			  3			   4			  			5			   6			7			   8			  9
	BaseColor whiteBack = new BaseColor(255,255,255);
	BaseColor whiteOrange = new BaseColor(255,204,153);
	BaseColor whiteCyan = new BaseColor(204,255,255);
	
//	String[] fontName = {"malgun.ttf","malgunbd.ttf","HY견명조.ttf","Gulim.ttf","Gungsuh.ttf","NanumGothicCoding.ttf","Dotum.ttf","Batang.ttf","HMFMOLD.ttf","D2Coding.ttf"};

	// font 위치 확인 필요. 
//	BaseFont objTitleFont = BaseFont.createFont("D://WebFiles//font//HMFMOLD.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	BaseFont objTitleFont = BaseFont.createFont("D://WebFiles//font//malgun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	Font objTitle = new Font(objTitleFont, 14);

	BaseFont objHeaderFont = BaseFont.createFont("D://WebFiles//font//malgun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	Font objHeader = new Font(objHeaderFont, 10);
	
	BaseFont objFooterFont = BaseFont.createFont("D://WebFiles//font//Gulim.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	Font objFooter = new Font(objFooterFont, 7);
	
	BaseFont objBaseFont = BaseFont.createFont("D://WebFiles//font//malgun.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	Font objFont = new Font(objBaseFont, 9);
	
	public CellLayout() throws DocumentException, IOException {
		//objBaseFont = BaseFont.createFont("font/malgun.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	}

	public CellLayout(float fontSize) throws DocumentException, IOException {
		objFont.setSize(fontSize);
	}

	public PdfPCell getTitle(String text){
		objFont.setSize(10);
//		return this.getCell(text, Element.ALIGN_MIDDLE);
//		Phrase p = new Phrase(text, objFont); 
		Paragraph p = new Paragraph(text, objTitle);
//		p.setAlignment(textAlignment);
		p.setFont(objTitle);
		
		PdfPCell cell = new PdfPCell(p);  
//		cell.setFixedHeight(20f);
//		cell.setBackgroundColor(whiteOrange);
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		return cell; 
	}
	
	public PdfPCell getTitle(String text, int textSize){
		
		objTitle.setSize(textSize);
		PdfPCell cell = new PdfPCell(new Phrase(text, objTitle));
		
//		cell.setFixedHeight(16f);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setBackgroundColor(whiteOrange);
//		//Phrase p = new Phrase(text, objFont); 
//		Paragraph p = new Paragraph(text, objTitle);
//		p.setAlignment(Element.ALIGN_MIDDLE);
//		p.setFont(objTitle);
//		
//		PdfPCell cell = new PdfPCell(p);  
//		cell.setFixedHeight(16f);
//		cell.setBackgroundColor(whiteCyan);
		
		//cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		return cell; 
	}

	public PdfPCell getTitle(String text, float height, int vAlign, int hAlign){
		
		PdfPCell cell = new PdfPCell(new Phrase(text, objTitle));
		
		cell.setFixedHeight(height);
		cell.setVerticalAlignment(vAlign);
		cell.setHorizontalAlignment(hAlign);
        cell.setBackgroundColor(whiteOrange);
		return cell;
	}

	public PdfPCell getTitle(String text, int textSize, String textFont, int textType, BaseColor baseColor) {
		PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(textFont, textSize, textType, baseColor)));
        cell.setBackgroundColor(whiteOrange);
		return cell; 
	}

	public PdfPCell getHeader(String text){
		objHeader.setSize(10);
//		return this.getCell(text, Element.ALIGN_MIDDLE);
		//Phrase p = new Phrase(text, objFont); 
		Paragraph p = new Paragraph(text, objHeader);
//		p.setAlignment(textAlignment);
		p.setFont(objHeader);
		
		PdfPCell cell = new PdfPCell(p);  
		cell.setFixedHeight(12f);
        cell.setBackgroundColor(whiteCyan);
		
		//cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		return cell; 
	}
	
	public PdfPCell getHeader(String text, int textSize){
		
		objHeader.setSize(textSize);
		PdfPCell cell = new PdfPCell(new Phrase(text, objHeader));
		
		cell.setFixedHeight(12f);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(whiteCyan);
//		//Phrase p = new Phrase(text, objFont); 
//		Paragraph p = new Paragraph(text, objTitle);
//		p.setAlignment(Element.ALIGN_MIDDLE);
//		p.setFont(objTitle);
//		
//		PdfPCell cell = new PdfPCell(p);  
//		cell.setFixedHeight(16f);
//		cell.setBackgroundColor(whiteCyan);
		
		//cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

		return cell; 
	}

	public PdfPCell getHeader(String text, int textSize, String textFont, int textType, BaseColor baseColor) {
		PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(textFont, textSize, textType, baseColor)));
        cell.setBackgroundColor(whiteCyan);
		return cell; 
	}

	public PdfPCell getHeader(String text, float height, int vAlign, int hAlign){
		
		PdfPCell cell = new PdfPCell(new Phrase(text, objHeader));
		
		cell.setFixedHeight(height);
		cell.setVerticalAlignment(vAlign);
		cell.setHorizontalAlignment(hAlign);
        cell.setBackgroundColor(whiteCyan);
		return cell;
	}

	public PdfPCell getCell(String text){
		return this.getCell(text, Element.ALIGN_LEFT); 
	}
	
	public PdfPCell getCell(String text, int textAlignment){
		
		//Phrase p = new Phrase(text, objFont); 
		Paragraph p = new Paragraph(text, objFont);
		p.setAlignment(textAlignment);
		p.setFont(objFont);
		
		PdfPCell cell = new PdfPCell(p);  
		cell.setFixedHeight(16f);
		
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

	public PdfPCell getCell(String text, String textFont, int textSize, int textType, BaseColor baseColor) {
		PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(textFont, textSize, textType, baseColor)));
		return cell; 
	}

	public PdfPCell getCell(String text, int textSize, int hAlign) {
		PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont("Courier", textSize, Font.NORMAL, BaseColor.BLACK)));
		cell.setHorizontalAlignment(hAlign);
//		cell.setFixedHeight(16f);
		return cell; 
	}

}

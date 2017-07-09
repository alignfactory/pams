package myApp.service.sys;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import myApp.client.sys.model.ColCommentsModel;
import myApp.client.sys.model.TabCommentsModel;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.DatabaseFactory;
import myApp.server.pdf.CellLayout;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ColCommentsPDF {
 
//public class ColCommentsPDF<AbstractDataModel> {
//    private List<AbstractDataModel> getColCommentsModel(String tableName){
//        
//    	
//    	SqlSession sqlSession = DatabaseFactory.openSession();
//		
//		List<AbstractDataModel> list = sqlSession.selectList( "sys00_ColComments.selectByTableName", tableName) ;
//		
//	    for(AbstractDataModel data : list){
//	    	
//	    	ColCommentsModel columnModel = (ColCommentsModel)data; 
//	    	System.out.println(columnModel.getColumnName()); 
//	    }
//
//	    return list; 
//    }
     
    public void getDocument(BufferedOutputStream bufferedOutputStream, HttpServletRequest request) throws DocumentException, IOException{
         
    	String tableName = request.getParameter("tableName"); 
    	System.out.println("tableName is " + tableName);
    	
    	BaseColor whiteOrange = new BaseColor(255,204,153);
//		BaseColor whiteCyan = new BaseColor(204,255,255);
    	
    	SqlSession sqlSession = DatabaseFactory.openSession();
		
		List<AbstractDataModel> list = sqlSession.selectList( "sys00_ColComments.selectByTableName", tableName) ;
		
		TabCommentsModel  tabCommentsModel = sqlSession.selectOne( "sys00_TabComments.selectByTableName", tableName) ;
	    
		Document document = new Document(PageSize.A4, 0, 0, 50, 0);
        PdfWriter.getInstance(document, bufferedOutputStream);

        document.open();
 
        CellLayout cellLayout = new CellLayout();
         
        PdfPTable table = new PdfPTable(50); // column count s
        table.setWidthPercentage(95);
//								  1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,0
        table.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
 
        PdfPCell cell;
         
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//		CellLayout titleLayout = new CellLayout(14);
////		cell = titleLayout.getCell("Table Details");
//		cell = titleLayout.getTitle("Table Details", 50f, Element.ALIGN_MIDDLE, Element.ALIGN_CENTER);
//        cell.setBackgroundColor(BaseColor.WHITE);
////		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setBorder(Rectangle.NO_BORDER);
////		cell.setFixedHeight(50f);
//		cell.setColspan(50);
//		table.addCell(cell);
//		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
		cell = cellLayout.getTitle("Table Name", 10, "맑은 고딕", Font.NORMAL, BaseColor.WHITE); 
//		cell = cellLayout.getCell("Table Name",10); 
		cell.setFixedHeight(24f);
		cell.setColspan(6);
        cell.setBackgroundColor(whiteOrange);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		
//		cell = cellLayout.getTitle(tabCommentsModel.getTableName()+" ( "+tabCommentsModel.getComments()+" )", 16, "Symbol", Font.NORMAL, BaseColor.BLACK); 
		cell = cellLayout.getTitle(tabCommentsModel.getTableName()+" ( "+tabCommentsModel.getComments()+" )"); 
//		cell.setBackgroundColor(whiteOrange);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setFixedHeight(48f);
		cell.setColspan(44);
		table.addCell(cell);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
		
		cell = cellLayout.getHeader("No", 10, "맑은 고딕", Font.NORMAL, BaseColor.RED); 
//		cell.setBackgroundColor(whiteCyan);
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setFixedHeight(24f);
		cell.setColspan(2);
		table.addCell(cell);
		
		cell = cellLayout.getHeader("Column ID",10); 
//		cell.setBackgroundColor(whiteCyan);
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setFixedHeight(24f);
		cell.setColspan(15);
		table.addCell(cell);
		
		cell = cellLayout.getHeader("Type",10); 
//		cell.setBackgroundColor(whiteCyan);
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setFixedHeight(24f);
		cell.setColspan(4);
		table.addCell(cell);
		
		cell = cellLayout.getHeader("Size",10); 
//		cell.setBackgroundColor(whiteCyan);
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setFixedHeight(24f);
		cell.setColspan(3);
		table.addCell(cell);
		
		cell = cellLayout.getHeader("Null",10); 
//		cell.setBackgroundColor(whiteCyan);
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setFixedHeight(24f);
		cell.setColspan(4);
		table.addCell(cell);

		cell = cellLayout.getHeader("Column Comment",10); 
//		cell.setBackgroundColor(whiteCyan);
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setFixedHeight(24f);
		cell.setColspan(22);
		table.addCell(cell);
		
	    for(AbstractDataModel data : list){
	    	
//			Image img = Image.getInstance(imageFile);
//	        PdfPCell cell = new PdfPCell(img, true);
//	        cell.setPadding(3);
//	        return cell;
	    	ColCommentsModel columnModel = (ColCommentsModel)data; 
//	    	System.out.println(columnModel.getColumnName()); 
	    	
			cell = cellLayout.getCell(columnModel.getColumnId()+"", 10, Element.ALIGN_CENTER); 
			cell.setColspan(2);
			table.addCell(cell);
			
			cell = cellLayout.getCell(columnModel.getColumnName(), "맑은 고딕", 10, Font.NORMAL, BaseColor.RED); 
			cell = cellLayout.getCell(columnModel.getColumnName(), 10, Element.ALIGN_LEFT); 
			cell.setColspan(15);
			table.addCell(cell);
			
			cell = cellLayout.getCell(columnModel.getDataType()); 
			cell.setColspan(4);
			table.addCell(cell);
			
			cell = cellLayout.getCell(columnModel.getColumnLength()); 
			cell.setColspan(3);
			table.addCell(cell);
			
			cell = cellLayout.getCell(columnModel.getNullAble()); 
			cell.setColspan(4);
			table.addCell(cell);

//			cell = cellLayout.getCell(columnModel.getColumnComment(), "맑은 고딕", 10, Font.NORMAL, BaseColor.RED); 
			cell = cellLayout.getCell(columnModel.getColumnComment(), 10, Element.ALIGN_LEFT); 
			cell.setColspan(22);
			table.addCell(cell);
			
	    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
		cell = cellLayout.getCell(""); 
		cell.setFixedHeight(150f);
		cell.setColspan(22);
		table.addCell(cell);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
		document.add(table);
		document.close();
    }

//    private void Chunk(String valueOf, Font redNormal) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public static final Font RED_NORMAL = new Font(FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
//    public static final Font BLUE_BOLD = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);
//    public static final Font GREEN_ITALIC = new Font(FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.GREEN);
//    public static final Chunk B = new Chunk("b", BLUE_BOLD);
//    public static final Chunk G = new Chunk("g", GREEN_ITALIC);
//
//    private Chunk returnCorrectColor(char letter) {
//        if (letter == 'b'){
//            return B;
//        }
//        else if(letter == 'g'){
//            return G;
//        }
//        return new Chunk(String.valueOf(letter), RED_NORMAL);
//    }

//    public class HeaderFooter extends PdfPageEventHelper {
//        protected ElementList header;
//        protected ElementList footer;
//        public HeaderFooter() throws IOException {
//            header = XMLWorkerHelper.parseToElementList(HEADER, null);
//            footer = XMLWorkerHelper.parseToElementList(FOOTER, null);
//        }
//        @Override
//        public void onEndPage(PdfWriter writer, Document document) {
//            try {
//                ColumnText ct = new ColumnText(writer.getDirectContent());
//                ct.setSimpleColumn(new Rectangle(36, 832, 559, 810));
//                for (Element e : header) {
//                    ct.addElement(e);
//                }
//                ct.go();
//                ct.setSimpleColumn(new Rectangle(36, 10, 559, 32));
//                for (Element e : footer) {
//                    ct.addElement(e);
//                }
//                ct.go();
//            } catch (DocumentException de) {
//                throw new ExceptionConverter(de);
//            }
//        }
//    }

}
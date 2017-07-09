package myApp.service.acc;
  
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
 
import myApp.client.psc.model.StudentModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.pdf.CellLayout;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
public class AccountPDF {
 
    private List<AbstractDataModel> getStudentModel(String studentId){
         
//      ServiceRequest request = new ServiceRequest("psc.Student.selectById");
//      request.add("studentId", Long.parseLong(studentId));
//      
//      RetrieveData retrieveData = new RetrieveData(); 
//      ServiceResult result = retrieveData.retrieve(request); 
 
//      if(result.getStatus() < 0 ) {
//          return null;
//      }
//      else {
//          if(result.getResult().size() > 0 ){
//              List<AbstractDataModel> list = result.getResult(); 
//              return list;
//          }
//          else {
//              System.out.println("Not found studnet:" + studentId); 
//              return null ; 
//          }
//      }
        return null; 
    }
     
    public void getDocument(BufferedOutputStream bufferedOutputStream, HttpServletRequest request) throws DocumentException, IOException{
         
        Document document = new Document(PageSize.A4, 0, 0, 50, 0);
        PdfWriter.getInstance(document, bufferedOutputStream);
        document.open();
 
//      Font paragrapthFont = Font.FontFactory. .valueOf("굴림체"); // Factory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
        CellLayout cellLayout = new CellLayout(10);
         
        PdfPTable table = new PdfPTable(17); // column count 
        table.setWidthPercentage(90);
        table.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
//      2       3           1   1   4               2       3           1
 
        PdfPCell cell;
         
//      cell = cellLayout.getCell("지출결의서");
//      cell.setColspan(17);
//      cell.setRowspan(2);
//      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//      table.addCell(cell);
 
/*
        cell = new PdfPCell();
        cell.addElement(new Paragraph("T_ 지출결의서 _O:"));
//      cell.setRowspan(2);
        Paragraph p = new Paragraph("T- 지출결의서 -O:");
        p.setAlignment(Element.ALIGN_RIGHT);
//      p.setIndentationRight(10);
        cell.addElement(p);
        cell.setColspan(17);
        table.addCell(cell);
*/
 
        cell = cellLayout.getCell("지출결의서");
        cell.setFixedHeight(40f);
        cell.setColspan(17);
//      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        cell.setRowspan(2);
        table.addCell(cell);
 
        cell = cellLayout.getCell("계"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);


        cell.setFixedHeight(24f);
        cell.setColspan(2);
        table.addCell(cell);
        
        Paragraph p = new Paragraph("Name");
        //p.setIndentationLeft(10);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        
//        cell = cellLayout.getCell("원감"); 
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setColspan(2);
        
        table.addCell(cell);
        
        PdfPCell cell2  = new PdfPCell(); 
        cell2 = cellLayout.getCell("MyHome", Element.ALIGN_RIGHT); 
        cell2.setColspan(2);
        table.addCell(cell2);
        
        
        cell = cellLayout.getCell("2017 학년도 세출\n선호유치원 회계"); 
        cell.setColspan(5);
        cell.setRowspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("계"); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("원감"); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("원장"); 
        cell.setColspan(2);
        table.addCell(cell);
 
        cell = cellLayout.getCell(""); 
        cell.setFixedHeight(48f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
//      cell = cellLayout.getCell(""); 
//      cell.setColspan(5);
//      table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
 
//      table.addCell(cellLayout.getCell(""));
//      table.addCell(cellLayout.getCell(""));
//      table.addCell(cellLayout.getCell(""));
//      table.addCell(cellLayout.getCell(""));
//      table.addCell(cellLayout.getCell(""));
//      table.addCell(cellLayout.getCell(""));
//      table.addCell(cellLayout.getCell(""));
 
        cell = cellLayout.getCell("발의"); 
        cell.setFixedHeight(28f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("2017년01월01일"); 
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell("관"); 
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell("관리운영비"); 
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell("발의"); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("2017년01월01일"); 
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
//      cell.setColspan(1);
        table.addCell(cell);
 
        cell = cellLayout.getCell("지출원인\n행위부등기"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(28f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("2017년01월01일"); 
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell("항"); 
        cell.setRowspan(2);
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell("학교운영비"); 
        cell.setRowspan(2);
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell("지급명령\n발행부등기"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("2017년01월01일"); 
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
//      cell.setColspan(1);
        table.addCell(cell);
 
        cell = cellLayout.getCell("계약"); 
        cell.setFixedHeight(28f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("2017년01월01일"); 
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
//      cell.setColspan(1);
        table.addCell(cell);
//      cell = cellLayout.getCell("항"); 
//      cell.setColspan(1);
//      table.addCell(cell);
//      cell = cellLayout.getCell("학교운영비"); 
//      cell.setColspan(4);
//      table.addCell(cell);
        cell = cellLayout.getCell("지출부등기"); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("2017년01월01일"); 
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
//      cell.setColspan(1);
        table.addCell(cell);
 
        cell = cellLayout.getCell("검사"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(28f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("2017년01월01일"); 
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell("목"); 
        cell.setRowspan(2);
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell("공통운영비"); 
        cell.setRowspan(2);
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell("지급명령\n법    호"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("제        호"); 
        cell.setColspan(4);
        table.addCell(cell);
 
        cell = cellLayout.getCell("출납부등기"); 
        cell.setFixedHeight(28f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("2017년01월01일"); 
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
//      cell.setColspan(1);
        table.addCell(cell);
//      cell = cellLayout.getCell("항"); 
//      cell.setColspan(1);
//      table.addCell(cell);
//      cell = cellLayout.getCell("학교운영비"); 
//      cell.setColspan(4);
//      table.addCell(cell);
        cell = cellLayout.getCell("부가가치세"); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("0"); 
        cell.setColspan(4);
        table.addCell(cell);
 
        cell = cellLayout.getCell("적    요"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setFixedHeight(28f);
        cell.setColspan(5);
        table.addCell(cell);
        cell = cellLayout.getCell("채    주"); 
        cell.setColspan(6);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(4);
        table.addCell(cell);
 
        cell = cellLayout.getCell("차량연료"); 
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
        cell.setFixedHeight(28f);
        cell.setRowspan(6);
        cell.setColspan(5);
        table.addCell(cell);
        cell = cellLayout.getCell("상    호"); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("본동문화주유소"); 
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(4);
        table.addCell(cell);
 
//      cell = cellLayout.getCell("차량연료"); 
//      cell.setVerticalAlignment(Element.ALIGN_LEFT);
//      cell.setFixedHeight(28f);
//      cell.setColspan(5);
//      table.addCell(cell);
        cell = cellLayout.getCell("주    소"); 
        cell.setFixedHeight(48f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("대구시 달서구 본동 1113-3외 5필"); 
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(4);
        table.addCell(cell);
 
//      cell = cellLayout.getCell("차량연료"); 
//      cell.setVerticalAlignment(Element.ALIGN_LEFT);
//      cell.setFixedHeight(24f);
//      cell.setColspan(5);
//      table.addCell(cell);
        cell = cellLayout.getCell("법인(주민)\n번호"); 
        cell.setFixedHeight(28f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("514-13-90896"); 
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(4);
        table.addCell(cell);
 
//      cell = cellLayout.getCell("차량연료"); 
//      cell.setVerticalAlignment(Element.ALIGN_LEFT);
//      cell.setFixedHeight(24f);
//      cell.setColspan(5);
//      table.addCell(cell);
        cell = cellLayout.getCell("계좌번호"); 
        cell.setFixedHeight(28f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("11111-314-01-92516"); 
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(4);
        table.addCell(cell);
 
//      cell = cellLayout.getCell("차량연료"); 
//      cell.setVerticalAlignment(Element.ALIGN_LEFT);
//      cell.setFixedHeight(24f);
//      cell.setColspan(5);
//      table.addCell(cell);
        cell = cellLayout.getCell("성    명"); 
        cell.setFixedHeight(28f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("황순이"); 
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell("공급가액"); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("91,000"); 
        cell.setColspan(4);
        table.addCell(cell);
 
//      cell = cellLayout.getCell("차량연료"); 
//      cell.setVerticalAlignment(Element.ALIGN_LEFT);
//      cell.setFixedHeight(24f);
//      cell.setColspan(5);
//      table.addCell(cell);
        cell = cellLayout.getCell("오른쪽금액을 영수함.\n2017년 03월 17일"); 
        cell.setFixedHeight(48f);
        cell.setColspan(6);
        table.addCell(cell);
        cell = cellLayout.getCell("합    계"); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("91,000"); 
        cell.setColspan(4);
        table.addCell(cell);
 
 
        //      cell = cellLayout.getCell("아주좋음"); 
//      cell.setColspan(3);
//
//      table.addCell(cellLayout.getCell("지출결의서"));
////        cell = cellLayout.getCell("지출결의서"); 
//
////        table.addCell(cellLayout.getCell(studentModel.getStudentNo()));
//
//      table.addCell(cellLayout.getCell("한글이름")); 
////        table.addCell(cellLayout.getCell(studentModel.getKorName()));
//      
//      table.addCell(cellLayout.getCell("주민번호")); 
//      table.addCell(cellLayout.getCell(""));
//
//      table.addCell(cellLayout.getCell("반이름")); 
//      table.addCell(cellLayout.getCell(""));
//      
//      table.addCell(cellLayout.getCell("배정일"));
//      table.addCell(cellLayout.getCell(""));
//      
//      table.addCell(cellLayout.getCell("남녀구분")); 
//      table.addCell(cellLayout.getCell(""));
//      
//      table.addCell(cellLayout.getCell("생일")); 
//      table.addCell(cellLayout.getCell(""));
//
//      table.addCell(cellLayout.getCell("영문명")); 
//      table.addCell(cellLayout.getCell(""));
//
//      table.addCell(cellLayout.getCell("한자명")); 
//      table.addCell(cellLayout.getCell(""));
//
//      table.addCell(cellLayout.getCell("핸드폰")); 
//      table.addCell(cellLayout.getCell(""));
//
//      table.addCell(cellLayout.getCell("집전화")); 
//      table.addCell(cellLayout.getCell(""));
//
//      table.addCell(cellLayout.getCell("이메일")); 
//      table.addCell(cellLayout.getCell(""));
//
//      table.addCell(cellLayout.getCell("성격")); 
//
//      cell = cellLayout.getCell("아주좋음"); 
//      cell.setColspan(3);
////        cell.setFixedHeight(20f);
////        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//      
//      table.addCell(cell);
//      
//      table.addCell(cellLayout.getCell("특기")); 
//      table.addCell(cellLayout.getCell(""));
//      
//      table.addCell(cellLayout.getCell("버릇")); 
//      table.addCell(cellLayout.getCell(""));
//      
//      cell = cellLayout.getCell("좋아하는\r음식"); 
//      cell.setFixedHeight(28f);
//      table.addCell(cell); 
//      table.addCell(cellLayout.getCell(""));
//
//      table.addCell(cellLayout.getCell("싫어하는\r음식"));
//      table.addCell(cellLayout.getCell(""));
//
//      table.addCell(cellLayout.getCell("희망학교"));
//      table.addCell(cellLayout.getCell(""));
//
//      table.addCell(cellLayout.getCell("이전학력")); 
//      cell = cellLayout.getCell(""); 
//      cell.setColspan(3);
//      table.addCell(cell);
         
        cell = cellLayout.getCell("특기사항"); 
        cell.setFixedHeight(42f);
        table.addCell(cell);
         
         
        cell = cellLayout.getCell("");
        cell.setColspan(6);
        table.addCell(cell);
         
        document.add(table);
//
//        
//        Paragraph p = new Paragraph(" ");
//        document.add(p); 
//          
//      PdfPTable table2 = new PdfPTable(8);
//      table2.setWidthPercentage(90);
//
//      table2.setWidths(new int[]{10, 15, 10, 15, 10, 15, 10, 15});
//      
//      table2.addCell(cellLayout.getCell("헨드폰")); 
//      table2.addCell(cellLayout.getCell(""));
//
//      table2.addCell(cellLayout.getCell("집전화")); 
//      table2.addCell(cellLayout.getCell(""));
//
//      table2.addCell(cellLayout.getCell("이메일")); 
//      table2.addCell(cellLayout.getCell(""));
//      
//      table2.addCell(cellLayout.getCell("이메일"));
//      table2.addCell(cellLayout.getCell(""));
         
         
//      document.add(table2);
         
        document.close();
         
//      for(AbstractDataModel dataModel:list){
//          StudentModel studentModel = (StudentModel)dataModel; 
//          
//          PdfPCell cell;
//          
//          cell = createImageCell(studentModel.getStudentId().toString()); 
//          cell.setRowspan(7);
//          cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//          table.addCell(cell);
//          
//          table.addCell(cellLayout.getCell("원생번호"));
//          table.addCell(cellLayout.getCell(studentModel.getStudentNo()));
//  
//          table.addCell(cellLayout.getCell("한글이름")); 
//          table.addCell(cellLayout.getCell(studentModel.getKorName()));
//          
//          table.addCell(cellLayout.getCell("주민번호")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("반이름")); 
//          table.addCell(cellLayout.getCell(""));
//          
//          table.addCell(cellLayout.getCell("배정일"));
//          table.addCell(cellLayout.getCell(""));
//          
//          table.addCell(cellLayout.getCell("남녀구분")); 
//          table.addCell(cellLayout.getCell(""));
//          
//          table.addCell(cellLayout.getCell("생일")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("영문명")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("한자명")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("헨드폰")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("집전화")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("이메일")); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("성격")); 
//  
//          cell = cellLayout.getCell("아주좋음"); 
//          cell.setColspan(3);
//  //      cell.setFixedHeight(20f);
//  //      cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//  //      cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//          
//          table.addCell(cell);
//          
//          table.addCell(cellLayout.getCell("특기")); 
//          table.addCell(cellLayout.getCell(""));
//          
//          table.addCell(cellLayout.getCell("버릇")); 
//          table.addCell(cellLayout.getCell(""));
//          
//          cell = cellLayout.getCell("좋아하는\r음식"); 
//          cell.setFixedHeight(28f);
//          table.addCell(cell); 
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("싫어하는\r음식"));
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("희망학교"));
//          table.addCell(cellLayout.getCell(""));
//  
//          table.addCell(cellLayout.getCell("이전학력")); 
//          cell = cellLayout.getCell(""); 
//          cell.setColspan(3);
//          table.addCell(cell);
//          
//          cell = cellLayout.getCell("특기사항"); 
//          cell.setFixedHeight(42f);
//          table.addCell(cell);
//          
//          
//          cell = cellLayout.getCell("");
//          cell.setColspan(6);
//          table.addCell(cell);
//      }
//      document.add(table);
//
//        
//        Paragraph p = new Paragraph(" ");
//        document.add(p); 
//          
//      PdfPTable table2 = new PdfPTable(8);
//      table2.setWidthPercentage(90);
//
//      table2.setWidths(new int[]{10, 15, 10, 15, 10, 15, 10, 15});
//      
//      table2.addCell(cellLayout.getCell("헨드폰")); 
//      table2.addCell(cellLayout.getCell(""));
//
//      table2.addCell(cellLayout.getCell("집전화")); 
//      table2.addCell(cellLayout.getCell(""));
//
//      table2.addCell(cellLayout.getCell("이메일")); 
//      table2.addCell(cellLayout.getCell(""));
//      
//      table2.addCell(cellLayout.getCell("이메일"));
//      table2.addCell(cellLayout.getCell(""));
//      
//      
//      document.add(table2);
//      
//      document.close();
    }
     
    private PdfPCell createImageCell(String studentId) throws DocumentException, IOException {
        String filePath = "D:\\WebFiles\\" ;
        String imageFile = filePath + (Long.parseLong(studentId)/100) + "\\"  + studentId ;
         
        Image img = Image.getInstance(imageFile);
        PdfPCell cell = new PdfPCell(img, true);
        cell.setPadding(3);
        return cell;
    }
     
 
}
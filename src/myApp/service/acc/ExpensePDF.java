package myApp.service.acc;
  
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;

import myApp.client.acc.model.SeasonModel;
import myApp.client.acc.model.TransModel;
import myApp.frame.LoginUser;
import myApp.server.DatabaseFactory;
import myApp.server.data.DateUtil;
import myApp.server.pdf.CellLayout;

import com.ibm.icu.text.NumberFormat;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
 
public class ExpensePDF {
 
	String companyName = null;
	String seasonSeq = null;
	
	private List<TransModel> getTransModel(HttpServletRequest request) throws ParseException{

    	String companyId = request.getParameter("companyId"); 
    	String baseMonth = request.getParameter("baseMonth"); 
    	baseMonth = baseMonth.replace("-",  ""); 
    	
    	this.companyName = request.getParameter("companyName");

    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date startDate = dateFormat.parse(baseMonth + "01"); 
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(startDate);

		Date endDate = dateFormat.parse(baseMonth + cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("companyId", Long.parseLong(companyId));
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		param.put("inOutCode", "OUT"); 
		
		System.out.println("companyId is " + Long.parseLong(companyId));
		System.out.println("startDate is " + startDate);
		System.out.println("endDate is " + endDate);
		
		SqlSession sqlSession = DatabaseFactory.openSession();
		
		SeasonModel season = sqlSession.selectOne("acc01_season.selectByBaseDate", param); 
		this.seasonSeq = season.getSeq(); 
		
		List<TransModel> list = sqlSession.selectList("acc06_trans.selectByTransDate", param) ;

	    return list; 
	}

    public void getDocument(BufferedOutputStream bufferedOutputStream, HttpServletRequest request) throws DocumentException, IOException, ParseException {
    	
    	List<TransModel> list = this.getTransModel(request); 
    	
    	if(list .size() > 0 ){
    	
	        Document document = new Document(PageSize.A4, 15, 15, 100, 20);
	        PdfWriter.getInstance(document, bufferedOutputStream);
	        
//	        document.setMargins(20, 20, 100, 20);
	        document.open();
	
//	        TransModel transModel = list.get(0); 
	        
	        for(TransModel transModel : list){
	        	printPDF(document, transModel); 
	        }
	        
//	        printPDF(document, transModel);
	        document.close();
        
    	}
    	else {
    		System.out.println("not found trans data");
    	}
        
	}

    
	private void printPDF(Document document, TransModel transModel) throws DocumentException, IOException{
		
    	document.newPage();
//      Font paragrapthFont = Font.FontFactory. .valueOf("굴림체");
//		Factory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
        CellLayout cellLayout = new CellLayout(10);
         
        PdfPTable table = new PdfPTable(17); // column count 
        table.setWidthPercentage(90);
        table.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1});
//      2       3           1   1   4               2       3           1
 
        PdfPCell cell;
         
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.KOREA);	//	Number를 Currency로 바꿈
        String	taxAmount = format.format(transModel.getTaxAmount());
        String	transAmount = format.format(transModel.getTransAmount());
        String	supplyAmount = format.format(transModel.getSupplyAmount()); 
        
//		String	transDate = format.format(transModel.getTransDate());
        String transDate = DateUtil.getDate(transModel.getTransDate(), "yyyy년MM월dd일"); 
//		System.out.println("transDate is " + transDate); 


		cell = cellLayout.getTitle("지출결의서", 18); 
//        cell = cellLayout.getHeader("지출결의서",18);//, "맑은 고딕", 18, Font.NORMAL, BaseColor.BLACK);
        cell.setFixedHeight(60f);
        cell.setBackgroundColor(BaseColor.WHITE);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(17);
		cell.setRowspan(2);
		table.addCell(cell);

//		cell = cellLayout.getTitle("지출결의서", 18, "맑은 고딕", Font.NORMAL, BaseColor.BLACK); 
//		cell.setFixedHeight(60f);
//		cell.setColspan(17);
//		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setRowspan(2);
//		table.addCell(cell);
 
        cell = cellLayout.getCell("계"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setFixedHeight(30f);
        cell.setColspan(2);
        table.addCell(cell);
        
        cell = cellLayout.getCell("원감"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        
        cell = cellLayout.getCell("원장"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        
//		PdfPCell cell2  = new PdfPCell(); 
//		cell2 = cellLayout.getCell("원장", Element.ALIGN_MIDDLE); 
//		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell2.setColspan(2);
//		table.addCell(cell2);

        cell = cellLayout.getCell(this.seasonSeq + " 학년도 세출\n\n" + this.companyName +" 회계"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(5);
        cell.setRowspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("계"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("원감"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("원장"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
 
        cell = cellLayout.getCell(""); 
        cell.setFixedHeight(60f);
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
        cell.setFixedHeight(30f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
//		table.addCell(cellLayout.getCell(transDate));
        cell = cellLayout.getCell(transDate); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell("관"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell(transModel.getGwanName()); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell("발의"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
		table.addCell(cell);
//		table.addCell(cellLayout.getCell(transDate)); 
		cell = cellLayout.getCell(transDate); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//      cell.setColspan(1);
        table.addCell(cell);
 
        cell = cellLayout.getCell("지출원인\n행위부등기"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(30f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transDate); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell("항"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setRowspan(2);
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell(transModel.getHangName()); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setRowspan(2);
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell("지급명령\n발행부등기"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transDate); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//      cell.setColspan(1);
        table.addCell(cell);
 
        cell = cellLayout.getCell("계약"); 
        cell.setFixedHeight(30f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transDate); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//      cell.setColspan(1);
        table.addCell(cell);
//      cell = cellLayout.getCell("항"); 
//      cell.setColspan(1);
//      table.addCell(cell);
//      cell = cellLayout.getCell("학교운영비"); 
//      cell.setColspan(4);
//      table.addCell(cell);
        cell = cellLayout.getCell("지출부등기"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transDate); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//      cell.setColspan(1);
        table.addCell(cell);
 
        cell = cellLayout.getCell("검사"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(30f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transDate); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell("목"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setRowspan(2);
//      cell.setColspan(1);
        table.addCell(cell);
        cell = cellLayout.getCell(transModel.getGmokName()); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setRowspan(2);
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell("지급명령\n법    호"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell("제        호"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        table.addCell(cell);
 
        cell = cellLayout.getCell("출납부등기"); 
        cell.setFixedHeight(30f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transDate); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(3);
        table.addCell(cell);
        cell = cellLayout.getCell("인"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//      cell.setColspan(1);
        table.addCell(cell);
//      cell = cellLayout.getCell("항"); 
//      cell.setColspan(1);
//      table.addCell(cell);
//      cell = cellLayout.getCell("학교운영비"); 
//      cell.setColspan(4);
//      table.addCell(cell);
        cell = cellLayout.getCell("부가가치세"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);

//		cell = cellLayout.getCell("￦"+transModel.getTaxAmount()); 
//        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.KOREA);//	.getCurrencyInstance(Locale.KOREAN);
//        String	taxAmount = format.format(transModel.getTaxAmount());
		cell = cellLayout.getCell(taxAmount); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setColspan(4);
        table.addCell(cell);
 
        cell = cellLayout.getCell("적    요"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(30f);
        cell.setColspan(5);
        table.addCell(cell);
        cell = cellLayout.getCell("채    주"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(6);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(4);
        table.addCell(cell);

        cell = cellLayout.getCell(transModel.getDescript()); 
        cell.setVerticalAlignment(Element.ALIGN_LEFT);
//		cell.setFixedHeight(30f);
        cell.setRowspan(6);
        cell.setColspan(5);
        table.addCell(cell);
        cell = cellLayout.getCell("상    호"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setFixedHeight(45f);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transModel.getClientName()); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(4);
        table.addCell(cell);
 
        cell = cellLayout.getCell("주    소"); 
        cell.setFixedHeight(90f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transModel.getZipCode()+ "\n\n" + transModel.getZipAddr() + "  " + transModel.getZipDetail()); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell(" "); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(" "); 
        cell.setColspan(4);
        table.addCell(cell);
 
        cell = cellLayout.getCell("법인(주민)\n번호"); 
        cell.setFixedHeight(45f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transModel.getBizNo()); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(4);
        table.addCell(cell);

        cell = cellLayout.getCell("계좌번호"); 
        cell.setFixedHeight(45f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transModel.getAccountNo()); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(""); 
        cell.setColspan(4);
        table.addCell(cell);

        cell = cellLayout.getCell("성    명"); 
        cell.setFixedHeight(45f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transModel.getCeoName()); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(4);
        table.addCell(cell);
        cell = cellLayout.getCell("공급가액"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(supplyAmount); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setColspan(4);
        table.addCell(cell);
 
        transDate = DateUtil.getDate(transModel.getTransDate(), "yyyy년 MM월 dd일");
        cell = cellLayout.getCell("오른쪽금액을 영수함.\n\n"+transDate);  
        cell.setFixedHeight(105f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(6);
        table.addCell(cell);
        cell = cellLayout.getCell("합    계"); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = cellLayout.getCell(transAmount); 
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setColspan(4);
        table.addCell(cell);

        document.add(table);

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
//      cell.setFixedHeight(30f);
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
//         
//        cell = cellLayout.getCell("특기사항"); 
//        cell.setFixedHeight(45f);
//        table.addCell(cell);
//         
//         
//        cell = cellLayout.getCell("");
//        cell.setColspan(6);
//        table.addCell(cell);
//         
//        document.add(table);
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
         
 
         
//      for(AbstractDataModel dataModel:list){
//          TransModel studentModel = (TransModel)dataModel; 
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
//          cell.setFixedHeight(30f);
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
//          cell.setFixedHeight(45f);
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
     
}
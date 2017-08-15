package myApp.server.file;
 
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import myApp.client.acc.model.BankUploadModel;
import myApp.client.sys.model.FileModel;
import myApp.server.DatabaseFactory;
import myApp.server.data.DateUtil;
import myApp.server.data.IsNewData;

public class FileUpload implements javax.servlet.Servlet {
	
	private String getUploadPath(String fileId){
		return "D:\\WebFiles\\" + (Long.parseLong(fileId)/100) ;	
	}
	
	public void saveFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SqlSession sqlSession = DatabaseFactory.openSession();
		
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(1 * 1024 * 1024); // 1 MB
			ServletFileUpload serveltFileUpload = new ServletFileUpload(factory);
			FileItem fileItem = serveltFileUpload.parseRequest(request).get(0); // 처음 하나의 파일만 가져온다.  

			String fileId = request.getParameter("fileId");

			FileModel fileModel = new FileModel();
			String fileName = fileItem.getName();	// file upload시에만 사용된다.
			
			if(fileId == null || "null".equals(fileId)){
				
				// get file id 
				IsNewData isNewData = new IsNewData(); 
				fileId = isNewData.getSeq(sqlSession).toString();
				
				fileModel.setFileId(Long.parseLong(fileId));
				fileModel.setParentId(Long.parseLong(request.getParameter("parentId"))); 
				fileModel.setFileName(fileName); 
				fileModel.setRegDate(new Date()); // 데이터베이스 시간으로 변경해야 한다.  
				fileModel.setServerPath(this.getUploadPath(fileId)); //100개씩 잘라 보관한다.
				
				Double size = Double.parseDouble((fileItem.getSize()/1024) + ""); 
				fileModel.setSize(size); 
				
				fileModel.setDelDate(null); 
				fileModel.setNote(null);
				
				sqlSession.insert("sys10_file.insert", fileModel); 
			}
			 
			File subDir  = new File(this.getUploadPath(fileId));
	        if(!subDir.exists()) {
	        	subDir.mkdir(); 
	        }

	        File file = new File(this.getUploadPath(fileId), fileId);
    	    file.deleteOnExit(); // 있으면 먼저 지워라.
            fileItem.write(file);
            
            sqlSession.commit();
            sqlSession.close();
            
            setResult(response, "파일을 성공적으로 등록하였습니다.");
            
		} 
		catch (FileUploadException e) {
			sqlSession.rollback();
			sqlSession.close();
			e.printStackTrace();
			setResult(response, "Error encountered while parsing the request");
		} 
		catch (Exception e) {
			sqlSession.rollback();
			sqlSession.close();
			e.printStackTrace();
			setResult(response, "Error encountered while uploading file");
		}
	}
	
	private void setResult(HttpServletResponse response, String message){
		
		try {
			PrintWriter out = response.getWriter();
			out.println(message);
			out.flush();
			
			System.out.println("end of file upload");
		}
		catch(Exception e){
			System.out.println(e.toString()); 
		}
	}

	private void bankUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("excel upload start"); 
		
		Long companyId = Long.parseLong(request.getParameter("companyId")); 
		String baseMonth = request.getParameter("baseMonth") ; 
		
		// http://javaslave.tistory.com/78 에서 참조할 것.
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(1 * 1024 * 1024); // 1 MB
			ServletFileUpload serveltFileUpload = new ServletFileUpload(factory);
			FileItem fileItem = serveltFileUpload.parseRequest(request).get(0); // 처음 하나의 파일만 가져온다.  

			// 파일로 저장하지 않고 stream으로 파일을 읽는다. 굳이 저장할 필요가 없잖아..
			InputStream inputStream = fileItem.getInputStream();  
			Workbook workbook = WorkbookFactory.create(inputStream);

			// sheet 찾기 - 여기서는 필요없음 1번째 시트만 읽을 예정임. 
			// for(int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++){
				
			List<BankUploadModel> uploadList = new ArrayList<BankUploadModel>(); 
			
			Sheet curSheet = workbook.getSheetAt(0); // 첫번째 시트만 읽는다. 
			
			// 2번째 행(row)부터 날자를 찾는다.
			for(int rowIndex = 2; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++){
					
				System.out.println("row cosunt is " + rowIndex); 
				
				Row curRow = curSheet.getRow(rowIndex);

				if(curRow.getCell(0) != null) {
					
					String transNo = curRow.getCell(0).getStringCellValue();
					String transDateString = curRow.getCell(1).getStringCellValue();
					String transName = curRow.getCell(2).getStringCellValue();
					Double transAmountIn = curRow.getCell(3).getNumericCellValue(); 
					Double transAmountOut = curRow.getCell(4).getNumericCellValue();
					Double balance = curRow.getCell(5).getNumericCellValue();
					String bigo = curRow.getCell(6).getStringCellValue();
					String memo = curRow.getCell(7).getStringCellValue();

					// validate : 년월비교 
					if(transDateString.indexOf(baseMonth) < 0 ){
						System.out.println("화면의 기준월과 엑셀의 월정보(yyyy-mm)가 맞지않습니다." + transDateString + ":" + baseMonth); 
						this.setResult(response, "화면의 기준월과 엑셀의 월정보가 맞지않습니다. 행번호:" +  (rowIndex + 1) ); 
						return; 
					}

					BankUploadModel uploadModel = new BankUploadModel();
					uploadModel.setCompanyId(companyId);
					uploadModel.setTransNo(Long.parseLong(transNo));
					
					Date transDate = DateUtil.getDate(transDateString.substring(0,  10)); 
					uploadModel.setTransDate(transDate);
					uploadModel.setTransName(transName);
					
					if(transAmountIn > 0){
						uploadModel.setBankInOutCode("IN");
						uploadModel.setTransAmount(transAmountIn.longValue());
					}
					else{
						uploadModel.setBankInOutCode("OUT");
						uploadModel.setTransAmount(transAmountOut.longValue());
					}
					
					uploadModel.setBalance(balance.longValue());
					uploadModel.setBigo(bigo);
					uploadModel.setMemo(memo);
					uploadList.add(uploadModel); 
					
					System.out.println("trans data is " + uploadModel); 
					
				}						
			} //end of for loop  
			
			// upload list and commit 
			this.uploadData(response, baseMonth, uploadList);
			
			this.setResult(response, "Upload OK " );
				
		} 
		catch (FileUploadException e) {
//			sqlSession.rollback();
//			sqlSession.close();
			e.printStackTrace();
			setResult(response, "Error encountered while parsing the request");
		} 
		catch (Exception e) {
//			sqlSession.rollback();
//			sqlSession.close();
			e.printStackTrace();
			setResult(response, "Error encountered while uploading file");
		}		
	}

		
	public void uploadData(HttpServletResponse response, String baseMonth, List<BankUploadModel> list){

		System.out.println("upload excel data insert start "); 
		
		SqlSession sqlSession = DatabaseFactory.openSession();
		
		try{
			// 이전월의 모든 자료는 삭제한다. 
			sqlSession.delete("acc03_bank_upload.delete", baseMonth);
			
			IsNewData isNewData = new IsNewData(); 
			
			for(BankUploadModel uploadModel : list){
				Long key = isNewData.getSeq(sqlSession) ;
				System.out.println("key is " + key); 
				
				uploadModel.setKeyId(key); 	
				sqlSession.insert("acc03_bank_upload.insert", uploadModel);
			}
			
			sqlSession.commit();
			sqlSession.close();
		}
		catch(Exception e ) {
			System.out.println(e.getMessage()); 
			sqlSession.rollback();
			sqlSession.close();
			setResult(response, "delete or insert data error ");
		}
	}
	
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {

		HttpServletRequest request =  (HttpServletRequest)arg0 ;
		
		HttpServletResponse response = (HttpServletResponse)arg1; 
		response.setCharacterEncoding("UTF-8"); 
		
		String uploadType = request.getParameter("uploadType"); // image or file 
		
		if("file".equals(uploadType)){
			this.saveFile(request, response);
		}
		
		if("bankExcel".equals(uploadType)){
			this.bankUpload(request, response);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
	}


}
package myApp.server.file;
 
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import myApp.client.sys.model.FileModel;
import myApp.server.DatabaseFactory;
import myApp.server.data.IsNewData;

public class FileUpload implements javax.servlet.Servlet {
	
	private String getUploadPath(String fileId){
		return "D:\\WebFiles\\" + (Long.parseLong(fileId)/100) ;	
	}
	
	public void saveFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8"); // encoding 해주어야 한글메세지가 보인다. 
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

	public void bankUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// excel upload 
		response.setCharacterEncoding("UTF-8"); // encoding 해주어야 한글메세지가 보인다. 
		
		try{

			DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(1 * 1024 * 1024); // 1 MB
			ServletFileUpload serveltFileUpload = new ServletFileUpload(factory);
			FileItem fileItem = serveltFileUpload.parseRequest(request).get(0); // 처음 하나의 파일만 가져온다.  

			// 파일로 저장하지 않고 stream으로 파일을 읽는다. 저장할데가 없잖아.. 
			InputStream inputStream = fileItem.getInputStream();  
			
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);    

			//http://javaslave.tistory.com/78 에서 참조할 것. 
			
			HSSFSheet curSheet; 
			HSSFRow curRow; 
			// XSSFCell curCell; 
			
			for(int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++){
				
				curSheet = workbook.getSheetAt(sheetIndex);
				
				for(int rowIndex=0; rowIndex<curSheet.getPhysicalNumberOfRows(); rowIndex++){
					
					if(rowIndex != 0){
						
						curRow = curSheet.getRow(rowIndex);
						
						String var = curRow.getCell(0).getStringCellValue(); 
						
						if(var != null){
							System.out.println("sheet data is " + var); 
						}
						else {
							break; 
						}
					}
				}
				
			}
//			
//			String fileId = request.getParameter("fileId");
//
//			FileModel fileModel = new FileModel();
//			String fileName = fileItem.getName();	// file upload시에만 사용된다.
//			
//			if(fileId == null || "null".equals(fileId)){
//				
//				// get file id 
//				IsNewData isNewData = new IsNewData(); 
//				
//				Double size = Double.parseDouble((fileItem.getSize()/1024) + ""); 
//				fileModel.setSize(size); 
//				
//				fileModel.setDelDate(null); 
//				fileModel.setNote(null);
//				
//		 
//			}
//			 
//			File subDir  = new File(this.getUploadPath(fileId));
//	        if(!subDir.exists()) {
//	        	subDir.mkdir(); 
//	        }
//
//	        File file = new File(this.getUploadPath(fileId), fileId);
//    	    file.deleteOnExit(); // 있으면 먼저 지워라.
//            fileItem.write(file);
//            
////            sqlSession.commit();
////            sqlSession.close();
            
            setResult(response, "파일을 성공적으로 등록하였습니다.");
            
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

		
		
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		System.out.println("file upload start"); 

		HttpServletRequest request =  (HttpServletRequest)arg0 ; 
		String uploadType = request.getParameter("uploadType"); // image or file 
		
		if("file".equals(uploadType)){
			this.saveFile(request, (HttpServletResponse)arg1);
		}
		
		if("bankExcel".equals(uploadType)){
			this.bankUpload(request, (HttpServletResponse)arg1);
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
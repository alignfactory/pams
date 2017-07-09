package myApp.server.pdf;
 
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;

public class PDFLoader implements javax.servlet.Servlet {
	
	private void fileDownload(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, DocumentException {
		
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
		
		// pdf를 생성할 클래스를 지정한다. 반드시 className을 붙여주어야 한다. 
		String pdfName = "myApp.service." + request.getParameter("className"); 
		// System.out.println("pdf class name is " + pdfName);
		
		try {
			Class<?> loadClass = Class.forName(pdfName);
			Object executor = loadClass.newInstance(); 
			Method method 
		    	= executor.getClass().getMethod("getDocument", new Class[]{BufferedOutputStream.class, HttpServletRequest.class});
			
			method.invoke(executor, bufferedOutputStream, request);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.setHeader("Content-Type", "application/pdf"); //  image가 아닌경우 어떻하지? 
        response.setHeader("Content-disposition", "attachment;filename=Student.pdf");
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		
		System.out.println("pdf file download start"); 
		
		try {
			this.fileDownload((HttpServletRequest)arg0, (HttpServletResponse)arg1);
		} 
		catch (DocumentException e) {
			e.printStackTrace();
		} 
	}
	@Override
	public void destroy() { 
	}
	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
}
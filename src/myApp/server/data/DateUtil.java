package myApp.server.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	
	public static Date getDate(String dateString, String dateFormat){
		
    	SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
    	
    	Date date = null;
    	
		try {
			date = transFormat.parse(dateString);
		
		} catch (ParseException e) {
			date = new Date();  //오류인 경우 오늘일자를 보내준다. 
			e.printStackTrace();
		}

		return date; 
	}
	
	public static String getDate(Date date, String dateFormat){
		
    	SimpleDateFormat transFormat = new SimpleDateFormat(dateFormat);
    	String returnString = transFormat.format(date); 
		return returnString; 
	}

	
	public static Date getDate(String dateString){
		return getDate(dateString, "yyyy-MM-dd"); 
		
	}
}

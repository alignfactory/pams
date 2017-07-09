package myApp.service.tmc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
//import com.google.gson.Gson;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;

public class StatsRetrieve {

	private String mapperName = "tmc99_retrieve"; 
	
	public void selectByRetrieve(SqlSession sqlSession, ServiceRequest request, ServiceResult result){
		Date fromDate = request.getDate("fromDate");
		Date toDate = request.getDate("toDate");
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("fromDate", fromDate); 
		param.put("toDate", toDate);
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByRetrieve", param);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByStatic(SqlSession sqlSession, ServiceRequest request, ServiceResult result){
		Date fromDate = request.getDate("fromDate");
		Date toDate = request.getDate("toDate");
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("fromDate", fromDate); 
		param.put("toDate", toDate);
		
		List<Map<String, Object>>  list = sqlSession.selectList(mapperName + ".selectByStatic", param);

//		Gson gson = new Gson();
		String jsonString = ""; 
		
		for(Map<String, Object> data : list){
			System.out.println("company name is " + data.get("sys01_company_nm"));
//			jsonString = jsonString + gson.toJson(data);
		}
        
		String returnvalue = jsonString ;  
        System.out.println(returnvalue);
        result.setJosnResult(returnvalue);
        
	}
	
	
}

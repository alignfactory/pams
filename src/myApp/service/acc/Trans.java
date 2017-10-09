package myApp.service.acc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.acc.model.TransModel;
//import myApp.client.acc.model.AccountModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class Trans {

	private String mapperName  = "acc06_trans"; 
	
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long transId = request.getLong("transId"); 
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectById", transId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByTransDate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) throws ParseException {
		Long companyId = request.getLong("companyId");
		String baseMonth = request.getString("baseMonth"); 
		
		baseMonth = baseMonth.replace("-", ""); 
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date startDate = dateFormat.parse(baseMonth + "01"); 
		
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(startDate);

		Date endDate = dateFormat.parse(baseMonth + cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("companyId", companyId);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		
		System.out.println("inOutCode is " + request.getString("inOutCode")); 
		param.put("inOutCode", request.getString("inOutCode")); 
		
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByTransDate", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	
	public void loadTrans(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLong("companyId");
		String baseMonth = request.getString("baseMonth"); 
		baseMonth = baseMonth.replace("-", ""); 
		String inOutCode = request.getString("inOutCode"); 
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("companyId", companyId);
		param.put("baseMonth", baseMonth);
		param.put("inOutCode", inOutCode);
		System.out.println("inoutcode is " + inOutCode); 
		
		
		sqlSession.selectOne(mapperName + ".loadTrans", param);
		
		System.out.println("return code is " + param.get("returnCode")); 
		System.out.println("return code is " + param.get("returnMsg"));
		
		int returnCode = (int) param.get("returnCode");
		result.setStatus(returnCode);
		
		String returnMsg = (String)param.get("returnMsg");
		result.setMessage(returnMsg);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<TransModel> updateModel = new UpdateDataModel<TransModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<TransModel> updateModel = new UpdateDataModel<TransModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}

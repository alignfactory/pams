package myApp.service.acc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.acc.model.AccountModel;
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
		
		
		System.out.println("companyId is " + companyId);
		System.out.println("baseMonth is " + baseMonth);
		System.out.println("start date is " + startDate); 
		System.out.println("end date is " + endDate);
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("companyId", companyId);
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByTransDate", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	
	public void loadTrans(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLong("companyId");
		String baseMonth = request.getString("baseMonth"); 
		baseMonth = baseMonth.replace("-", ""); 
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("companyId", companyId);
		param.put("baseMonth", baseMonth);
		
		sqlSession.selectOne(mapperName + ".loadTrans", param);
		
		System.out.println("return code is " + param.get("returnCode")); 
		System.out.println("return code is " + param.get("returnMsg"));
		
		int returnCode = (int) param.get("returnCode");
		result.setStatus(returnCode);
		
		String returnMsg = (String)param.get("returnMsg");
		result.setMessage(returnMsg);
		
		
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<AccountModel> updateModel = new UpdateDataModel<AccountModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<AccountModel> updateModel = new UpdateDataModel<AccountModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}

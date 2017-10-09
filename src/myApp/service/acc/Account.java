package myApp.service.acc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.acc.model.AccountModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class Account {

	private String mapperName  = "acc02_account"; 
	
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long seasionId = request.getLong("accountId"); 
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectById", seasionId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByEduOfficeCode(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String eduOfficeCode = request.getString("eduOfficeCode"); 
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByEduOfficeCode", eduOfficeCode);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", request.getLong("companyId")); 
		param.put("eduOfficeCode", request.getString("eduOfficeCode")); 
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByCompanyId", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByComboBox(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLong("companyId");
		String baseMonth = request.getString("baseMonth"); 

		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", companyId);
		param.put("baseMonth", baseMonth);
		param.put("inOutCode", request.getString("inOutCode"));
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByComboBox", param);
		
		System.out.println("combo count is " + list.size()); 
		
		result.setRetrieveResult(1, "select ok", list);
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

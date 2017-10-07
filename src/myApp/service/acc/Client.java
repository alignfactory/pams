package myApp.service.acc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class Client {

	private String mapperName  = "acc04_client"; 
	
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long id = request.getLong("clientId"); 
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectById", id);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLong("companyId"); 
		
		System.out.println("company id is " + companyId);
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByCompanyId", companyId);
		
		System.out.println("client count is  " + list.size());
		
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByClientName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long companyId = request.getLong("companyId");
		String clientName = request.getString("clientName"); 
		
		Map<String, Object> param = new HashMap<String, Object>(); 
		param.put("companyId", companyId); 
		param.put("clientName", clientName);
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByClientName", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<AbstractDataModel> updateModel = new UpdateDataModel<AbstractDataModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<AbstractDataModel> updateModel = new UpdateDataModel<AbstractDataModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}

package myApp.service.acc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class BankUpload {

	private String mapperName = "acc03_bank_upload"; 
	
	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long seasionId = request.getLong("bankUploadId"); 
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectById", seasionId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByBaseMonth(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>(); 
		
		param.put("companyId", request.getLong("companyId")); 
		param.put("baseMonth", request.getString("baseMonth"));
		
		String bankInOutCode = request.getString("bankInOutCode"); 
		if(bankInOutCode == null){
			bankInOutCode = "%"; 
		}
		param.put("bankInOutCode", bankInOutCode);
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByBaseMonth", param);
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

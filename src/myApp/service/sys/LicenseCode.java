package myApp.service.sys;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.sys.model.LicenseCodeModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class LicenseCode {

	private String mapperName  = "sys21_license_code"; 
	
	public void selectByName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String licenseName = request.getString("licenseName"); 
		if(licenseName != null){
			licenseName = "%" + licenseName + "%";
		}
		else {
			licenseName = "%"; 
		}
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByName", licenseName);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void selectByApplyDate(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applyDate", Calendar.getInstance().getTime()); // system date 
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByCodeKindId", param);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<LicenseCodeModel> updateModel = new UpdateDataModel<LicenseCodeModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<LicenseCodeModel> updateModel = new UpdateDataModel<LicenseCodeModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}

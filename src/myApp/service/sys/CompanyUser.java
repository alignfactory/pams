package myApp.service.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.sys.model.CompanyUserModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class CompanyUser { 
	
	String mapperName = "sys03_company_user"; 
	
	public void selectByUserId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long userId = request.getLong("userId"); 
		List<AbstractDataModel> userCompanyList = sqlSession.selectList(mapperName + ".selectByUserId", userId) ;
		result.setRetrieveResult(1, "사용자별 고객권한 조회", userCompanyList);
	}
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<CompanyUserModel> updateModel = new UpdateDataModel<CompanyUserModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}
	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<CompanyUserModel> updateModel = new UpdateDataModel<CompanyUserModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
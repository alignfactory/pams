package myApp.service.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.sys.model.CompanyUserModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class Manager { 
	
	String mapperName = "sys07_manager"; 
	
	public void selectByUserId(SqlSession sqlSession, ServiceRequest request,ServiceResult result) {

		Long userId = request.getLong("userId"); 
		System.out.println("user id is " + userId); 
		
		List<AbstractDataModel> managerList = sqlSession.selectList(mapperName + ".selectByUserId", userId) ;
		result.setRetrieveResult(1, "사용자별 매니저정보조회", managerList);
	}

	public void selectByCompanyId(SqlSession sqlSession, ServiceRequest request,ServiceResult result) {

		Long companyId = request.getLong("companyId"); 
		System.out.println("company id is " + companyId); 
		
		List<AbstractDataModel> managerList = sqlSession.selectList(mapperName + ".selectByCompanyId", companyId) ;
		result.setRetrieveResult(1, "회사별 매니저정보조회", managerList);
	}
	
	public void insert(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		
		UpdateDataModel<CompanyUserModel> updateModel = new UpdateDataModel<CompanyUserModel>();
		
		// 사용자의 의한 자료변경 없이 즉시 데이타베이스에 등록하여야 하는 경우 사용한다.  
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<CompanyUserModel> updateModel = new UpdateDataModel<CompanyUserModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
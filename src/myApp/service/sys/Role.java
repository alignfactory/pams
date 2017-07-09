package myApp.service.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.sys.model.RoleModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class Role { 
	
	String mapperName = "sys04_role"; 
	
	public void selectByAll(SqlSession sqlSession, ServiceRequest request,
			ServiceResult result) {

		List<AbstractDataModel> roleList = sqlSession.selectList(mapperName + ".selectByAll") ; 
		result.setRetrieveResult(roleList.size(), "role list select ok", roleList);
	}

	public void selectByNotAssigned(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long userId = request.getLong("userId"); 
		
		System.out.println("userId is " + userId); 
		
		List<AbstractDataModel> userRoleList = sqlSession.selectList(mapperName + ".selectByNotAssigned", userId) ;
		result.setRetrieveResult(userRoleList.size(), "미등록 권한정보 조회", userRoleList);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<RoleModel> updateModel = new UpdateDataModel<RoleModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<RoleModel> updateModel = new UpdateDataModel<RoleModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}
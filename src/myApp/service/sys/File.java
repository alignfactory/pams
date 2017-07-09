package myApp.service.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.sys.model.CompanyModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class File {

	private String mapperName = "sys10_file"; 
	
	public void selectByAll(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByAll");
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectById(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long fileId = request.getLong("fileId"); 
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByParentId",  fileId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByParentId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long parentId = request.getLong("parentId"); 
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByParentId",  parentId);
		result.setRetrieveResult(1, "select ok", list);
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<CompanyModel> updateModel = new UpdateDataModel<CompanyModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<CompanyModel> updateModel = new UpdateDataModel<CompanyModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}

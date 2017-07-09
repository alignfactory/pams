package myApp.service.psc;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.psc.model.ClassStudentModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class ClassStudent {

	private String mapperName  = "psc04_class_student"; 
	
	public void selectByStudentId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long studentId = request.getLong("studentId"); 
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByStudentId", studentId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<ClassStudentModel> updateModel = new UpdateDataModel<ClassStudentModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<ClassStudentModel> updateModel = new UpdateDataModel<ClassStudentModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}


}

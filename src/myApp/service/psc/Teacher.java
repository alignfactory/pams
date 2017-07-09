package myApp.service.psc;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import myApp.client.psc.model.TeacherModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class Teacher {

	private String mapperName  = "psc02_teacher"; 
	
	public void selectByStudyClassId(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		Long studyClassId = request.getLong("studyClassId"); 
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByStudyClassId", studyClassId);
		result.setRetrieveResult(1, "select ok", list);
	}

	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		System.out.println("teacher update"); 
		UpdateDataModel<TeacherModel> updateModel = new UpdateDataModel<TeacherModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 사용중인 코드인지 체크 로직 추가 필요.  
		UpdateDataModel<TeacherModel> updateModel = new UpdateDataModel<TeacherModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}

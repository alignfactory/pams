package myApp.service.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;

public class ColComments { 
	
	String mapperName = "sys00_ColComments"; 
	
	public void selectByTableName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	
		System.out.println("sys00_ColComments param: " + request.getString("tableName") ); 
		
		String tableName = request.getString("tableName");
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByTableName", tableName) ;
		
		System.out.println("sys00_ColComments size: " + list.size() );
		
		result.setRetrieveResult(1, "select ok", list);
	}

}
package myApp.service.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;

public class TabComments { 
	
	private	String mapperName = "sys00_TabComments"; 
	
	public void selectByTableName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
	
		System.out.println("sys00_TabComments param: " + request.getString("tableName") ); 
		
		String tableName = request.getString("tableName");
		
		List<AbstractDataModel> list = sqlSession.selectList(mapperName + ".selectByTableName", tableName) ;
		
		System.out.println("sys00_TabComments size: " + list.size() );
		
		result.setRetrieveResult(1, "select ok", list);
	}

	public void selectByColumnName(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		String tableName = request.getString("tableName"); 
		List<AbstractDataModel> List = sqlSession.selectList(mapperName + ".selectByColumnName", tableName) ;
		result.setRetrieveResult(1, "select ok", List);
	}

}

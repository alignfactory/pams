package myApp.service.sys;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import myApp.client.sys.model.RoleModel;
import myApp.client.sys.model.MenuModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;
import myApp.server.data.UpdateDataModel;

public class Menu { 
	
	String mapperName = "sys06_menu"; 
	
	
	public void selectByAll(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		List<AbstractDataModel> roleObjectList 
			= sqlSession.selectList(mapperName + ".selectByParentId", Long.parseLong("0"));

		for(AbstractDataModel child : roleObjectList){
			MenuModel menuModel = (MenuModel)child;
			List<AbstractDataModel> childList = getChildItem(sqlSession, menuModel.getMenuId());  
			menuModel.setChildList(childList); 	
		}
		result.setRetrieveResult(roleObjectList.size(), "하위메뉴 조회", roleObjectList);
	}
	
	private List<AbstractDataModel> getChildItem(SqlSession sqlSession, Long parentId){
		
		List<AbstractDataModel> roleObjectList = sqlSession.selectList(mapperName + ".selectByParentId", parentId);

		for(AbstractDataModel child : roleObjectList){
			MenuModel menuModel = (MenuModel)child;
			List<AbstractDataModel> childList = getChildItem(sqlSession, menuModel.getMenuId());  
			menuModel.setChildList(childList); 	
		}

		return roleObjectList ; 
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<MenuModel> updateModel = new UpdateDataModel<MenuModel>(); 
		updateModel.updateModel(sqlSession, request.getList(), mapperName, result);
	}

	public void delete(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		UpdateDataModel<RoleModel> updateModel = new UpdateDataModel<RoleModel>(); 
		updateModel.deleteModel(sqlSession, request.getList(), mapperName, result);
	}
}

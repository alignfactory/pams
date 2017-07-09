package myApp.service.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import myApp.client.sys.model.RoleMenuModel;
import myApp.frame.service.ServiceRequest;
import myApp.frame.service.ServiceResult;
import myApp.frame.ui.AbstractDataModel;

public class RoleMenu { 
	
	String mapperName = "sys07_role_menu"; 
	
	public void selectByUserId(SqlSession sqlSession, ServiceRequest request,
			ServiceResult result) {

		Long userId = request.getLong("userId");
		List<AbstractDataModel> roleMenuList = getChildByUserId(sqlSession, userId, Long.parseLong("0")); 
		result.setRetrieveResult(roleMenuList.size(), "사용자별 메뉴 조회", roleMenuList);
	}

	private List<AbstractDataModel> getChildByUserId(SqlSession sqlSession, Long userId, Long parentId ){

		Map<String, Long> param = new HashMap<String, Long>(); 
		param.put("userId", userId); 
		param.put("parentId", parentId); 
		
		List<AbstractDataModel> roleMenuList 
			= sqlSession.selectList(this.mapperName + ".selectByUserId", param);
		
		for(AbstractDataModel child : roleMenuList){
			RoleMenuModel roleMenuModel = (RoleMenuModel)child;
			List<AbstractDataModel> childList = getChildByUserId(sqlSession, userId, roleMenuModel.getMenuId());  
			roleMenuModel.setChildList(childList); 	
		}
		return roleMenuList ; 
	}
	
	public void selectByCheckedRoot(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {
		// 전체 메뉴 리스트에 해당 role에서 사용할 수 있는 정보를 추가로 받는다. 
		Long roleId = request.getLong("roleId");
		List<AbstractDataModel> roleObjectList = getCheckedChild(sqlSession, Long.parseLong("0"), roleId); 
		result.setRetrieveResult(roleObjectList.size(), "하위메뉴 조회", roleObjectList);
	}
	
	private List<AbstractDataModel> getCheckedChild(SqlSession sqlSession, Long parentId, Long roleId ){

		Map<String, Long> param = new HashMap<String, Long>(); 
		param.put("parentId", parentId); 
		param.put("roleId", roleId); 
		
		List<AbstractDataModel> roleObjectList 
			= sqlSession.selectList(mapperName + ".selectByCheckedRole", param);
		
		for(AbstractDataModel child : roleObjectList){
			RoleMenuModel roleMenuModel = (RoleMenuModel)child;
			List<AbstractDataModel> childList = getCheckedChild(sqlSession, roleMenuModel.getMenuId(), roleId);  
			roleMenuModel.setChildList(childList); 	
		}

		return roleObjectList ; 
	}
	
	public void update(SqlSession sqlSession, ServiceRequest request, ServiceResult result) {

		Long roleId = request.getLong("roleId");
		System.out.println("role id is " + roleId); 
		
		
		List <AbstractDataModel> list = request.getList();  
		List<AbstractDataModel> updateList = new ArrayList<AbstractDataModel>(); 
		
		for(AbstractDataModel data : list){
			
			RoleMenuModel roleMenuModel = (RoleMenuModel)data; 
			Map<String, Object> updateModel = new HashMap<String, Object>();
			
			if(roleMenuModel.getRoleMenuId() == null){
				// insert role menu 
				roleMenuModel.setRoleMenuId(sqlSession.selectOne("getSeq"));
				roleMenuModel.setRoleId(roleId);	

				updateModel.put("roleMenuId", roleMenuModel.getRoleMenuId()); 
				updateModel.put("roleId", roleMenuModel.getRoleId());
				updateModel.put("menuId", roleMenuModel.getMenuId());
				updateModel.put("useYn", roleMenuModel.getUseYn());
				updateModel.put("note", roleMenuModel.getMenuName());
				
				sqlSession.insert(this.mapperName + ".insert", updateModel);
			}
			else {
				// update 
				updateModel.put("roleMenuId", roleMenuModel.getRoleMenuId()); 
				updateModel.put("roleId", roleMenuModel.getRoleId());
				updateModel.put("menuId", roleMenuModel.getMenuId());
				updateModel.put("useYn", roleMenuModel.getUseYn());
				updateModel.put("note", roleMenuModel.getMenuName());
				
				sqlSession.update(this.mapperName + ".update", updateModel);
			}
			updateList.add(sqlSession.selectOne(mapperName + ".selectById", roleMenuModel.getRoleMenuId())); 
			
		}
		result.setRetrieveResult(1, "update success!", updateList); 
	}
}

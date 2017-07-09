package myApp.client.sys.model;

import java.util.List;

import myApp.frame.ui.AbstractDataModel;

public class RoleMenuModel extends AbstractDataModel {
	
	private Long 	menuId;
	private Long 	parentId;
	private String 	hiddenYn;
	private String 	menuName; 
	private String 	className;
	private String 	seq;
	private String 	note;
	
	// sys07_role_menu columns...
	private Long 	roleMenuId; 
	private Long 	roleId; 
	private String 	useYn; 
	
	private List<AbstractDataModel> childList;
	
	
	@Override
	public void setKeyId(Long id) {
		this.setMenuId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getMenuId();
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getHiddenYn() {
		return hiddenYn;
	}

	public void setHiddenYn(String hiddenYn) {
		this.hiddenYn = hiddenYn;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getRoleMenuId() {
		return roleMenuId;
	}

	public void setRoleMenuId(Long roleMenuId) {
		this.roleMenuId = roleMenuId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public Boolean getUseYnFlag() {
		return "true".equals(useYn) ; 
	} 
	public void setUseYnFlag(Boolean useYnFlag) {
		this.useYn = useYnFlag.toString();
	}

	public List<AbstractDataModel> getChildList() {
		return childList;
	}

	public void setChildList(List<AbstractDataModel> childList) {
		this.childList = childList;
	}


}

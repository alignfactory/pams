package myApp.client.sys.model;

import java.util.List;

import myApp.frame.ui.AbstractDataModel;

public class MenuModel extends AbstractDataModel {
	private Long 	menuId;
	private Long 	parentId ;
	private String 	hiddenYn;
	private String 	menuName; 
	private String 	className;
	private String 	seq;
	private String 	note;
	
	private List<AbstractDataModel> childList;
	
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

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public MenuModel(){
	}
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public List<AbstractDataModel> getChildList() {
		return childList;
	}
	
	public void setChildList(List<AbstractDataModel> childList) {
		this.childList = childList;
	}

	@Override
	public void setKeyId(Long id) {
		this.setMenuId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getMenuId();
	}

	public String getHiddenYn() {
		return hiddenYn;
	}

	public void setHiddenYn(String hiddenYn) {
		this.hiddenYn = hiddenYn;
	}

	public Boolean getHiddenYnFlag() {
		return "true".equals(hiddenYn) ; 
	} 
	public void setHiddenYnFlag(Boolean hiddenYnFlag) {
		this.hiddenYn = hiddenYnFlag.toString();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}

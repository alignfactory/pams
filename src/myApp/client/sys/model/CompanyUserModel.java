package myApp.client.sys.model;

import myApp.frame.ui.AbstractDataModel;

public class CompanyUserModel extends AbstractDataModel {

	private Long companyUserId;
	private Long userId;
	private Long companyId;
	private String note; 
	private CompanyModel companyModel = new CompanyModel(); 
	
	@Override
	public void setKeyId(Long id) {
		this.setCompanyUserId(id);;
	}
	@Override
	public Long getKeyId() {
		return this.getCompanyUserId();
	}
	public Long getCompanyUserId() {
		return companyUserId;
	}
	public void setCompanyUserId(Long companyUserId) {
		this.companyUserId = companyUserId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public CompanyModel getCompanyModel() {
		return companyModel;
	}
	public void setCompanyModel(CompanyModel companyModel) {
		this.companyModel = companyModel;
	}
}

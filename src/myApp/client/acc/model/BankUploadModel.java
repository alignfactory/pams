package myApp.client.acc.model;

import java.util.Date;

import myApp.frame.ui.AbstractDataModel;

public class BankUploadModel extends AbstractDataModel {
	
	private Long  bankUploadId;
	private Long companyId; 
	private String bankInOutCode; 
	private String bankInOutName; 
	private Long transNo; 
	private Date transDate; 
	private String transTime; 
	private String transName; 
	private Long transAmount;
	private Long balance;
	private String bigo;
	private String memo;
	private String note;
	
	@Override
	public void setKeyId(Long id) {
		this.setBankUploadId(id);
	}
	@Override
	public Long getKeyId() {
		return this.getBankUploadId(); 
	}
	public Long getBankUploadId() {
		return bankUploadId;
	}
	public void setBankUploadId(Long bankUploadId) {
		this.bankUploadId = bankUploadId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getBankInOutCode() {
		return bankInOutCode;
	}
	public void setBankInOutCode(String bankInOutCode) {
		this.bankInOutCode = bankInOutCode;
	}
	public String getBankInOutName() {
		return bankInOutName;
	}
	public void setBankInOutName(String bankInOutName) {
		this.bankInOutName = bankInOutName;
	}
	public Long getTransNo() {
		return transNo;
	}
	public void setTransNo(Long transNo) {
		this.transNo = transNo;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public Long getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Long transAmount) {
		this.transAmount = transAmount;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public String getBigo() {
		return bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	

	
}

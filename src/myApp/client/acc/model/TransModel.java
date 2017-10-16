package myApp.client.acc.model;

import java.util.Date;

import myApp.frame.ui.AbstractDataModel;

public class TransModel extends AbstractDataModel {
	
	private Long transId;
	private Long companyId;
	private String inExpCode;
	private String inExpName;
	private Date transDate;
	private String transName;
	
	private Long accountId; // 계정정보 
	private String gmokCode;
	private String smokCode;
	private String accountName;
	
	private Long clientId; // 거래처 정보 
	private String bizNo;
	private String clientName;
	private Long transAmount;
	private String bankCode;
	private String accountNo;
	private String accountOwner;

	private Date accountDate;
	private String taxApplyYn;
	private Long supplyAmount;
	private Long taxAmount;
	private String descript;
	private Date chargeDate;
	private String note;
	
	private String gwanName;
	private String hangName;
	private String gmokName;
	private String ceoName;

	private String zipCode;
	private String zipAddr;
	private String zipDetail;
	
	@Override
	public void setKeyId(Long id) {
		this.setTransId(id);
	}
	@Override
	public Long getKeyId() {
		return this.getTransId(); 
	}
	
	public Long getTransId() {
		return transId;
	}
	public void setTransId(Long transId) {
		this.transId = transId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getInExpCode() {
		return inExpCode;
	}
	public void setInExpCode(String inExpCode) {
		this.inExpCode = inExpCode;
	}
	public String getInExpName() {
		return inExpName;
	}
	public void setInExpName(String inExpName) {
		this.inExpName = inExpName;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public String getTransName() {
		return transName;
	}
	public void setTransName(String transName) {
		this.transName = transName;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getGmokCode() {
		return gmokCode;
	}
	public void setGmokCode(String gmokCode) {
		this.gmokCode = gmokCode;
	}
	public String getSmokCode() {
		return smokCode;
	}
	public void setSmokCode(String smokCode) {
		this.smokCode = smokCode;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getBizNo() {
		return bizNo;
	}
	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Long getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Long transAmount) {
		this.transAmount = transAmount;
	}
	public Date getAccountDate() {
		return accountDate;
	}
	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}
	public String getTaxApplyYn() {
		return taxApplyYn;
	}
	public void setTaxApplyYn(String taxApplyYn) {
		this.taxApplyYn = taxApplyYn;
	}
	public Long getSupplyAmount() {
		return supplyAmount;
	}
	public void setSupplyAmount(Long supplyAmount) {
		this.supplyAmount = supplyAmount;
	}
	public Long getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Long taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public Date getChargeDate() {
		return chargeDate;
	}
	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCeoName() {
		return ceoName;
	}
	public void setCeoName(String ceoName) {
		this.ceoName = ceoName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getHangName() {
		return hangName;
	}
	public void setHangName(String hangName) {
		this.hangName = hangName;
	}
	public String getGmokName() {
		return gmokName;
	}
	public void setGmokName(String gmokName) {
		this.gmokName = gmokName;
	}
	public String getGwanName() {
		return gwanName;
	}
	public void setGwanName(String gwanName) {
		this.gwanName = gwanName;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getZipAddr() {
		return zipAddr;
	}
	public void setZipAddr(String zipAddr) {
		this.zipAddr = zipAddr;
	}
	public String getZipDetail() {
		return zipDetail;
	}
	public void setZipDetail(String zipDetail) {
		this.zipDetail = zipDetail;
	}

}

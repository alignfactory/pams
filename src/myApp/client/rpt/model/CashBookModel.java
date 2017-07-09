package myApp.client.rpt.model;

import java.util.Date;

import myApp.frame.ui.AbstractDataModel;

public class CashBookModel extends AbstractDataModel {

	private	Long	RowNo;	
	private	Long	companyId;	
	private	String	yearMonth;	
	private	Date	transDate;	
	private	String	accountName;	
	private	String	transDescript;	
	private	Long	inAmount;	
	private	Long	outAmonut;	
	private	Long	sumAmount;	

	public CashBookModel(){
	}

	@Override
	public void setKeyId(Long id) {
		this.setRowNo(id);
	}

	@Override
	public Long getKeyId() {
		return this.getRowNo(); 
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getInAmount() {
		return inAmount;
	}

	public void setInAmount(Long inAmount) {
		this.inAmount = inAmount;
	}

	public Long getOutAmonut() {
		return outAmonut;
	}

	public void setOutAmonut(Long outAmonut) {
		this.outAmonut = outAmonut;
	}

	public Long getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(Long sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public Long getRowNo() {
		return RowNo;
	}

	public void setRowNo(Long rowNo) {
		RowNo = rowNo;
	}

	public String getTransDescript() {
		return transDescript;
	}

	public void setTransDescript(String transDescript) {
		this.transDescript = transDescript;
	}

}


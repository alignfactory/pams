package myApp.client.rpt.model;

import myApp.frame.ui.AbstractDataModel;

public class GeneralLedgerModel extends AbstractDataModel {

	private	Long	RowNo;	
	private	String	gwonCode;	
	private	String	gwonName;	
	private	String	hangCode;	
	private	String	hangName;	
	private	String	gmokCode;	
	private	String	gmokName;
	private	String	yearMonth;	
	private	Long	transAmount;	

	public GeneralLedgerModel(){
	}

	@Override
	public void setKeyId(Long id) {
		this.setRowNo(id);
	}

	@Override
	public Long getKeyId() {
		return this.getRowNo(); 
	}

	public String getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	public Long getRowNo() {
		return RowNo;
	}

	public void setRowNo(Long rowNo) {
		RowNo = rowNo;
	}

	public String getGwonCode() {
		return gwonCode;
	}

	public void setGwonCode(String gwonCode) {
		this.gwonCode = gwonCode;
	}

	public String getGwonName() {
		return gwonName;
	}

	public void setGwonName(String gwonName) {
		this.gwonName = gwonName;
	}

	public String getHangCode() {
		return hangCode;
	}

	public void setHangCode(String hangCode) {
		this.hangCode = hangCode;
	}

	public String getHangName() {
		return hangName;
	}

	public void setHangName(String hangName) {
		this.hangName = hangName;
	}

	public String getGmokCode() {
		return gmokCode;
	}

	public void setGmokCode(String gmokCode) {
		this.gmokCode = gmokCode;
	}

	public String getGmokName() {
		return gmokName;
	}

	public void setGmokName(String gmokName) {
		this.gmokName = gmokName;
	}

	public Long getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Long transAmount) {
		this.transAmount = transAmount;
	}

}


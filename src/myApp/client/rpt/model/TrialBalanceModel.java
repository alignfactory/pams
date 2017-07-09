package myApp.client.rpt.model;

import myApp.frame.ui.AbstractDataModel;

public class TrialBalanceModel extends AbstractDataModel {

	private	Long	RowNo;	
	private	Long	inBalanceSum;	
	private	Long	inAccumulatedSum;	
	private	Long	inAmounts;	
	private	Long	inBudgetAmount;	
	private	String	boldGb;	
	private	String	accountCd;	
	private	String	subCd;	
	private	String	accountPrtNm;	
	private	Long	outBudgetAmount;	
	private	Long	outAmonut;	
	private	Long	outAccumulatedSum;	
	private	Long	outAalanceSum;	

	public TrialBalanceModel(){
	}

	@Override
	public void setKeyId(Long id) {
		this.setRowNo(id);
	}

	@Override
	public Long getKeyId() {
		return this.getRowNo(); 
	}

	public Long getRowNo() {
		return RowNo;
	}

	public void setRowNo(Long rowNo) {
		RowNo = rowNo;
	}

	public Long getInBalanceSum() {
		return inBalanceSum;
	}

	public void setInBalanceSum(Long inBalanceSum) {
		this.inBalanceSum = inBalanceSum;
	}

	public Long getInAccumulatedSum() {
		return inAccumulatedSum;
	}

	public void setInAccumulatedSum(Long inAccumulatedSum) {
		this.inAccumulatedSum = inAccumulatedSum;
	}

	public Long getInAmounts() {
		return inAmounts;
	}

	public void setInAmounts(Long inAmounts) {
		this.inAmounts = inAmounts;
	}

	public Long getInBudgetAmount() {
		return inBudgetAmount;
	}

	public void setInBudgetAmount(Long inBudgetAmount) {
		this.inBudgetAmount = inBudgetAmount;
	}

	public String getBoldGb() {
		return boldGb;
	}

	public void setBoldGb(String boldGb) {
		this.boldGb = boldGb;
	}

	public String getAccountCd() {
		return accountCd;
	}

	public void setAccountCd(String accountCd) {
		this.accountCd = accountCd;
	}

	public String getSubCd() {
		return subCd;
	}

	public void setSubCd(String subCd) {
		this.subCd = subCd;
	}

	public String getAccountPrtNm() {
		return accountPrtNm;
	}

	public void setAccountPrtNm(String accountPrtNm) {
		this.accountPrtNm = accountPrtNm;
	}

	public Long getOutBudgetAmount() {
		return outBudgetAmount;
	}

	public void setOutBudgetAmount(Long outBudgetAmount) {
		this.outBudgetAmount = outBudgetAmount;
	}

	public Long getOutAmonut() {
		return outAmonut;
	}

	public void setOutAmonut(Long outAmonut) {
		this.outAmonut = outAmonut;
	}

	public Long getOutAccumulatedSum() {
		return outAccumulatedSum;
	}

	public void setOutAccumulatedSum(Long outAccumulatedSum) {
		this.outAccumulatedSum = outAccumulatedSum;
	}

	public Long getOutAalanceSum() {
		return outAalanceSum;
	}

	public void setOutAalanceSum(Long outAalanceSum) {
		this.outAalanceSum = outAalanceSum;
	}

}


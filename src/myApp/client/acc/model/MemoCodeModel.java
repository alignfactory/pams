package myApp.client.acc.model;

import myApp.frame.ui.AbstractDataModel;
import java.util.Date;

public class MemoCodeModel extends AbstractDataModel {

		private	Long		memoId;
		private	Long		companyId;
		private	String		memoCode;
		private	String		acctCode;
		private	String		subCode;
		private	String		memoDscr;
		private	String		acctName;


	public MemoCodeModel(){
	}

	@Override
	public void setKeyId(Long id) {
		this.setMemoId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getMemoId(); 
	}

	public	Long getMemoId() {
		return	memoId;
	}

	public void setMemoId(Long memoId) {
		this.memoId = memoId;
	}

	public	Long getCompanyId() {
		return	companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public	String getMemoCode() {
		return	memoCode;
	}

	public void setMemoCode(String memoCode) {
		this.memoCode = memoCode;
	}

	public	String getAcctCode() {
		return	acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public	String getSubCode() {
		return	subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public	String getMemoDscr() {
		return	memoDscr;
	}

	public void setMemoDscr(String memoDscr) {
		this.memoDscr = memoDscr;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}



}


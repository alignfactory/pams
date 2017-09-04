package myApp.client.acc.model;

import myApp.frame.ui.AbstractDataModel;
import java.util.Date;

public class FillerStringModel extends AbstractDataModel {

		private	Long		fillerId;
		private	Long		companyId;
		private	String		replaceDscr;
		private	String		seqOrder;


	public FillerStringModel(){
	}

	@Override
	public void setKeyId(Long id) {
		this.setFillerId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getFillerId(); 
	}

	public	Long getFillerId() {
		return	fillerId;
	}

	public void setFillerId(Long fillerId) {
		this.fillerId = fillerId;
	}

	public	Long getCompanyId() {
		return	companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public	String getReplaceDscr() {
		return	replaceDscr;
	}

	public void setReplaceDscr(String replaceDscr) {
		this.replaceDscr = replaceDscr;
	}

	public	String getSeqOrder() {
		return	seqOrder;
	}

	public void setSeqOrder(String seqOrder) {
		this.seqOrder = seqOrder;
	}



}


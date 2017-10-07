package myApp.client.acc.model;

import myApp.frame.ui.AbstractDataModel;

public class FilterModel extends AbstractDataModel {

		private	Long		filterId;
		private	Long		companyId;
		private	String		replaceDscr;
		private	String		seqOrder;


	public FilterModel(){
	}

	@Override
	public void setKeyId(Long id) {
		this.setFilterId(id);
	}

	@Override
	public Long getKeyId() {
		return this.getFilterId(); 
	}

	public	Long getFilterId() {
		return	filterId;
	}

	public void setFilterId(Long filterId) {
		this.filterId = filterId;
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


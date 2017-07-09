package myApp.client.sys.model;

import myApp.frame.ui.AbstractDataModel;

public class TabCommentsModel extends AbstractDataModel {

	private Long	tableId; 
	private	String	tableName ;
	private	String	comments;
	private	String	tablespaceName;
	private	Long	pctFree;
	private	Long	pctUsed;
	private	Long	iniTrans;
	private	Long	maxTrans;
	private	Long	pctIncrease;
	private	Long	freeLists;
	private	Long	numRows;
	private	Long	blocks;
	private	Long	emptyBlocks;
	private	Long	chainCnt;
	private	Long	avgRowLen;
	private	String	initialExtent;
	private	String	nextExtent;
	private	String	cache;
	private	String	tableLock;

	public TabCommentsModel(){
	}

	@Override
	public void setKeyId(Long id) {
		this.setTableId(id);
		
	}

	@Override
	public Long getKeyId() {
		return this.getTableId();
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTablespaceName() {
		return tablespaceName;
	}

	public void setTablespaceName(String tablespaceName) {
		this.tablespaceName = tablespaceName;
	}

	public Long getPctFree() {
		return pctFree;
	}

	public void setPctFree(Long pctFree) {
		this.pctFree = pctFree;
	}

	public Long getPctUsed() {
		return pctUsed;
	}

	public void setPctUsed(Long pctUsed) {
		this.pctUsed = pctUsed;
	}

	public Long getIniTrans() {
		return iniTrans;
	}

	public void setIniTrans(Long iniTrans) {
		this.iniTrans = iniTrans;
	}

	public Long getMaxTrans() {
		return maxTrans;
	}

	public void setMaxTrans(Long maxTrans) {
		this.maxTrans = maxTrans;
	}

	public Long getPctIncrease() {
		return pctIncrease;
	}

	public void setPctIncrease(Long pctIncrease) {
		this.pctIncrease = pctIncrease;
	}

	public Long getFreeLists() {
		return freeLists;
	}

	public void setFreeLists(Long freeLists) {
		this.freeLists = freeLists;
	}

	public Long getNumRows() {
		return numRows;
	}

	public void setNumRows(Long numRows) {
		this.numRows = numRows;
	}

	public Long  getBlocks() {
		return blocks;
	}

	public void setBlocks(Long blocks) {
		this.blocks = blocks;
	}

	public Long getEmptyBlocks() {
		return emptyBlocks;
	}

	public void setEmptyBlocks(Long emptyBlocks) {
		this.emptyBlocks = emptyBlocks;
	}

	public Long getChainCnt() {
		return chainCnt;
	}

	public void setChainCnt(Long chainCnt) {
		this.chainCnt = chainCnt;
	}

	public Long getAvgRowLen() {
		return avgRowLen;
	}

	public void setAvgRowLen(Long avgRowLen) {
		this.avgRowLen = avgRowLen;
	}

	public String getInitialExtent() {
		return initialExtent;
	}

	public void setInitialExtent(String initialExtent) {
		this.initialExtent = initialExtent;
	}

	public String getNextExtent() {
		return nextExtent;
	}

	public void setNextExtent(String nextExtent) {
		this.nextExtent = nextExtent;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public String getTableLock() {
		return tableLock;
	}

	public void setTableLock(String tableLock) {
		this.tableLock = tableLock;
	}

}


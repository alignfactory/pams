package myApp.client.sys.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface TabCommentsModelProperties extends PropertyAccess<TabCommentsModel> {
	
	ModelKeyProvider<TabCommentsModel> keyId();
	
	ValueProvider<TabCommentsModel, String>	tableName();
	ValueProvider<TabCommentsModel, String>	comments();
	ValueProvider<TabCommentsModel, String>	tablespaceName();
	ValueProvider<TabCommentsModel, Long>	pctFree();
	ValueProvider<TabCommentsModel, Long>	pctUsed();
	ValueProvider<TabCommentsModel, Long>	iniTrans();
	ValueProvider<TabCommentsModel, Long>	maxTrans();
	ValueProvider<TabCommentsModel, Long>	pctIncrease();
	ValueProvider<TabCommentsModel, Long>	freeLists();
	ValueProvider<TabCommentsModel, Long>	numRows();
	ValueProvider<TabCommentsModel, Long>	blocks();
	ValueProvider<TabCommentsModel, Long>	emptyBlocks();
	ValueProvider<TabCommentsModel, Long>	chainCnt();
	ValueProvider<TabCommentsModel, Long>	avgRowLen();
	ValueProvider<TabCommentsModel, String>	initialExtent();
	ValueProvider<TabCommentsModel, String>	nextExtent();
	ValueProvider<TabCommentsModel, String>	cache();
	ValueProvider<TabCommentsModel, String>	tableLock();	
}

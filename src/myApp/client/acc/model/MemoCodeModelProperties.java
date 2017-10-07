package myApp.client.acc.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface MemoCodeModelProperties extends PropertyAccess<MemoCodeModel> {
	
	ModelKeyProvider<MemoCodeModel> keyId();

		ValueProvider<MemoCodeModel, Long>		memoId();
		ValueProvider<MemoCodeModel, Long>		companyId();
		ValueProvider<MemoCodeModel, String>		memoCode();
		ValueProvider<MemoCodeModel, String>		acctCode();
		ValueProvider<MemoCodeModel, String>		subCode();
		ValueProvider<MemoCodeModel, String>		memoDscr();
		ValueProvider<MemoCodeModel, String>		acctName();
}

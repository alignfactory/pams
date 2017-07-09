package myApp.client.rpt.model;

import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface TrialBalanceModelProperties extends PropertyAccess<TrialBalanceModel> {
	
	ModelKeyProvider<TrialBalanceModel> keyId();
	
	ValueProvider<TrialBalanceModel, 	Long>	RowNo();	
	ValueProvider<TrialBalanceModel, 	Long>	inBalanceSum();	
	ValueProvider<TrialBalanceModel, 	Long>	inAccumulatedSum();	
	ValueProvider<TrialBalanceModel, 	Long>	inAmounts();	
	ValueProvider<TrialBalanceModel, 	Long>	inBudgetAmount();	
	ValueProvider<TrialBalanceModel, 	String>	boldGb();	
	ValueProvider<TrialBalanceModel, 	String>	accountCd();	
	ValueProvider<TrialBalanceModel, 	String>	subCd();	
	ValueProvider<TrialBalanceModel, 	String>	accountPrtNm();	
	ValueProvider<TrialBalanceModel, 	Long>	outBudgetAmount();	
	ValueProvider<TrialBalanceModel, 	Long>	outAmonut();	
	ValueProvider<TrialBalanceModel, 	Long>	outAccumulatedSum();	
	ValueProvider<TrialBalanceModel, 	Long>	outAalanceSum();	

}

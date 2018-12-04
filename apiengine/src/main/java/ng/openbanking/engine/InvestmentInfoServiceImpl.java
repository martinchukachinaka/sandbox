package ng.openbanking.engine;



import ng.openbanking.service.DataService;
import ng.openbanking.api.payload.bank.exception.BankResourceNotFoundException;
import ng.openbanking.api.payload.bank.exception.ServiceOperationNotSupported;
import ng.openbanking.api.payload.bank.service.InvestmentInfoService;
import ng.openbanking.api.payload.customer.PocessingOperationResponse;
import ng.openbanking.api.payload.definition.OperationStatus;
import ng.openbanking.api.payload.investment.InvestmentBook;
import ng.openbanking.api.payload.investment.InvestmentProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentInfoServiceImpl implements InvestmentInfoService {

	private static final String INVESTMENT_MODEL_FILE_NAME="Investment";
	
	
	@Autowired
	private DataService dataService;

	
	public List<InvestmentProduct> getInvestmentProduct()
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(INVESTMENT_MODEL_FILE_NAME);
	}

	
	public List<InvestmentProduct> getInvestmentByAccountId(String accountId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(INVESTMENT_MODEL_FILE_NAME);
	}

	
	public PocessingOperationResponse bookInvestment(InvestmentBook investmentBook)
			throws ServiceOperationNotSupported {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

}

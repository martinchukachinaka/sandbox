package com.apifuze.obn.bank.service;


import com.apifuze.utils.DataService;
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

@Service("investmentInfoService")
public class InvestmentInfoServiceImpl extends AbsractBankService implements InvestmentInfoService {

	private static final String INVESTMENT_MODEL_FILE_NAME="Investment";
	
	
	@Autowired
	private DataService dataService;

	
	public List<InvestmentProduct> getInvestmentProduct()
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(INVESTMENT_MODEL_FILE_NAME,InvestmentProduct.class);
	}

	
	public List<InvestmentProduct> getInvestmentByAccountId(String accountId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(INVESTMENT_MODEL_FILE_NAME,InvestmentProduct.class);
	}

	
	public PocessingOperationResponse bookInvestment(InvestmentBook investmentBook)
			throws ServiceOperationNotSupported {
		return generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

}

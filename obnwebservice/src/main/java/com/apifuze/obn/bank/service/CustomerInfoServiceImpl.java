package com.apifuze.obn.bank.service;


import com.apifuze.utils.DataService;
import ng.openbanking.api.payload.bank.exception.BankResourceNotFoundException;
import ng.openbanking.api.payload.bank.exception.ServiceOperationNotSupported;
import ng.openbanking.api.payload.bank.service.CustomerInfoService;
import ng.openbanking.api.payload.customer.Customer;
import ng.openbanking.api.payload.customer.PocessingOperationResponse;
import ng.openbanking.api.payload.definition.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("customerInfoService")
public class CustomerInfoServiceImpl extends AbsractBankService implements CustomerInfoService {

	private static final String CUSTOMER_MODEL_FILE_NAME = "Customer";
	
	
	@Autowired
	private DataService dataService;

	
	public Customer getByCustomerId(String customerId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(CUSTOMER_MODEL_FILE_NAME,Customer.class);
	}

	
	public Customer getByPhoneNumber(String phoneNumber)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(CUSTOMER_MODEL_FILE_NAME,Customer.class);
	}

	
	public Customer getByEmail(String email) throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(CUSTOMER_MODEL_FILE_NAME,Customer.class);
	}

	
	public Customer getByBVN(String bvn) throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(CUSTOMER_MODEL_FILE_NAME,Customer.class);
	}

	
	public PocessingOperationResponse block(String customerId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

	
	
}

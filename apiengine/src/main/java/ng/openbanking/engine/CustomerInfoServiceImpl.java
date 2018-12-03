package ng.openbanking.engine;


import ng.openbanking.service.DataService;
import ng.openbanking.api.payload.bank.exception.BankResourceNotFoundException;
import ng.openbanking.api.payload.bank.exception.ServiceOperationNotSupported;
import ng.openbanking.api.payload.bank.service.CustomerInfoService;
import ng.openbanking.api.payload.customer.Customer;
import ng.openbanking.api.payload.customer.PocessingOperationResponse;
import ng.openbanking.api.payload.definition.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

	private static final String CUSTOMER_MODEL_FILE_NAME = "Customer";
	
	
	@Autowired
	private DataService dataService;

	
	public Customer getByCustomerId(String customerId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(CUSTOMER_MODEL_FILE_NAME);
	}

	
	public Customer getByPhoneNumber(String phoneNumber)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(CUSTOMER_MODEL_FILE_NAME);
	}

	
	public Customer getByEmail(String email) throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(CUSTOMER_MODEL_FILE_NAME);
	}

	
	public Customer getByBVN(String bvn) throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(CUSTOMER_MODEL_FILE_NAME);
	}

	
	public PocessingOperationResponse block(String customerId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

	
	
}

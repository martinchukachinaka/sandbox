package ng.openbanking.engine;

import ng.openbanking.api.payload.bank.exception.BankResourceNotFoundException;
import ng.openbanking.api.payload.bank.exception.ServiceOperationNotSupported;
import ng.openbanking.api.payload.bank.service.BankTransactionService;
import ng.openbanking.api.payload.customer.PocessingOperationResponse;
import ng.openbanking.api.payload.definition.OperationStatus;
import ng.openbanking.api.payload.transaction.*;
import ng.openbanking.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BankTransactionServiceImpl implements BankTransactionService {

	@Autowired
	private DataService dataService;
	
	private static final String STATEMENT_MODEL_FILE_NAME = "statement";
	
	
	public PocessingOperationResponse singleTransferWithinBank(SingleTransferBank singleTransferBank)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

	
	public PocessingOperationResponse singleTransferOtherBank(SingleTransferBank singleTransferBank)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}


	public PocessingOperationResponse singleTransferToEmail(String email, SingleTransfer singleTransfer) {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
		
	}

	
	public PocessingOperationResponse singleTransferToPhone(String phone, SingleTransfer singleTransfer) {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}


	public PocessingOperationResponse multipleTransferWithinBank(MultipleTransferBank multipleTransferBank) {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

	
	public PocessingOperationResponse multipleTransferOtherBank(MultipleTransferBank multipleTransferBank) {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}


	public PocessingOperationResponse multipleTransferToPhone(MultipleTransfer multipleTransfer) {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

	
	public PocessingOperationResponse multipleTransferToEmail(MultipleTransfer multipleTransfer) {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}


	public PocessingOperationResponse placeHold(PlaceHold placeHold) {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

	
	public PocessingOperationResponse getHold(String accountNumber, String holdReferenceId) {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

	
	public PocessingOperationResponse removeHold(String accountNumber, String holdReferenceId) {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

	
	public PocessingOperationResponse placePnd(String accountNumber, String pndReferenceId, String amount,
			String reason) {
		return dataService.generateProcessingResponse(OperationStatus.SUCCESSFUL);
	}

	
	public List<GetStatementOutput> getStatement(GetStatement getStatement) {
		return dataService.getModelList(STATEMENT_MODEL_FILE_NAME);
	}

}

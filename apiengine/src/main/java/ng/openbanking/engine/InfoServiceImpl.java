package ng.openbanking.engine;



import ng.openbanking.service.DataService;
import ng.openbanking.api.payload.agency.Agency;
import ng.openbanking.api.payload.atm.ATM;
import ng.openbanking.api.payload.bank.exception.BankResourceNotFoundException;
import ng.openbanking.api.payload.bank.exception.ServiceOperationNotSupported;
import ng.openbanking.api.payload.bank.service.BankInfoService;
import ng.openbanking.api.payload.branch.Branch;
import ng.openbanking.api.payload.pos.POS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoServiceImpl implements BankInfoService {

	@Autowired
	private DataService dataService;
	
	private static final String AGENCY_MODEL_FILE_NAME = "Agency";
	
	private static final String ATM_MODEL_FILE_NAME = "Atm";
	
	private static final String BRANCH_MODEL_FILE_NAME = "Branch";
	
	private static final String POS_MODEL_FILE_NAME="Pos";

	
	public Agency getAgencyById(String agencyId) throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(AGENCY_MODEL_FILE_NAME);
		
	}

	
	public List<Agency> getAgencies() throws ServiceOperationNotSupported {
		return dataService.getModelList(AGENCY_MODEL_FILE_NAME);
	}

	
	public List<Agency> getAgencyByLongAndLat(double longitude, double latitude) throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(AGENCY_MODEL_FILE_NAME);
	}

	
	public ATM getAtmById(String terminalId) throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(AGENCY_MODEL_FILE_NAME);
	}

	
	public List<ATM> getAtms() throws ServiceOperationNotSupported {
		return dataService.getModelList(ATM_MODEL_FILE_NAME);
	}

	
	public ATM getAtmByBranchCode(String branchCode)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(ATM_MODEL_FILE_NAME);
	}

	
	public List<ATM> getAtmByLongAndLat(double longitude, double latitude) throws ServiceOperationNotSupported {
		return dataService.getModelList(ATM_MODEL_FILE_NAME);
	}

	
	public Branch getBanchByBranchId(String branchId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getSingleFromList(BRANCH_MODEL_FILE_NAME);
	}

	
	public List<Branch> getBranches() throws ServiceOperationNotSupported {
		return dataService.getModelList(BRANCH_MODEL_FILE_NAME);
	}

	
	public List<Branch> getBranchesByLongAndLat(double longitude, double latitude) throws ServiceOperationNotSupported {
		return dataService.getModelList(BRANCH_MODEL_FILE_NAME);
	}

	
	public  List<POS> getPosByTerminalId(String terminalId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(POS_MODEL_FILE_NAME);
	}

	
	public  List<POS> getPosByMerchantId(String merchantId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(POS_MODEL_FILE_NAME);
	}

	
	public  List<POS> getPosByBranchId(String branchId) throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(POS_MODEL_FILE_NAME);
	}

	
	public  List<POS> getPosByEmail(String email) throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(POS_MODEL_FILE_NAME);
	}

	
	public List<POS> getPosNearLocation(double longitude, double latitude) throws ServiceOperationNotSupported {
		return dataService.getModelList(POS_MODEL_FILE_NAME);
	}
	
}

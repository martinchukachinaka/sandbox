package com.apifuze.obn.bank.service;


import com.apifuze.utils.DataService;
import ng.openbanking.api.payload.bank.exception.BankResourceNotFoundException;
import ng.openbanking.api.payload.bank.exception.ServiceOperationNotSupported;
import ng.openbanking.api.payload.bank.service.BillerInfoService;
import ng.openbanking.api.payload.billpayment.BillPaymentItem;
import ng.openbanking.api.payload.billpayment.Biller;
import ng.openbanking.api.payload.billpayment.BillerCategory;
import ng.openbanking.api.payload.billpayment.BillingSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("billerInfoService")
public class BillerInfoServiceImpl extends AbsractBankService  implements BillerInfoService {

	private static final String BILLER_CAT_MODEL_FILE_NAME="BillerCategory";
	
	private static final String BILLER_SYS_MODEL_FILE_NAME="BillingSystem";
	
	private static final String BILLER_MODEL_FILE_NAME="Biller";
	
	private static final String BILL_ITEM_MODEL_FILE_NAME="BillPaymentItem";
	
	@Autowired
	private DataService dataService;


	
	public List<BillerCategory> getBillerCategories() throws ServiceOperationNotSupported {
		return dataService.getModelList(BILLER_CAT_MODEL_FILE_NAME,BillerCategory.class);
	}


	
	public List<BillingSystem> getBillingSystems() throws ServiceOperationNotSupported {
		return dataService.getModelList(BILLER_SYS_MODEL_FILE_NAME,BillingSystem.class);
	}


	
	public List<Biller> getBillersByCategoryId(String categoryId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(BILLER_MODEL_FILE_NAME,Biller.class);
	}


	
	public List<BillPaymentItem> getBillPaymentItemByBillerId(String billerId)
			throws BankResourceNotFoundException, ServiceOperationNotSupported {
		return dataService.getModelList(BILL_ITEM_MODEL_FILE_NAME,BillPaymentItem.class);
	}


}

package com.apifuze.obn.bank.service;

import com.apifuze.utils.DataService;
import ng.openbanking.api.payload.customer.PocessingOperationResponse;
import ng.openbanking.api.payload.definition.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbsractBankService {

    @Autowired
    DataService dataService;

    public PocessingOperationResponse generateProcessingResponse(OperationStatus requiredStatus) {
        PocessingOperationResponse response=new PocessingOperationResponse();
//        response.setResponseCode(requiredStatus);
//        response.setTransactionReferenceId(System.currentTimeMillis()+"");
//        response.setMessage(requiredStatus.name());
        return response;
    }
}

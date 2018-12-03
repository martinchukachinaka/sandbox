package com.apifuze.obn.bank.service;

import ng.openbanking.api.payload.customer.PocessingOperationResponse;
import ng.openbanking.api.payload.definition.OperationStatus;

public abstract class AbsractBankService {


    public PocessingOperationResponse generateProcessingResponse(OperationStatus requiredStatus) {
        PocessingOperationResponse response=new PocessingOperationResponse();
//        response.setResponseCode(requiredStatus);
//        response.setTransactionReferenceId(System.currentTimeMillis()+"");
//        response.setMessage(requiredStatus.name());
        return response;
    }
}

package com.revolut.money.transfer.service;

import com.revolut.money.transfer.domain.AccountDetail;
import com.revolut.money.transfer.dto.GenericResponse;
import com.revolut.money.transfer.dto.TransferRequest;

import java.util.List;

public interface AccountDetailService {
    GenericResponse transfer(TransferRequest transferRequest);
    List<AccountDetail> getAll();

    void save();
}

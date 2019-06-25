package com.revolut.money.transfer.util;

import com.revolut.money.transfer.dto.AccountDetailRequest;
import com.revolut.money.transfer.exception.TransactionException;

import javax.ws.rs.core.Response;

public class AccountDetailUtil {
    private AccountDetailUtil() {
    }

    public static void checkFromAndToAccountEquality(AccountDetailRequest fromAccount, AccountDetailRequest toAccount) {
        if (fromAccount.getAccountNumber().equalsIgnoreCase(toAccount.getAccountNumber())) {
            throw new TransactionException(Response.Status.CONFLICT, ResponseMessageConstant.ACCOUNT_NUMBER_CONFLICT);
        }
    }
}

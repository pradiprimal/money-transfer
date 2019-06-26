package com.revolut.money.transfer.util;

import com.revolut.money.transfer.dto.AccountDetailRequest;
import com.revolut.money.transfer.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;

public class AccountDetailUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDetailUtil.class);

    private AccountDetailUtil() {
    }

    public static void checkFromAndToAccountEquality(AccountDetailRequest fromAccount, AccountDetailRequest toAccount) {
        if (fromAccount.getAccountNumber().equalsIgnoreCase(toAccount.getAccountNumber())) {
            LOGGER.error("From account and To account number is same");
            throw new TransactionException(Response.Status.CONFLICT, ResponseMessageConstant.ACCOUNT_NUMBER_CONFLICT);
        }
    }
}

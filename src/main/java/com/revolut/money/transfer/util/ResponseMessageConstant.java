package com.revolut.money.transfer.util;

public interface ResponseMessageConstant {
    String ACCOUNT_NUMBER_CONFLICT = "From and To account number can't be same";
    String FROM_ACCOUNT_NOT_FOUND = "From account has not been found";
    String TO_ACCOUNT_NOT_FOUND = "To account has not been found";
    String INSUFFICIENT_AMOUNT = "Insufficient amount found";
    String CONCURRENT_TRANSACTION = "Concurrent transaction found so transaction has been rollbacked";
    String SUCCESSFULLY_TRANSFERRED = "Successfully transferred";
}

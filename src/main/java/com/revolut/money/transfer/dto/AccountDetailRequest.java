package com.revolut.money.transfer.dto;

import org.hibernate.validator.constraints.NotBlank;

public class AccountDetailRequest {
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String accountHolderName;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
}

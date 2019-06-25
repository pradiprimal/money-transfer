package com.revolut.money.transfer.dto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferRequest {

    @Valid
    @NotNull
    private AccountDetailRequest fromAccount;
    @Valid
    @NotNull
    private AccountDetailRequest toAccount;
    @Min(1)
    private BigDecimal amount;

    private String remarks;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public AccountDetailRequest getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(AccountDetailRequest fromAccount) {
        this.fromAccount = fromAccount;
    }

    public AccountDetailRequest getToAccount() {
        return toAccount;
    }

    public void setToAccount(AccountDetailRequest toAccount) {
        this.toAccount = toAccount;
    }
}

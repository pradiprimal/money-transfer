package com.revolut.money.transfer.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountDetail {
    public AccountDetail() {
    }

    public AccountDetail(String id, String accountNumber, String accountHolderName,
                         BigDecimal balance, LocalDateTime createdDate,
                         LocalDateTime updatedDate, long version) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.version = version;
    }

    private String id;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private long version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void debit(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
    }
}

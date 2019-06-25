package com.revolut.money.transfer.domain;

import com.revolut.money.transfer.util.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDetail {
    private String id;
    private TransactionTypeEnum transactionType;
    private String remarks;
    private BigDecimal amount;
    private LocalDateTime createdDate;
    private String accountDetailId;

    public TransactionDetail() {
    }

    public TransactionDetail(String id, TransactionTypeEnum transactionType,
                             String remarks, BigDecimal amount, LocalDateTime createdDate, String accountDetailId) {
        this.id = id;
        this.transactionType = transactionType;
        this.remarks = remarks;
        this.amount = amount;
        this.createdDate = createdDate;
        this.accountDetailId = accountDetailId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEnum transactionType) {
        this.transactionType = transactionType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getAccountDetailId() {
        return accountDetailId;
    }

    public void setAccountDetailId(String accountDetailId) {
        this.accountDetailId = accountDetailId;
    }
}

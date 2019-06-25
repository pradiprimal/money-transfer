package com.revolut.money.transfer.repository;

import com.revolut.money.transfer.domain.AccountDetail;
import com.revolut.money.transfer.domain.TransactionDetail;
import com.revolut.money.transfer.util.H2JDBIRule;
import com.revolut.money.transfer.util.TransactionTypeEnum;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TransactionDetailRepositoryTest {
    @ClassRule
    public static H2JDBIRule rule = new H2JDBIRule();

    @Test
    public void testShould_Save_TransactionDetails() {
        TransactionDetailRepository transactionDetailRepository = rule.getDbi().open(TransactionDetailRepository.class);
        AccountDetailRepository accountDetailRepository = rule.getDbi().open(AccountDetailRepository.class);
        AccountDetail accountDetail = getAccountDetail();
        accountDetailRepository.createAccountDetail(accountDetail);
        TransactionDetail transactionDetail = getTransactionDetail();
        transactionDetail.setAccountDetailId(accountDetail.getId());
        transactionDetailRepository.createTransactionDetail(transactionDetail);
        List<TransactionDetail> transactionDetails = transactionDetailRepository.getAll();
        Assert.assertNotNull("Transaction details should not be null", transactionDetails);
        Assert.assertEquals("Transaction details size should be one", 1, transactionDetails.size());
        Assert.assertEquals("Transaction type should be equal", TransactionTypeEnum.DR, transactionDetails.get(0).getTransactionType());
    }

    private TransactionDetail getTransactionDetail() {
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setCreatedDate(LocalDateTime.now());
        transactionDetail.setRemarks("Bank to bank transfer");
        transactionDetail.setId(UUID.randomUUID().toString());
        transactionDetail.setTransactionType(TransactionTypeEnum.DR);
        transactionDetail.setAmount(BigDecimal.TEN);
        return transactionDetail;
    }

    private AccountDetail getAccountDetail() {
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setId(UUID.randomUUID().toString());
        accountDetail.setCreatedDate(LocalDateTime.now());
        accountDetail.setBalance(BigDecimal.TEN);
        accountDetail.setAccountNumber("ACC-12");
        accountDetail.setAccountHolderName("Pradip Rimal");
        return accountDetail;
    }
}

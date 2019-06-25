package com.revolut.money.transfer.repository;

import com.revolut.money.transfer.domain.AccountDetail;
import com.revolut.money.transfer.util.H2JDBIRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class AccountDetailRepositoryTest {

    @ClassRule
    public static H2JDBIRule rule = new H2JDBIRule();

    @Test
    public void testShouldGetAllAccountDetailsData() {//Testing with existing data
        AccountDetailRepository accountDetailRepository = rule.getDbi().open(AccountDetailRepository.class);
        List<AccountDetail> accountDetails = accountDetailRepository.getAll();
        Assert.assertNotNull("Account details should not be null", accountDetails);
        Assert.assertTrue("Data should be more than three", accountDetails.size() > 3);
    }

    @Test
    public void testShould_Find_AccountDetails_ByAccountNumberAndName() {
        AccountDetailRepository accountDetailRepository = rule.getDbi().open(AccountDetailRepository.class);
        accountDetailRepository.createAccountDetail(getAccountDetail("Test", "Test_acc"));
        AccountDetail accountDetailsNumberAndName = accountDetailRepository.findByAccountNumberAndName("Test", "Test_acc");
        Assert.assertNotNull("accountDetailsByAccountNumberAndName should not be null ", accountDetailsNumberAndName);
        Assert.assertEquals("Account Name should be equal ", "Test", accountDetailsNumberAndName.getAccountHolderName());
        Assert.assertEquals("Account Number should be equal ", "Test_acc", accountDetailsNumberAndName.getAccountNumber());
    }

    @Test
    public void testShould_Update_AccountDetails_ByAccountNumberAndName() {
        AccountDetailRepository accountDetailRepository = rule.getDbi().open(AccountDetailRepository.class);
        accountDetailRepository.createAccountDetail(getAccountDetail("Pradip", "Acc_1"));
        AccountDetail accountDetailsNumberAndName = accountDetailRepository.findByAccountNumberAndName("Pradip", "Acc_1");
        accountDetailsNumberAndName.setUpdatedDate(LocalDateTime.now());
        accountDetailsNumberAndName.setBalance(BigDecimal.valueOf(100));
        accountDetailRepository.updateAccountDetail(accountDetailsNumberAndName);
        AccountDetail updatedDetailsNumberAndName = accountDetailRepository.findByAccountNumberAndName("Pradip", "Acc_1");
        Assert.assertNotNull("accountDetailsByAccountNumberAndName should not be null ", updatedDetailsNumberAndName);
        Assert.assertEquals("Account Name should be equal ", "Pradip", updatedDetailsNumberAndName.getAccountHolderName());
        Assert.assertEquals("Account Number should be equal ", "Acc_1", updatedDetailsNumberAndName.getAccountNumber());
        Assert.assertTrue("Version should be greater than zero", updatedDetailsNumberAndName.getVersion() > 0);
        Assert.assertNotNull("Updated date should not be null ", updatedDetailsNumberAndName.getUpdatedDate());
    }

    private AccountDetail getAccountDetail(String name, String accountNumber) {
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setId(UUID.randomUUID().toString());
        accountDetail.setCreatedDate(LocalDateTime.now());
        accountDetail.setBalance(BigDecimal.TEN);
        accountDetail.setAccountNumber(accountNumber);
        accountDetail.setAccountHolderName(name);
        return accountDetail;
    }
}

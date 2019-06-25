package com.revolut.money.transfer.util;


import com.revolut.money.transfer.dto.AccountDetailRequest;
import com.revolut.money.transfer.exception.TransactionException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountDetailUtilTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testShould_ThrowTransactionException_When_FromAndToAccountIsEqual() {
        expectedException.expect(TransactionException.class);
        expectedException.expectMessage(ResponseMessageConstant.ACCOUNT_NUMBER_CONFLICT);
        AccountDetailUtil.checkFromAndToAccountEquality(getAccountDetailRequest("test name", "accNumber"),
                getAccountDetailRequest("test name", "accNumber"));

    }

    @Test
    public void testShould_CheckFromAndToAccountIsNotEqual() {
        AccountDetailUtil.checkFromAndToAccountEquality(getAccountDetailRequest("test name", "accNumber"),
                getAccountDetailRequest("test name", "acc"));

    }

    private AccountDetailRequest getAccountDetailRequest(String name, String accountNumber) {
        AccountDetailRequest accountDetailRequest = new AccountDetailRequest();
        accountDetailRequest.setAccountHolderName(name);
        accountDetailRequest.setAccountNumber(accountNumber);
        return accountDetailRequest;
    }
}

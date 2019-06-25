package com.revolut.money.transfer.service;

import com.revolut.money.transfer.domain.AccountDetail;
import com.revolut.money.transfer.dto.AccountDetailRequest;
import com.revolut.money.transfer.dto.GenericResponse;
import com.revolut.money.transfer.dto.TransferRequest;
import com.revolut.money.transfer.exception.TransactionException;
import com.revolut.money.transfer.repository.AccountDetailRepository;
import com.revolut.money.transfer.repository.TransactionDetailRepository;
import com.revolut.money.transfer.util.ResponseMessageConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class AccountDetailServiceTest {

    private AccountDetailServiceImpl accountDetailService;
    private AccountDetailRepository accountDetailRepository;
    private TransactionDetailRepository transactionDetailRepository;

    private String toAccount = "toAccount";
    private String fromAccount = "fromAccount";
    private String toAccountName = "Acc-to";
    private String fromAccountName = "Acc-from";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        accountDetailRepository = Mockito.mock(AccountDetailRepository.class);
        transactionDetailRepository = Mockito.mock(TransactionDetailRepository.class);
        accountDetailService = Mockito.mock(AccountDetailServiceImpl.class, Mockito.CALLS_REAL_METHODS);
        Mockito.when(accountDetailService.accountDetailRepository()).thenReturn(accountDetailRepository);
        Mockito.when(accountDetailService.transactionDetailRepository()).thenReturn(transactionDetailRepository);
    }

    @Test
    public void testShould_ThrowTransactionException_When_FromAccountDetails_NotFound() {
        expectedException.expect(TransactionException.class);
        expectedException.expectMessage(ResponseMessageConstant.FROM_ACCOUNT_NOT_FOUND);
        TransferRequest transferRequest = getTransferRequest(BigDecimal.valueOf(20));
        transferRequest.setFromAccount(getAccountDetailRequest(fromAccountName, fromAccount));
        transferRequest.setToAccount(getAccountDetailRequest(toAccountName, toAccount));
        accountDetailService.transfer(transferRequest);
    }


    @Test
    public void testShould_ThrowTransactionException_When_ToAccountDetails_NotFound() {
        expectedException.expect(TransactionException.class);
        expectedException.expectMessage(ResponseMessageConstant.TO_ACCOUNT_NOT_FOUND);
        Mockito.when(accountDetailRepository.findByAccountNumberAndName(fromAccountName, fromAccount)).thenReturn(new AccountDetail());
        TransferRequest transferRequest = getTransferRequest(BigDecimal.valueOf(20));
        transferRequest.setFromAccount(getAccountDetailRequest(fromAccountName, fromAccount));
        transferRequest.setToAccount(getAccountDetailRequest(toAccountName, toAccount));
        accountDetailService.transfer(transferRequest);
    }

    @Test
    public void testShould_ThrowTransactionException_When_Insufficient_BalanceFound() {
        expectedException.expect(TransactionException.class);
        expectedException.expectMessage(ResponseMessageConstant.INSUFFICIENT_AMOUNT);
        Mockito.when(accountDetailRepository.findByAccountNumberAndName(fromAccountName, fromAccount))
                .thenReturn(getAccountDetail(BigDecimal.valueOf(0)));
        Mockito.when(accountDetailRepository.findByAccountNumberAndName(toAccountName, toAccount))
                .thenReturn(getAccountDetail(BigDecimal.valueOf(200)));
        TransferRequest transferRequest = getTransferRequest(BigDecimal.valueOf(200));
        transferRequest.setFromAccount(getAccountDetailRequest(fromAccountName, fromAccount));
        transferRequest.setToAccount(getAccountDetailRequest(toAccountName, toAccount));
        accountDetailService.transfer(transferRequest);
    }

    @Test
    public void testShould_ThrowTransactionException_When_ConcurrentTransaction_Occurred() {
        expectedException.expect(TransactionException.class);
        expectedException.expectMessage(ResponseMessageConstant.CONCURRENT_TRANSACTION);
        Mockito.when(accountDetailRepository.findByAccountNumberAndName(fromAccountName, fromAccount))
                .thenReturn(getAccountDetail(BigDecimal.valueOf(1000)));
        Mockito.when(accountDetailRepository.findByAccountNumberAndName(toAccountName, toAccount))
                .thenReturn(getAccountDetail(BigDecimal.valueOf(200)));
        Mockito.when(accountDetailRepository.updateAccountDetail(Mockito.any(AccountDetail.class))).thenReturn(0);
        TransferRequest transferRequest = getTransferRequest(BigDecimal.valueOf(200));
        transferRequest.setFromAccount(getAccountDetailRequest(fromAccountName, fromAccount));
        transferRequest.setToAccount(getAccountDetailRequest(toAccountName, toAccount));
        accountDetailService.transfer(transferRequest);
        Assert.assertTrue("Transfer should be success", true);

    }

    @Test
    public void testShouldTransferBalance() {
        Mockito.when(accountDetailRepository.findByAccountNumberAndName(fromAccountName, fromAccount))
                .thenReturn(getAccountDetail(BigDecimal.valueOf(1000)));
        Mockito.when(accountDetailRepository.findByAccountNumberAndName(toAccountName, toAccount))
                .thenReturn(getAccountDetail(BigDecimal.valueOf(200)));
        Mockito.when(accountDetailRepository.updateAccountDetail(Mockito.any(AccountDetail.class))).thenReturn(1);
        TransferRequest transferRequest = getTransferRequest(BigDecimal.valueOf(200));
        transferRequest.setFromAccount(getAccountDetailRequest(fromAccountName, fromAccount));
        transferRequest.setToAccount(getAccountDetailRequest(toAccountName, toAccount));
        GenericResponse genericResponse = accountDetailService.transfer(transferRequest);
        Assert.assertNotNull("Generic response should not be null", genericResponse);
    }

    private TransferRequest getTransferRequest(BigDecimal balance) {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setAmount(balance);
        transferRequest.setRemarks("Loan");
        return transferRequest;
    }

    private AccountDetailRequest getAccountDetailRequest(String name, String accountNumber) {
        AccountDetailRequest accountDetailRequest = new AccountDetailRequest();
        accountDetailRequest.setAccountHolderName(name);
        accountDetailRequest.setAccountNumber(accountNumber);
        return accountDetailRequest;
    }

    private AccountDetail getAccountDetail(BigDecimal balance) {
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setId(UUID.randomUUID().toString());
        accountDetail.setVersion(0);
        accountDetail.setCreatedDate(LocalDateTime.now());
        accountDetail.setBalance(balance);
        return accountDetail;
    }
}

package com.revolut.money.transfer.service;

import com.revolut.money.transfer.domain.AccountDetail;
import com.revolut.money.transfer.domain.TransactionDetail;
import com.revolut.money.transfer.dto.GenericResponse;
import com.revolut.money.transfer.dto.TransferRequest;
import com.revolut.money.transfer.exception.TransactionException;
import com.revolut.money.transfer.repository.AccountDetailRepository;
import com.revolut.money.transfer.repository.TransactionDetailRepository;
import com.revolut.money.transfer.util.AccountDetailUtil;
import com.revolut.money.transfer.util.ResponseMessageConstant;
import com.revolut.money.transfer.util.TransactionTypeEnum;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public abstract class AccountDetailServiceImpl implements AccountDetailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDetailServiceImpl.class);

    @CreateSqlObject
    abstract AccountDetailRepository accountDetailRepository();

    @CreateSqlObject
    abstract TransactionDetailRepository transactionDetailRepository();

    @Override
    @Transaction
    public GenericResponse transfer(TransferRequest transferRequest) {
        AccountDetailUtil.checkFromAndToAccountEquality(transferRequest.getFromAccount(), transferRequest.getToAccount());

        AccountDetail fromAccountDetails = accountDetailRepository().findByAccountNumberAndName(
                transferRequest.getFromAccount().getAccountHolderName(), transferRequest.getFromAccount().getAccountNumber());

        if (null == fromAccountDetails) {
            LOGGER.error("fromAccountDetails not found in database for account holder name {} and account number {}",
                    transferRequest.getFromAccount().getAccountHolderName(), transferRequest.getFromAccount().getAccountNumber());
            throw new TransactionException(Response.Status.NOT_FOUND, ResponseMessageConstant.FROM_ACCOUNT_NOT_FOUND);
        }

        AccountDetail toAccountDetails = accountDetailRepository().findByAccountNumberAndName(
                transferRequest.getToAccount().getAccountHolderName(), transferRequest.getToAccount().getAccountNumber());

        if (null == toAccountDetails) {
            LOGGER.error("toAccountDetails not found in database for account holder name {} and account number {}",
                    transferRequest.getFromAccount().getAccountHolderName(), transferRequest.getFromAccount().getAccountNumber());
            throw new TransactionException(Response.Status.NOT_FOUND, ResponseMessageConstant.TO_ACCOUNT_NOT_FOUND);
        }

        if (fromAccountDetails.getBalance().compareTo(transferRequest.getAmount()) < 0) {
            LOGGER.error("Insufficient balance found");
            throw new TransactionException(Response.Status.NOT_ACCEPTABLE, ResponseMessageConstant.INSUFFICIENT_AMOUNT);
        }
        fromAccountDetails.debit(transferRequest.getAmount());
        update(fromAccountDetails);
        toAccountDetails.credit(transferRequest.getAmount());
        update(toAccountDetails);
        saveTransactionDetail(fromAccountDetails, transferRequest, TransactionTypeEnum.DR);
        saveTransactionDetail(fromAccountDetails, transferRequest, TransactionTypeEnum.CR);
        return (new GenericResponse<>(Response.Status.OK.getStatusCode(), ResponseMessageConstant.SUCCESSFULLY_TRANSFERRED));
    }

    private void update(AccountDetail accountDetails) {
        accountDetails.setUpdatedDate(LocalDateTime.now());
        if (0 == accountDetailRepository().updateAccountDetail(accountDetails)) {
            LOGGER.error("Somebody already perform the transaction");
            throw new TransactionException(Response.Status.PRECONDITION_FAILED, ResponseMessageConstant.CONCURRENT_TRANSACTION);
        }
    }

    private void saveTransactionDetail(AccountDetail accountDetail, TransferRequest transferRequest, TransactionTypeEnum transactionType) {
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setId(UUID.randomUUID().toString());
        transactionDetail.setAmount(transferRequest.getAmount());
        transactionDetail.setAccountDetailId(accountDetail.getId());
        transactionDetail.setRemarks(transferRequest.getRemarks());
        transactionDetail.setTransactionType(transactionType);
        transactionDetail.setCreatedDate(LocalDateTime.now());
        transactionDetailRepository().createTransactionDetail(transactionDetail);
    }


    public List<AccountDetail> getAll() {
        return accountDetailRepository().getAll();
    }

    @Transaction
    public void save() {
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setAccountHolderName("Test");
        accountDetail.setAccountNumber("acc12");
        accountDetail.setBalance(new BigDecimal(2000));
        accountDetail.setId(UUID.randomUUID().toString());
        accountDetail.setCreatedDate(LocalDateTime.now());
        accountDetailRepository().createAccountDetail(accountDetail);
        accountDetail.setBalance(new BigDecimal(20.2323));
        accountDetailRepository().updateAccountDetail(accountDetail);
    }


}

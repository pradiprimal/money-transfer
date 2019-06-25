package com.revolut.money.transfer.repository;

import com.revolut.money.transfer.domain.TransactionDetail;
import com.revolut.money.transfer.mapper.TransactionDetailMapper;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(TransactionDetailMapper.class)
public interface TransactionDetailRepository {

    String INSERT_TRANSACTION_DETAIL = "INSERT INTO TRANSACTION_DETAIL (ID,AMOUNT,TRANSACTION_TYPE," +
            "REMARKS,CREATED_DATE,ACCOUNT_DETAIL_ID)" +
            "VALUES(:id,:amount,:transactionType,:remarks,:createdDate,:accountDetailId)";

    @SqlUpdate(INSERT_TRANSACTION_DETAIL)
    void createTransactionDetail(@BindBean TransactionDetail transactionDetail);

    @SqlQuery("SELECT * FROM TRANSACTION_DETAIL")
    List<TransactionDetail> getAll();


}

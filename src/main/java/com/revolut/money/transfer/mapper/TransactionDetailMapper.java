package com.revolut.money.transfer.mapper;

import com.revolut.money.transfer.domain.TransactionDetail;
import com.revolut.money.transfer.util.TransactionTypeEnum;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransactionDetailMapper implements ResultSetMapper<TransactionDetail> {
    private static final String ID = "ID";
    private static final String TRANSACTION_TYPE = "TRANSACTION_TYPE";
    private static final String AMOUNT = "AMOUNT";
    private static final String CREATED_DATE = "CREATED_DATE";
    private static final String REMARKS = "REMARKS";
    private static final String ACCOUNT_DETAIL_ID = "ACCOUNT_DETAIL_ID";

    @Override
    public TransactionDetail map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new TransactionDetail(r.getString(ID),
                TransactionTypeEnum.valueOf(r.getString(TRANSACTION_TYPE)),r.getString(REMARKS),r.getBigDecimal(AMOUNT), r.getObject(CREATED_DATE, LocalDateTime.class), r.getString(ACCOUNT_DETAIL_ID));
    }
}

package com.revolut.money.transfer.mapper;

import com.revolut.money.transfer.domain.AccountDetail;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AccountDetailMapper implements ResultSetMapper<AccountDetail> {
    private static final String ID = "ID";
    private static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
    private static final String ACCOUNT_HOLDER_NAME = "ACCOUNT_HOLDER_NAME";
    private static final String BALANCE = "BALANCE";
    private static final String CREATED_DATE = "CREATED_DATE";
    private static final String UPDATED_DATE = "UPDATED_DATE";
    private static final String VERSION = "VERSION";

    @Override
    public AccountDetail map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new AccountDetail(resultSet.getString(ID),
                resultSet.getString(ACCOUNT_NUMBER),
                resultSet.getString(ACCOUNT_HOLDER_NAME), resultSet.getBigDecimal(BALANCE),
                resultSet.getObject(CREATED_DATE, LocalDateTime.class), resultSet.getObject(UPDATED_DATE, LocalDateTime.class), resultSet.getLong(VERSION));
    }
}

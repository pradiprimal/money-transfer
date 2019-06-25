package com.revolut.money.transfer.repository;

import com.revolut.money.transfer.domain.AccountDetail;
import com.revolut.money.transfer.mapper.AccountDetailMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(AccountDetailMapper.class)
public interface AccountDetailRepository {

    String INSERT_ACCOUNT_DETAIL = "INSERT INTO ACCOUNT_DETAIL (ID,ACCOUNT_NUMBER,ACCOUNT_HOLDER_NAME," +
            "BALANCE,CREATED_DATE,UPDATED_DATE,VERSION)" +
            "VALUES(:id,:accountNumber,:accountHolderName,:balance,:createdDate,:updatedDate,:version)";

    String UPDATE_ACCOUNT_DETAIL = "UPDATE ACCOUNT_DETAIL SET BALANCE = :balance , UPDATED_DATE = :updatedDate , VERSION = VERSION+1 " +
            "WHERE ID = :id AND VERSION = :version";

    @SqlQuery("SELECT * FROM ACCOUNT_DETAIL")
    List<AccountDetail> getAll();

    @SqlUpdate(INSERT_ACCOUNT_DETAIL)
    void createAccountDetail(@BindBean AccountDetail accountDetail);

    @SqlUpdate(UPDATE_ACCOUNT_DETAIL)
    int updateAccountDetail(@BindBean AccountDetail accountDetail);

    @SqlQuery("SELECT * FROM ACCOUNT_DETAIL WHERE ACCOUNT_HOLDER_NAME = :name AND ACCOUNT_NUMBER = :accountNumber")
    AccountDetail findByAccountNumberAndName(@Bind("name") String name, @Bind("accountNumber") String accountNumber);


}

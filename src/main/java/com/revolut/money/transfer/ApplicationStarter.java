package com.revolut.money.transfer;

import com.revolut.money.transfer.config.MoneyTransferConfiguration;
import com.revolut.money.transfer.exception.GlobalExceptionMapper;
import com.revolut.money.transfer.exception.TransactionExceptionMapper;
import com.revolut.money.transfer.resource.TransactionResource;
import com.revolut.money.transfer.service.AccountDetailServiceImpl;
import com.revolut.money.transfer.util.LiquibaseRunner;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class ApplicationStarter extends Application<MoneyTransferConfiguration> {

    private String liquibaseChangeLogFile = "master.xml";
    private DBI dbi;

    public static void main(String[] args) throws Exception {
        new ApplicationStarter().run(args);
    }

    public void run(MoneyTransferConfiguration moneyTransferConfiguration, Environment environment) throws Exception {
        dbi = new DBIFactory().build(environment, moneyTransferConfiguration.getDataSourceFactory(), "dbi");
        environment.jersey().register(new TransactionResource(dbi.onDemand(AccountDetailServiceImpl.class)));
        environment.lifecycle().manage(new LiquibaseRunner(liquibaseChangeLogFile, dbi.open()));
        environment.jersey().register(TransactionExceptionMapper.class);
        environment.jersey().register(GlobalExceptionMapper.class);
    }
}

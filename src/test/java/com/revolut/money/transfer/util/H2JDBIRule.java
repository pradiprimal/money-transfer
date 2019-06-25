package com.revolut.money.transfer.util;

import com.codahale.metrics.MetricRegistry;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.rules.ExternalResource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

public class H2JDBIRule extends ExternalResource {

    private DBI dbi;

    private Handle handle;

    private Liquibase liquibase;

    public DBI getDbi() {
        return dbi;
    }

    public Handle getHandle() {
        return handle;
    }

    @Override
    protected void before() throws Exception {
        Environment environment = new Environment("test", Jackson.newObjectMapper(), null, new MetricRegistry(), null);
        dbi = new DBIFactory().build(environment, getDataSourceFactory(), "test");
        handle = dbi.open();
        migrateDatabase();
    }

    @Override
    protected void after() {
        try {
            liquibase.dropAll();
        } catch (Exception e) {
            throw new RuntimeException("failed clearing up Liquibase object", e);
        }
        handle.close();
    }

    private void migrateDatabase() throws LiquibaseException {
        liquibase = new Liquibase("master.xml", new ClassLoaderResourceAccessor(), new JdbcConnection(handle.getConnection()));
        liquibase.update("");
    }

    private DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("org.h2.Driver");
        dataSourceFactory.setUrl("jdbc:h2:mem:bank");
        return dataSourceFactory;
    }
}



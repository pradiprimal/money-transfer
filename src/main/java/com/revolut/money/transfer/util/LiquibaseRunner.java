package com.revolut.money.transfer.util;

import io.dropwizard.lifecycle.Managed;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.skife.jdbi.v2.Handle;

public class LiquibaseRunner implements Managed {
    private final String liquibaseChangeLogFile;
    private final Handle handle;

    public LiquibaseRunner(String liquibaseChangeLogFile, Handle handle) {
        this.liquibaseChangeLogFile = liquibaseChangeLogFile;
        this.handle = handle;
    }

    @Override
    public void start() throws Exception {
        Liquibase liquibase = new Liquibase(liquibaseChangeLogFile,
                new ClassLoaderResourceAccessor(), new JdbcConnection(handle.getConnection()));
        liquibase.update("");
    }

    @Override
    public void stop() throws Exception {

    }
}

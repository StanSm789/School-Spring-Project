package com.smirnov.springschooldatabase.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

public abstract class DaoTest {

    DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
            .addScript("schema_test.sql")
            .addScript("data_test.sql")
            .build();

    public JdbcTemplate jdbcTemplate() {

        return new JdbcTemplate(dataSource);
    }

    PlatformTransactionManager transactionManager() {

        return new DataSourceTransactionManager(dataSource);
    }

}


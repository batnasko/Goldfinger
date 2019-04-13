package com.goldfinger.auditability.repositories;

import com.goldfinger.auditability.repositories.contracts.AuditabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;


@Repository
@PropertySource("classpath:application.properties")
public class AuditabilityRepositoryImpl implements AuditabilityRepository {
    private String dbUrl, username, password;

    @Autowired
    public AuditabilityRepositoryImpl(Environment environment) {
        dbUrl = environment.getProperty("database.url.jdbc");
        username = environment.getProperty("database.username");
        password = environment.getProperty("database.password");
    }


}

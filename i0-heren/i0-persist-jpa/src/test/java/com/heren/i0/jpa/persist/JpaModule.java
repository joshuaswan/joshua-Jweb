package com.heren.core.jpa.persist;

import com.heren.core.config.Configuration;
import com.heren.core.container.grizzly.EmbeddedGrizzly;
import com.heren.core.core.Application;
import com.heren.core.core.ApplicationModule;
import com.heren.core.core.Servlet3;
import com.heren.core.jpa.JpaConfiguration;
import com.heren.core.jpa.JpaPersist;
import com.heren.core.jpa.config.H2;
import com.heren.core.jpa.config.Hibernate;

import static com.heren.core.config.Configuration.config;
import static com.heren.core.jpa.DatabaseConfiguration.database;


@JpaPersist(unit = "domain")
@EmbeddedGrizzly
@Servlet3
@Application("jpa")
public class JpaModule extends ApplicationModule<JpaConfiguration> {
    @Override
    protected JpaConfiguration createDefaultConfiguration(Configuration.ConfigurationBuilder config) {
        return new JpaConfiguration(config().logging().console().end().end().build(),
                database().with(H2.driver, H2.tempFileDB, H2.compatible("ORACLE"),
                        Hibernate.dialect("Oracle10g"), Hibernate.showSql)
                        .user("sa").password("").build());
    }
}

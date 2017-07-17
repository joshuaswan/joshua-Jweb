package com.heren.core.jpa;


import com.heren.core.config.Configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class JpaConfiguration extends Configuration implements WithDatabase {
    private DatabaseConfiguration database;

    public JpaConfiguration() {
    }

    public JpaConfiguration(Configuration configuration, DatabaseConfiguration database) {
        super(configuration);
        this.database = database;
    }

    @XmlElement
    @Override
    public DatabaseConfiguration getDatabase() {
        return database;
    }
}

package com.heren.core.jpa.config;

import java.io.File;
import java.io.IOException;

import static com.google.common.base.Throwables.propagate;
import static com.heren.core.jpa.DatabaseConfiguration.DatabaseConfigurationBuilder;

public class H2 {
    public static final DatabaseConfigurationBuilder.Setting driver = new DatabaseConfigurationBuilder.Setting() {
        @Override
        public void set(DatabaseConfigurationBuilder config) {
            config.driver("org.h2.Driver");
        }
    };
    public static final DatabaseConfigurationBuilder.Setting tempFileDB = new DatabaseConfigurationBuilder.Setting() {
        @Override
        public void set(DatabaseConfigurationBuilder config) {
            try {
                config.url("jdbc:h2:" + File.createTempFile("i0-db-driver", ".db").getAbsolutePath());
            } catch (IOException e) {
                propagate(e);
            }
        }
    };

    public static final DatabaseConfigurationBuilder.Setting fileDB(final String fileName) {
        return new DatabaseConfigurationBuilder.Setting() {
            @Override
            public void set(DatabaseConfigurationBuilder config) {
                config.url("jdbc:h2:" + fileName);
            }
        };
    }

    public static final DatabaseConfigurationBuilder.Setting privateMemoryDB = new DatabaseConfigurationBuilder.Setting() {
        @Override
        public void set(DatabaseConfigurationBuilder config) {
            config.url("jdbc:h2:mem");
        }
    };


    public static DatabaseConfigurationBuilder.Setting compatible(final String mode) {
        return new DatabaseConfigurationBuilder.Setting() {
            @Override
            public void set(DatabaseConfigurationBuilder config) {
                config.appendToUrl(";MODE=" + mode);
            }
        };
    }
}

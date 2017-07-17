package com.heren.core.core.servlet3;

import com.heren.core.config.Configuration;
import com.heren.core.core.Application;
import com.heren.core.core.ApplicationModule;
import com.heren.core.core.Servlet3;

import static com.heren.core.config.Configuration.config;

@Application("autoscan")
@Servlet3
public class AutoScan extends ApplicationModule<Configuration> {
    @Override
    protected Configuration createDefaultConfiguration(Configuration.ConfigurationBuilder config) {
        return config().http().end().build();
    }
}

package com.heren.core.core.guicemodule;

import com.heren.core.config.Configuration;
import com.heren.core.core.Application;
import com.heren.core.core.ApplicationModule;
import com.heren.core.core.GuiceModule;

@GuiceModule
@Application("autoscan")
public class AutoScan extends ApplicationModule<Configuration> {
    @Override
    protected Configuration createDefaultConfiguration(Configuration.ConfigurationBuilder config) {
        return Configuration.config().http().end().build();
    }
}

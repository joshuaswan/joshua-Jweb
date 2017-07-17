package com.heren.core.core.guicemodule;

import com.heren.core.config.Configuration;
import com.heren.core.core.ApplicationModule;
import com.heren.core.core.GuiceModule;

@GuiceModule(packages = "com.heren.i0.core.guicemodule.p2")
public class Specified extends ApplicationModule<Configuration> {
    @Override
    protected Configuration createDefaultConfiguration(Configuration.ConfigurationBuilder config) {
        return Configuration.config().http().end().build();
    }
}

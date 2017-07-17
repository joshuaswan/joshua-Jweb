package com.heren.core.jersey.api;

import com.heren.core.config.Configuration;
import com.heren.core.config.util.LogLevel;
import com.heren.core.container.grizzly.EmbeddedGrizzly;
import com.heren.core.core.Application;
import com.heren.core.core.ApplicationModule;
import com.heren.core.jersey.RestApi;

import static com.heren.core.config.Configuration.config;

@Application("autoscan") @EmbeddedGrizzly
@RestApi(prefix = "/api/v2/*")
public class V2 extends ApplicationModule<Configuration> {
    @Override
    protected Configuration createDefaultConfiguration(Configuration.ConfigurationBuilder config) {
        return config().http().port(8051).end().logging().level(LogLevel.DEBUG).console().end().end().build();
    }
}

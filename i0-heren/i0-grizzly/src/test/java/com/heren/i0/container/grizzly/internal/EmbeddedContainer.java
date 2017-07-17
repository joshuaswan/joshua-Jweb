package com.heren.core.container.grizzly.internal;

import com.heren.core.config.Configuration;
import com.heren.core.config.util.LogLevel;
import com.heren.core.container.grizzly.EmbeddedGrizzly;
import com.heren.core.core.Application;
import com.heren.core.core.ApplicationModule;
import com.heren.core.core.Servlet3;

import static com.heren.core.config.Configuration.config;
import static com.heren.core.container.grizzly.EmbeddedGrizzly.Asset;

@Application("embedded")
@EmbeddedGrizzly(assets = @Asset(uri = "/static", resource = "./webapp"),
mimeExtensions = {@EmbeddedGrizzly.MimeExtension(extension = "eot", mime = "application/vnd.ms-fontobject"),
@EmbeddedGrizzly.MimeExtension(extension = "svg", mime = "image/svg+xml")})
@Servlet3
public class EmbeddedContainer extends ApplicationModule<Configuration> {
    @Override
    protected Configuration createDefaultConfiguration(Configuration.ConfigurationBuilder config) {
        return config().http().port(8051).end().logging().level(LogLevel.INFO).console().end().end().build();
    }
}

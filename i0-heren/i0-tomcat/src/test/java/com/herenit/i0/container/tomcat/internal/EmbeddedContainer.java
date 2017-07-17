package com.herenit.i0.container.tomcat.internal;

import com.herenit.i0.container.tomcat.EmbeddedTomcat;
import com.heren.core.config.Configuration;
import com.heren.core.config.util.LogLevel;
import com.heren.core.core.Application;
import com.heren.core.core.ApplicationModule;
import com.heren.core.core.Servlet3;

import static com.heren.core.config.Configuration.config;


@Application("embedded")
@EmbeddedTomcat(assets = @EmbeddedTomcat.Asset(uri = "/static", resource = "./webapp"),
mimeExtensions = {@EmbeddedTomcat.MimeExtension(extension = "eot", mime = "application/vnd.ms-fontobject"),
@EmbeddedTomcat.MimeExtension(extension = "svg", mime = "image/svg+xml")})
@Servlet3
public class EmbeddedContainer extends ApplicationModule<Configuration> {
    @Override
    protected Configuration createDefaultConfiguration(Configuration.ConfigurationBuilder config) {
        return config().http().port(8051).end().logging().level(LogLevel.DEBUG).console().end().end().build();
    }
}
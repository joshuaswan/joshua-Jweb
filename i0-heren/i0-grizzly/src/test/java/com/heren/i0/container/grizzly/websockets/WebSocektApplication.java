package com.heren.core.container.grizzly.websockets;

import com.heren.core.config.Configuration;
import com.heren.core.config.util.LogLevel;
import com.heren.core.container.grizzly.EmbeddedGrizzly;
import com.heren.core.container.grizzly.WebSocket;
import com.heren.core.core.Application;
import com.heren.core.core.ApplicationModule;

import static com.heren.core.config.Configuration.config;

@EmbeddedGrizzly
@WebSocket
@Application("websocket")
public class WebSocektApplication extends ApplicationModule<Configuration> {
    @Override
    protected Configuration createDefaultConfiguration(Configuration.ConfigurationBuilder config) {
        return config().http().port(8051).end().logging().level(LogLevel.INFO).console().end().end().build();
    }
}

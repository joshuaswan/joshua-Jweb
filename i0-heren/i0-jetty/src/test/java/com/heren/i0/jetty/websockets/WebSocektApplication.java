package com.heren.core.jetty.websockets;

import com.heren.core.config.Configuration;
import com.heren.core.config.util.LogLevel;
import com.heren.core.core.Application;
import com.heren.core.core.ApplicationModule;
import com.heren.core.jetty.EmbeddedJetty;
import com.heren.core.jetty.WebSocket;

import static com.heren.core.config.Configuration.config;

@EmbeddedJetty
@WebSocket
@Application("websocket")
public class WebSocektApplication extends ApplicationModule<Configuration> {
    @Override
    protected Configuration createDefaultConfiguration(Configuration.ConfigurationBuilder config) {
        return config().http().port(8051).end().logging().level(LogLevel.INFO).console().end().end().build();
    }
}

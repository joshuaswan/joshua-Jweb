package com.heren.core.container.grizzly.internal.tryus;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.heren.core.core.GuiceInjector;
import com.heren.core.core.ServletContainer;

import javax.websocket.server.ServerEndpointConfig;

public class WebsocketConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass)
            throws InstantiationException {
        Injector injector = GuiceInjector.getInjector();
        T t = (T) injector.getInstance(endpointClass);
        return t;
    }


}

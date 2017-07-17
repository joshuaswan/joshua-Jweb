package com.heren.core.jetty.internal;

import com.google.inject.Binder;
import com.google.inject.servlet.ServletModule;
import com.heren.core.config.Configuration;
import com.heren.core.core.ApplicationModule;
import com.heren.core.core.BindingProvider;
import com.heren.core.core.ContainerCreator;
import com.heren.core.core.ServletContainer;
import com.heren.core.core.internal.servlet.AssetServlet;
import com.heren.core.jetty.EmbeddedJetty;

import java.util.HashMap;
import java.util.Map;

public class EmbeddedFactory implements ContainerCreator<EmbeddedJetty, Configuration>, BindingProvider<EmbeddedJetty, Configuration> {
    @Override
    public ServletContainer create(EmbeddedJetty annotation, Configuration configuration) {
        return new Embedded(configuration.getHttp());
    }

    @Override
    public void configure(Binder binder, final EmbeddedJetty annotation, ApplicationModule<?> module, Configuration configuration) {
        if (annotation.assets().length == 0) return;
        binder.install(new ServletModule() {
            @Override
            protected void configureServlets() {
                for (EmbeddedJetty.Asset asset : annotation.assets()) {
                    serve(asset.uri() + "/*").with(new AssetServlet(asset.resource()).setMimeExtensions(toMap(annotation.mimeExtensions())));
                }
            }
        });
    }

    private Map<String, String> toMap(EmbeddedJetty.MimeExtension[] mimeExtensions) {
        Map<String, String> map = new HashMap<>();
        for (EmbeddedJetty.MimeExtension mimeExtension : mimeExtensions) {
            map.put(mimeExtension.extension(), mimeExtension.mime());
        }
        return map;
    }
}

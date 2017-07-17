package com.heren.core.container.grizzly.internal;

import com.google.inject.Binder;
import com.google.inject.servlet.ServletModule;
import com.heren.core.config.Configuration;
import com.heren.core.container.grizzly.EmbeddedGrizzly;
import com.heren.core.core.ApplicationModule;
import com.heren.core.core.BindingProvider;
import com.heren.core.core.ContainerCreator;
import com.heren.core.core.ServletContainer;
import com.heren.core.core.internal.servlet.AssetServlet;

import java.util.HashMap;
import java.util.Map;

import static com.heren.core.container.grizzly.EmbeddedGrizzly.MimeExtension;

public class EmbeddedFactory implements ContainerCreator<EmbeddedGrizzly, Configuration>, BindingProvider<EmbeddedGrizzly, Configuration> {

    @Override
    public ServletContainer create(EmbeddedGrizzly annotation, Configuration configuration) {
        return new Embedded(configuration.getHttp());
    }

    @Override
    public void configure(Binder binder, final EmbeddedGrizzly annotation, ApplicationModule<?> module, Configuration configuration) {
        if (annotation.assets().length == 0) return;
        binder.install(new ServletModule() {
            @Override
            protected void configureServlets() {
                for (EmbeddedGrizzly.Asset asset : annotation.assets()) {
                    serve(asset.uri() + "/*").with(new AssetServlet(asset.resource()).setMimeExtensions(toMap(annotation.mimeExtensions())));
                }
            }
        });
    }

    private Map<String, String> toMap(MimeExtension[] mimeExtensions) {
        Map<String, String> map = new HashMap<>();
        for (MimeExtension mimeExtension : mimeExtensions) {
            map.put(mimeExtension.extension(), mimeExtension.mime());
        }
        return map;
    }
}

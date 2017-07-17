package com.heren.core.core.guicemodule.p1;

import com.google.inject.AbstractModule;

public class Module1 extends AbstractModule {
    @Override
    protected void configure() {
        bind(String.class).toInstance("everything");
    }
}

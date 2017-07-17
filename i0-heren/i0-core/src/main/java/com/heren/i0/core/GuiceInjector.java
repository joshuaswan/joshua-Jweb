package com.heren.core.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.heren.core.core.internal.GuiceModuleEnabler;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class GuiceInjector {
    private static Injector injector;

    public static void setInjector(Module... modules) {
        injector = Guice.createInjector(modules);
    }

    public static void setInjector(Injector injector1) {
        injector = injector1;
    }

    public static Injector getInjector() {
        return injector;
    }


}

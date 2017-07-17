package com.heren.core.core;

import com.heren.core.core.internal.GuiceModuleEnabler;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Inherited
@Target({ANNOTATION_TYPE, TYPE})
@Retention(RUNTIME)
@Facet(GuiceModuleEnabler.class)
public @interface GuiceModule {
    String[] packages() default {};
}

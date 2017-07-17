package com.heren.core.jersey;

import com.heren.core.core.Facet;
import com.heren.core.jersey.internal.JerseyEnabler;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Inherited
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Facet(JerseyEnabler.class)
public @interface RestApi {
    String prefix() default "/api/*";

    String[] packages() default {};
}

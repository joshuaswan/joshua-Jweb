package com.heren.core.core;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Application {
    String value();

    boolean shareNothing() default true;
}

package com.joshua.core.core;

import java.lang.annotation.*;

/**
 * Created by joshua on 2017/7/18.
 */

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Application {
    String value();

    boolean shareNothing() default true;
}

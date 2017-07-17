package com.heren.core.container.grizzly;

import com.heren.core.container.grizzly.internal.EmbeddedFactory;
import com.heren.core.core.Facet;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Inherited
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Facet(EmbeddedFactory.class)
public @interface EmbeddedGrizzly {
    public static @interface Asset {
        String uri();

        String resource();
    }

    Asset[] assets() default {};

    public static @interface MimeExtension{
        String mime();

        String extension();
    }

    MimeExtension[] mimeExtensions() default {};
}

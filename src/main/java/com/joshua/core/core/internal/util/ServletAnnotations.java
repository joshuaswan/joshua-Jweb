package com.joshua.core.core.internal.util;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.omg.CORBA.ORB;

import javax.annotation.Nullable;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.ImmutableSet.copyOf;
import static com.google.common.collect.ImmutableSet.of;
import static com.google.common.collect.Iterables.filter;

/**
 * Created by joshua on 2017/7/24.
 */
public class ServletAnnotations {

    public static ImmutableSet<String> urlPatterns(Class<?> servletOrFiler) {
        if (servletOrFiler.isAnnotationPresent(WebServlet.class)) {
            WebServlet webServlet = servletOrFiler.getAnnotation(WebServlet.class);
            return copyOf(filter(ImmutableSet.<String>builder().add(webServlet.urlPatterns()).add(webServlet.value()).build(), NOT_EMPTY));
        } else if (servletOrFiler.isAnnotationPresent(WebFilter.class)) {
            WebFilter webFilter = servletOrFiler.getAnnotation(WebFilter.class);
            return copyOf(filter(ImmutableSet.<String>builder().add(webFilter.urlPatterns()).add(webFilter.value()).build(), NOT_EMPTY));
        }
        return of();
    }

    private static final Predicate<String> NOT_EMPTY = new Predicate<String>() {
        @Override
        public boolean apply(@Nullable String input) {
            return !input.isEmpty();
        }
    };

    public static final Function<Class<?>, String> LOG_FORMATTER = new Function<Class<?>, String>() {
        @Nullable
        @Override
        public String apply(@Nullable Class<?> input) {
            return String.format("%s --> %s", input, on(". ").join(urlPatterns(input)));
        }
    };
}

package com.joshua.core.config.builder;

import com.google.common.base.Function;
import com.google.common.base.Optional;

import javax.annotation.Nullable;

/**
 * Created by joshua on 2017/7/14.
 */
public class OptionalBuilder<T extends Builder<R>,R> implements Builder<Optional<R>> {
    private final T builder;
    private Optional<T> wrapper = Optional.absent();

    public OptionalBuilder(T builder) {
        this.builder = builder;
    }

    public T builder(){
        if (!wrapper.isPresent())
            wrapper = Optional.of(builder);
        return builder;
    }

    @Override
    public Optional<R> build() {
        return wrapper.transform(new Function<T, R>() {
            @Nullable
            @Override
            public R apply(@Nullable T input) {
                return input.build();
            }
        });
    }
}

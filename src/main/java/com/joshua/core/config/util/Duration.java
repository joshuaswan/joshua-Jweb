package com.joshua.core.config.util;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.CharMatcher.*;
import static com.google.common.collect.ImmutableSet.copyOf;

/**
 * Created by joshua on 2017/7/14.
 */
public class Duration {

    public Duration(double quantity, Unit unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public static enum Unit{
        ;
        private final TimeUnit timeUnit;
        private final ImmutableSet<String> representations;

        private Unit(TimeUnit timeUnit, ImmutableSet<String> representations) {
            this.timeUnit = timeUnit;
            this.representations = representations;
        }

        private static Optional<Unit> parse(final String name){
            return Iterables.tryFind(copyOf(Unit.values()), new Predicate<Unit>() {
                @Override
                public boolean apply(@Nullable Unit input) {
                    return input.toString().equals(name) || input.representations.contains(name);
                }
            });
        }
    }

    private final double quantity;
    @NotNull
    private final Unit unit;

    private static Unit unit(String trimmed){
        Optional<Unit> unit = Unit.parse(DIGIT.trimLeadingFrom(trimmed).toUpperCase());
        Preconditions.checkArgument(unit.isPresent(),"invalid size format");
        return unit.get();
    }

    public long value(){
        return (long) (quantity * unit.timeUnit.toMillis(1));
    }

    @JsonCreator
    public static Duration valueOf(String text){
        String trimmed = WHITESPACE.removeFrom(text).trim();
        return new Duration(quantity(trimmed),unit(trimmed));
    }

    private static double quantity(String trimmed) {
        try{
            return Double.parseDouble(JAVA_LETTER.trimTrailingFrom(trimmed));
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("invalid size format");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Duration duration = (Duration) o;

        if (Double.compare(duration.quantity, quantity) != 0) return false;
        return unit == duration.unit;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(quantity);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + unit.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Duration{" +
                "quantity=" + quantity +
                ", unit=" + unit +
                '}';
    }
}

package org.mosestream.stream;

import org.jetbrains.annotations.NotNull;
import org.mosestream.MoseStream;
import org.mosestream.lamda.ThrowableConsumer;
import org.mosestream.lamda.ThrowableFunction;
import org.mosestream.lamda.ThrowablePredicate;
import org.mosestream.number.MoseDoubleStream;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class DoubleJavaStreamWrapper extends JavaStreamWrapper<Double> implements MoseDoubleStream {
    public DoubleJavaStreamWrapper(Stream<?> stream) {
        super(stream);
    }

    public DoubleJavaStreamWrapper(Supplier<Stream<?>> getter) {
        super(getter);
    }

    protected DoubleJavaStreamWrapper(Supplier<Stream<?>> getter, Function<Stream<?>, Stream<Double>> previousAction) {
        super(getter, previousAction);
    }

    private DoubleJavaStreamWrapper(JavaStreamWrapper<Double> wrapper) {
        super(wrapper.getter, wrapper.previousAction);
    }

    @Override
    public @NotNull <T1 extends Throwable> DoubleJavaStreamWrapper filter(@NotNull ThrowablePredicate<Double, T1> predicate) {
        return new DoubleJavaStreamWrapper(super.filter(predicate));
    }

    @Override
    public @NotNull <T1 extends Throwable> DoubleJavaStreamWrapper filterOut(@NotNull ThrowablePredicate<Double, T1> predicate) {
        return new DoubleJavaStreamWrapper(super.filterOut(predicate));
    }

    @Override
    public @NotNull <T1 extends Throwable> DoubleJavaStreamWrapper distinctBy(@NotNull ThrowableFunction<Double, ?, T1> function) {
        return new DoubleJavaStreamWrapper(super.distinctBy(function));
    }

    @Override
    public @NotNull DoubleJavaStreamWrapper distinct() {
        return new DoubleJavaStreamWrapper(super.distinct());
    }

    @Override
    public @NotNull DoubleJavaStreamWrapper distinct(@NotNull BiPredicate<Double, Double> predicate) {
        return new DoubleJavaStreamWrapper(super.distinct(predicate));
    }

    @Override
    public @NotNull DoubleJavaStreamWrapper limit(long limit) {
        return new DoubleJavaStreamWrapper(super.limit(limit));
    }

    @Override
    public @NotNull DoubleJavaStreamWrapper skip(long skip) {
        return new DoubleJavaStreamWrapper(super.skip(skip));
    }

    @Override
    public @NotNull DoubleJavaStreamWrapper sorted(@NotNull Comparator<Double> compare) {
        return new DoubleJavaStreamWrapper(super.sorted(compare));
    }

    @Override
    public @NotNull <T1 extends Throwable> DoubleJavaStreamWrapper each(@NotNull ThrowableConsumer<Double, T1> consumer) {
        return new DoubleJavaStreamWrapper(super.each(consumer));
    }
}

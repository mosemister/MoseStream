package org.mosestream.stream;

import org.jetbrains.annotations.NotNull;
import org.mosestream.lamda.ThrowableConsumer;
import org.mosestream.lamda.ThrowableFunction;
import org.mosestream.lamda.ThrowablePredicate;
import org.mosestream.number.MoseIntegerStream;
import org.mosestream.number.MoseIntegerStream;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class IntegerJavaStreamWrapper extends JavaStreamWrapper<Integer> implements MoseIntegerStream {

    public IntegerJavaStreamWrapper(Stream<?> stream) {
        super(stream);
    }

    public IntegerJavaStreamWrapper(Supplier<Stream<?>> getter) {
        super(getter);
    }

    protected IntegerJavaStreamWrapper(Supplier<Stream<?>> getter, Function<Stream<?>, Stream<Integer>> previousAction) {
        super(getter, previousAction);
    }

    private IntegerJavaStreamWrapper(JavaStreamWrapper<Integer> wrapper) {
        super(wrapper.getter, wrapper.previousAction);
    }

    @Override
    public @NotNull <T1 extends Throwable> IntegerJavaStreamWrapper filter(@NotNull ThrowablePredicate<Integer, T1> predicate) {
        return new IntegerJavaStreamWrapper(super.filter(predicate));
    }

    @Override
    public @NotNull <T1 extends Throwable> IntegerJavaStreamWrapper filterOut(@NotNull ThrowablePredicate<Integer, T1> predicate) {
        return new IntegerJavaStreamWrapper(super.filterOut(predicate));
    }

    @Override
    public @NotNull <T1 extends Throwable> IntegerJavaStreamWrapper distinctBy(@NotNull ThrowableFunction<Integer, ?, T1> function) {
        return new IntegerJavaStreamWrapper(super.distinctBy(function));
    }

    @Override
    public @NotNull IntegerJavaStreamWrapper distinct() {
        return new IntegerJavaStreamWrapper(super.distinct());
    }

    @Override
    public @NotNull IntegerJavaStreamWrapper distinct(@NotNull BiPredicate<Integer, Integer> predicate) {
        return new IntegerJavaStreamWrapper(super.distinct(predicate));
    }

    @Override
    public @NotNull IntegerJavaStreamWrapper limit(long limit) {
        return new IntegerJavaStreamWrapper(super.limit(limit));
    }

    @Override
    public @NotNull IntegerJavaStreamWrapper skip(long skip) {
        return new IntegerJavaStreamWrapper(super.skip(skip));
    }

    @Override
    public @NotNull IntegerJavaStreamWrapper sorted(@NotNull Comparator<Integer> compare) {
        return new IntegerJavaStreamWrapper(super.sorted(compare));
    }

    @Override
    public @NotNull <T1 extends Throwable> IntegerJavaStreamWrapper each(@NotNull ThrowableConsumer<Integer, T1> consumer) {
        return new IntegerJavaStreamWrapper(super.each(consumer));
    }
}

package org.mosestream;

import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.base.DoubleBaseStream;
import org.mosestream.base.IntegerBaseStream;
import org.mosestream.base.SimpleBaseStream;
import org.mosestream.iterable.array.ArrayIterable;
import org.mosestream.iterable.array.DoubleArrayIterable;
import org.mosestream.iterable.array.IntegerArrayIterable;
import org.mosestream.iterator.ThrowableIterator;
import org.mosestream.lamda.ThrowableBiFunction;
import org.mosestream.lamda.ThrowableConsumer;
import org.mosestream.lamda.ThrowableFunction;
import org.mosestream.lamda.ThrowablePredicate;
import org.mosestream.number.MoseDoubleStream;
import org.mosestream.number.MoseIntegerStream;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface MoseStream<V> {

    static <V> MoseStream<V> stream(Iterable<V> iterable) {
        return new SimpleBaseStream<>(iterable);
    }

    @SafeVarargs
    static <V> MoseStream<V> stream(V... array) {
        return stream(new ArrayIterable<>(array));
    }

    static MoseDoubleStream streamDouble(Iterable<Double> iterable) {
        return new DoubleBaseStream(iterable);
    }

    static MoseDoubleStream streamDouble(double... array) {
        return streamDouble(new DoubleArrayIterable(array));
    }

    static MoseIntegerStream streamInteger(Iterable<Integer> iterable) {
        return new IntegerBaseStream(iterable);
    }

    static MoseIntegerStream streamInteger(int... array) {
        return streamInteger(new IntegerArrayIterable(array));
    }

    @NotNull
    @CheckReturnValue
    <T extends Throwable> MoseStream<V> filter(@NotNull ThrowablePredicate<V, T> predicate);

    @NotNull
    @CheckReturnValue
    <T extends Throwable> MoseStream<V> filterOut(@NotNull ThrowablePredicate<V, T> predicate);

    @NotNull
    @CheckReturnValue
    <M, T extends Throwable> MoseStream<M> map(@NotNull ThrowableFunction<V, M, T> function);

    @NotNull
    @CheckReturnValue
    <T extends Throwable> MoseDoubleStream mapToDouble(@NotNull ThrowableFunction<V, Double, T> function);

    @NotNull
    @CheckReturnValue
    <T extends Throwable> MoseIntegerStream mapToInteger(@NotNull ThrowableFunction<V, Integer, T> function);

    @NotNull
    @CheckReturnValue
    <M, T extends Throwable> MoseStream<M> flatMap(@NotNull ThrowableFunction<V, Iterable<M>, T> function);

    @NotNull
    @CheckReturnValue
    <T extends Throwable> MoseDoubleStream flatMapToDouble(@NotNull ThrowableFunction<V, Iterable<Double>, T> function);

    @NotNull
    @CheckReturnValue
    <T extends Throwable> MoseIntegerStream flatMapToInteger(@NotNull ThrowableFunction<V, Iterable<Integer>, T> function);

    @NotNull
    @CheckReturnValue
    <T extends Throwable> MoseStream<V> distinctBy(@NotNull ThrowableFunction<V, ?, T> function);

    @NotNull
    @CheckReturnValue
    MoseStream<V> distinct();

    @NotNull
    @CheckReturnValue
    MoseStream<V> distinct(@NotNull BiPredicate<V, V> predicate);

    @NotNull
    @CheckReturnValue
    MoseStream<V> limit(long limit);

    @NotNull
    @CheckReturnValue
    MoseStream<V> skip(long skip);

    @NotNull
    @CheckReturnValue
    MoseStream<V> sorted(@NotNull Comparator<V> compare);

    @NotNull
    @CheckReturnValue
    <T extends Throwable> MoseStream<V> each(@NotNull ThrowableConsumer<V, T> consumer);

    @NotNull
    @CheckReturnValue
    <C, E, K, T extends Throwable> MoseStream<E> groupBy(Collector<V, C, E> collector, ThrowableFunction<V, K, T> function);

    @UnknownNullability
    @CheckReturnValue
    <R, T extends Throwable> R reduce(@UnknownNullability R initialValue, ThrowableBiFunction<V, R, R, T> function) throws T;

    @UnknownNullability
    @CheckReturnValue
    <T extends Throwable> Optional<V> reduce(ThrowableBiFunction<V, V, V, T> function) throws T;

    @NotNull
    @CheckReturnValue
    <R, A, T extends Throwable> R collect(@NotNull Collector<? super V, A, R> collector) throws T;

    @NotNull
    @CheckReturnValue
    <T extends Throwable> Optional<V> first() throws T;

    @NotNull
    @CheckReturnValue
    ThrowableIterator<V> iterator();

    @CheckReturnValue
    <T extends Throwable> boolean allMatch(@NotNull ThrowablePredicate<V, T> value) throws T;

    @CheckReturnValue
    <T extends Throwable> boolean noneMatch(@NotNull ThrowablePredicate<V, T> value) throws T;

    @CheckReturnValue
    <T extends Throwable> boolean anyMatch(@NotNull ThrowablePredicate<V, T> value) throws T;

    @CheckReturnValue
    <T extends Throwable> long count() throws T;

    <T extends Throwable> void forEach(@NotNull ThrowableConsumer<V, T> consumer) throws T;

    @CheckReturnValue
    <T extends Throwable> @NotNull Optional<V> max(@NotNull Comparator<V> compare) throws T;

    @CheckReturnValue
    <T extends Throwable> @NotNull Optional<V> min(@NotNull Comparator<V> compare) throws T;

    default <T extends Throwable> List<V> toList() throws T {
        return collect(Collectors.toList());
    }

    default <T extends Throwable> Set<V> toSet() throws T {
        return collect(Collectors.toSet());
    }

    <T extends Throwable> V[] toArray(@NotNull IntFunction<V[]> generator) throws T;

    <T extends Throwable> Stream<V> toStream() throws T;

    default <C> MoseStream<C> filterCast(@NotNull Class<?> type) {
        return this.filter(type::isInstance).map(value -> (C) value);
    }

}

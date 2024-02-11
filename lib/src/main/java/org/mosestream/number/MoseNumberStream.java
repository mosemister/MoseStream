package org.mosestream.number;

import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.mosestream.MoseStream;
import org.mosestream.lamda.ThrowableConsumer;
import org.mosestream.lamda.ThrowableFunction;
import org.mosestream.lamda.ThrowablePredicate;

import java.util.Comparator;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiPredicate;

public interface MoseNumberStream<N extends Number & Comparable<N>> extends MoseStream<N> {

    @CheckReturnValue
    default double[] toDoubleArray() {
        N[] array = this.toArray();
        double[] doubleArray = new double[array.length];
        for (int i = 0; i < doubleArray.length; i++) {
            doubleArray[i] = array[i].doubleValue();
        }
        return doubleArray;
    }

    @CheckReturnValue
    @NotNull
    N sum();

    @CheckReturnValue
    @NotNull <T extends Throwable> OptionalDouble average() throws T;

    @CheckReturnValue
    @NotNull
    default Optional<N> max() {
        return max(Comparator.<N>naturalOrder().reversed());
    }

    @CheckReturnValue
    @NotNull
    default Optional<N> min() {
        return min(Comparator.<N>naturalOrder().reversed());
    }

    @CheckReturnValue
    @NotNull
    N[] toArray();

    @CheckReturnValue
    @NotNull
    default MoseNumberStream<N> sorted() {
        return sorted(Comparator.naturalOrder());
    }

    @Override
    @NotNull <T extends Throwable> MoseNumberStream<N> filter(@NotNull ThrowablePredicate<N, T> predicate);

    @Override
    @NotNull <T extends Throwable> MoseNumberStream<N> filterOut(@NotNull ThrowablePredicate<N, T> predicate);

    @Override
    @NotNull <T extends Throwable> MoseNumberStream<N> distinctBy(@NotNull ThrowableFunction<N, ?, T> function);

    @Override
    @NotNull MoseNumberStream<N> distinct();

    @Override
    @NotNull MoseNumberStream<N> distinct(@NotNull BiPredicate<N, N> predicate);

    @Override
    @NotNull MoseNumberStream<N> limit(long limit);

    @Override
    @NotNull MoseNumberStream<N> skip(long skip);

    @Override
    @NotNull MoseNumberStream<N> sorted(@NotNull Comparator<N> compare);

    @Override
    @NotNull <T extends Throwable> MoseNumberStream<N> each(@NotNull ThrowableConsumer<N, T> consumer);
}

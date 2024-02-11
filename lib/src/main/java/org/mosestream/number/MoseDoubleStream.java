package org.mosestream.number;

import org.jetbrains.annotations.NotNull;
import org.mosestream.iterator.ThrowableIterator;
import org.mosestream.lamda.ThrowableConsumer;
import org.mosestream.lamda.ThrowableFunction;
import org.mosestream.lamda.ThrowablePredicate;

import java.util.Comparator;
import java.util.OptionalDouble;
import java.util.function.BiPredicate;

public interface MoseDoubleStream extends MoseNumberStream<Double> {

    @Override
    default @NotNull Double[] toArray() {
        return toArray(Double[]::new);
    }

    @Override
    default @NotNull Double sum() {
        return reduce(Double::sum).orElse(0.0);
    }

    @Override
    default @NotNull <T extends Throwable> OptionalDouble average() throws T {
        double total = 0;
        int amount = 0;
        ThrowableIterator<Double> iterator = this.iterator();
        if (!iterator.hasNext()) {
            return OptionalDouble.empty();
        }
        try {
            while (iterator.hasNext()) {
                double value = iterator.next();
                total += value;
                amount += 1;
            }
            return OptionalDouble.of(total / amount);
        } catch (Throwable e) {
            throw (T) e;
        }
    }

    @Override
    @NotNull <T extends Throwable> MoseDoubleStream filter(@NotNull ThrowablePredicate<Double, T> predicate);

    @Override
    @NotNull <T extends Throwable> MoseDoubleStream filterOut(@NotNull ThrowablePredicate<Double, T> predicate);

    @Override
    @NotNull <T extends Throwable> MoseDoubleStream distinctBy(@NotNull ThrowableFunction<Double, ?, T> function);

    @Override
    @NotNull MoseDoubleStream distinct();

    @Override
    @NotNull MoseDoubleStream distinct(@NotNull BiPredicate<Double, Double> predicate);

    @Override
    @NotNull MoseDoubleStream limit(long limit);

    @Override
    @NotNull MoseDoubleStream skip(long skip);

    @Override
    @NotNull MoseDoubleStream sorted(@NotNull Comparator<Double> compare);

    @Override
    @NotNull <T extends Throwable> MoseDoubleStream each(@NotNull ThrowableConsumer<Double, T> consumer);
}

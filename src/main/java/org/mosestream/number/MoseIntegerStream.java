package org.mosestream.number;

import org.jetbrains.annotations.NotNull;
import org.mosestream.iterator.ThrowableIterator;
import org.mosestream.lamda.ThrowableConsumer;
import org.mosestream.lamda.ThrowableFunction;
import org.mosestream.lamda.ThrowablePredicate;

import java.util.Comparator;
import java.util.OptionalDouble;
import java.util.function.BiPredicate;

public interface MoseIntegerStream extends MoseNumberStream<Integer> {

    @Override
    default @NotNull Integer[] toArray() {
        return toArray(Integer[]::new);
    }

    @Override
    default @NotNull Integer sum() {
        return reduce(Integer::sum).orElse(0);
    }

    @Override
    default @NotNull <T extends Throwable> OptionalDouble average() throws T {
        double total = 0;
        int amount = 0;
        ThrowableIterator<Integer, T> iterator = this.iterator();
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
    @NotNull <T extends Throwable> MoseIntegerStream filter(@NotNull ThrowablePredicate<Integer, T> predicate);

    @Override
    @NotNull <T extends Throwable> MoseIntegerStream filterOut(@NotNull ThrowablePredicate<Integer, T> predicate);

    @Override
    @NotNull <T extends Throwable> MoseIntegerStream distinctBy(@NotNull ThrowableFunction<Integer, ?, T> function);

    @Override
    @NotNull MoseIntegerStream distinct();

    @Override
    @NotNull MoseIntegerStream distinct(@NotNull BiPredicate<Integer, Integer> predicate);

    @Override
    @NotNull MoseIntegerStream limit(long limit);

    @Override
    @NotNull MoseIntegerStream skip(long skip);

    @Override
    @NotNull MoseIntegerStream sorted(@NotNull Comparator<Integer> compare);

    @Override
    @NotNull <T extends Throwable> MoseIntegerStream each(@NotNull ThrowableConsumer<Integer, T> consumer);
}

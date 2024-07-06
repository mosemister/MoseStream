package org.mosestream;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.StreamAction;
import org.mosestream.action.actions.*;
import org.mosestream.action.actions.whole.FlatMapAction;
import org.mosestream.action.actions.whole.GroupByAction;
import org.mosestream.action.actions.whole.SortAction;
import org.mosestream.action.actions.whole.WholeAction;
import org.mosestream.action.stream.DoubleActionStream;
import org.mosestream.action.stream.IntegerActionStream;
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
import java.util.function.BiPredicate;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

@ApiStatus.Internal
public abstract class AbstractStream<V, S extends AbstractStream<V, S>> implements MoseStream<V> {

    private StreamAction<V, ?, ?> action;
    private WholeAction<V, ?, ?> preActions;

    public abstract AbstractStream<?, ?> baseStream();

    public abstract <T> MoseStream<T> newAction();

    public abstract S newInstance();

    public @Nullable StreamAction<V, ?, ?> action() {
        return this.action;
    }

    public @Nullable WholeAction<V, ?, ?> preAction() {
        return this.preActions;
    }

    @Override
    public @NotNull <T extends Throwable> S filter(@NotNull ThrowablePredicate<V, T> predicate) {
        this.action = new FilterAction<>(predicate);
        return newInstance();
    }

    @Override
    public @NotNull <T extends Throwable> S filterOut(@NotNull ThrowablePredicate<V, T> predicate) {
        this.action = new FilterAction<>(value -> !predicate.apply(value));
        return newInstance();
    }

    @Override
    public @NotNull <M, T extends Throwable> MoseStream<M> map(@NotNull ThrowableFunction<V, M, T> function) {
        this.action = new MapAction<>(function);
        return newAction();
    }

    @Override
    public @NotNull <M, T extends Throwable> MoseStream<M> flatMap(@NotNull ThrowableFunction<V, Iterable<M>, T> function) {
        this.preActions = new FlatMapAction<>(function);
        return newAction();
    }

    @Override
    public @NotNull <T extends Throwable> MoseDoubleStream mapToDouble(@NotNull ThrowableFunction<V, Double, T> function) {
        this.action = new MapAction<>(function);
        return new DoubleActionStream(this);
    }

    @Override
    public @NotNull <T extends Throwable> MoseIntegerStream mapToInteger(@NotNull ThrowableFunction<V, Integer, T> function) {
        this.action = new MapAction<>(function);
        return new IntegerActionStream(this);
    }

    @Override
    public @NotNull <T extends Throwable> MoseDoubleStream flatMapToDouble(@NotNull ThrowableFunction<V, Iterable<Double>, T> function) {
        this.preActions = new FlatMapAction<>(function);
        return new DoubleActionStream(this);
    }

    @Override
    public @NotNull <T extends Throwable> MoseIntegerStream flatMapToInteger(@NotNull ThrowableFunction<V, Iterable<Integer>, T> function) {
        this.preActions = new FlatMapAction<>(function);
        return new IntegerActionStream(this);
    }

    @Override
    public @NotNull S sorted(@NotNull Comparator<V> compare) {
        this.preActions = new SortAction<>(compare);
        return newInstance();
    }

    @Override
    public @NotNull <T extends Throwable> S distinctBy(@NotNull ThrowableFunction<V, ?, T> function) {
        this.action = new DistinctByAction<>(function);
        return newInstance();
    }

    @Override
    public @NotNull S distinct(@NotNull BiPredicate<V, V> predicate) {
        this.action = new DistinctAction<>(predicate);
        return newInstance();
    }

    @Override
    public @NotNull S distinct() {
        return distinctBy(value -> value);
    }

    @Override
    public <T extends Throwable> boolean allMatch(@NotNull ThrowablePredicate<V, T> value) throws T {
        ThrowableIterator<V> iterator = this.iterator();
        try {
            while (iterator.hasNext()) {
                V val = iterator.next();
                if (!value.apply(val)) {
                    return false;
                }
            }
        } catch (Throwable e) {
            throw (T) e;
        }
        return true;
    }

    @Override
    public <T extends Throwable> boolean noneMatch(@NotNull ThrowablePredicate<V, T> value) throws T {
        ThrowableIterator<V> iterator = this.iterator();
        try {
            while (iterator.hasNext()) {
                V val = iterator.next();
                if (value.apply(val)) {
                    return false;
                }
            }
        } catch (Throwable e) {
            throw (T) e;
        }
        return true;
    }

    @Override
    public <T extends Throwable> boolean anyMatch(@NotNull ThrowablePredicate<V, T> value) throws T {
        ThrowableIterator<V> iterator = this.iterator();
        try {
            while (iterator.hasNext()) {
                V val = iterator.next();
                if (value.apply(val)) {
                    return true;
                }
            }
        } catch (Throwable e) {
            throw (T) e;
        }
        return false;
    }

    @Override
    public <T extends Throwable> long count() throws T {
        ThrowableIterator<V> iterator = this.iterator();
        try {
            long count = 0;
            while (iterator.hasNext()) {
                iterator.next();
                count++;
            }
            return count;
        } catch (Throwable e) {
            throw (T) e;
        }
    }

    @Override
    public @NotNull S limit(long limit) {
        this.action = new LimitAction<>(limit);
        return newInstance();
    }

    @Override
    public @NotNull S skip(long skip) {
        this.action = new SkipAction<>(skip);
        return newInstance();
    }

    @Override
    public @NotNull <C, E, K, T extends Throwable> MoseStream<E> groupBy(Collector<V, C, E> collector, ThrowableFunction<V, K, T> function) {
        this.preActions = new GroupByAction<>(collector, function);
        return newAction();
    }

    @Override
    public @NotNull <T extends Throwable> S each(@NotNull ThrowableConsumer<V, T> consumer) {
        this.action = new EachAction<>(consumer);
        return newInstance();
    }

    @Override
    public <T extends Throwable> @NotNull Optional<V> max(@NotNull Comparator<V> compare) throws T {
        return compare(compare, value -> value > 0);
    }

    @Override
    public @NotNull <T extends Throwable> Optional<V> min(@NotNull Comparator<V> compare) throws T {
        return compare(compare, value -> value < 0);
    }

    private <T extends Throwable> Optional<V> compare(@NotNull Comparator<V> compare, IntPredicate assign) throws T {
        ThrowableIterator<V> iterator = this.iterator();
        try {
            V toCompare = null;
            while (iterator.hasNext()) {
                V value = iterator.next();
                if (toCompare == null) {
                    toCompare = value;
                    continue;
                }
                int compared = compare.compare(toCompare, value);
                if (assign.test(compared)) {
                    toCompare = value;
                }
            }
            return Optional.ofNullable(toCompare);
        } catch (Throwable e) {
            throw (T) e;
        }
    }

    @Override
    public @NotNull <R, T extends Throwable> R reduce(@UnknownNullability R initialValue, ThrowableBiFunction<V, R, R, T> function) throws T {
        ThrowableIterator<V> iterator = this.iterator();
        try {
            R value = initialValue;
            while (iterator.hasNext()) {
                V iterValue = iterator.next();
                value = function.map(iterValue, value);
            }
            return value;
        } catch (Throwable e) {
            throw (T) e;
        }
    }

    @Override
    public @NotNull <T extends Throwable> Optional<V> reduce(ThrowableBiFunction<V, V, V, T> function) throws T {
        ThrowableIterator<V> iterator = this.iterator();
        try {
            V value = null;
            while (iterator.hasNext()) {
                V iterValue = iterator.next();
                if (value == null) {
                    value = iterValue;
                    continue;
                }
                value = function.map(iterValue, value);
            }
            return Optional.ofNullable(value);
        } catch (Throwable e) {
            throw (T) e;
        }
    }


    @Override
    public <T extends Throwable> void forEach(@NotNull ThrowableConsumer<V, T> consumer) throws T {
        ThrowableIterator<V> iterator = this.iterator();
        try {
            while (iterator.hasNext()) {
                V value = iterator.next();
                consumer.apply(value);
            }
        } catch (Throwable e) {
            throw (T) e;
        }
    }

    @Override
    public <R, A, T extends Throwable> @NotNull R collect(@NotNull Collector<? super V, A, R> collector) throws T {
        A collection = collector.supplier().get();
        ThrowableIterator<V> iterator = this.iterator();
        while (iterator.hasNext()) {
            try {
                V value = iterator.next();
                collector.accumulator().accept(collection, value);
            } catch (Throwable e) {
                throw (T) e;
            }
        }
        return collector.finisher().apply(collection);
    }

    @Override
    public <T extends Throwable> V[] toArray(@NotNull IntFunction<V[]> generator) throws T {
        List<V> list = toList();
        V[] array = generator.apply(list.size());
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    @Override
    public <T extends Throwable> @NotNull Optional<V> first() throws T {
        ThrowableIterator<V> iterator = iterator();
        if (iterator.hasNext()) {
            try {
                return Optional.of(iterator.next());
            } catch (Throwable e) {
                throw (T) e;
            }
        }

        return Optional.empty();
    }

    @Override
    public <T extends Throwable> @NotNull Stream<V> toStream() throws T {
        Stream.Builder<V> builder = Stream.builder();
        ThrowableIterator<V> iterator = this.iterator();
        while (iterator.hasNext()) {
            try {
                V value = iterator.next();
                builder.add(value);
            } catch (Throwable e) {
                throw (T) e;
            }
        }
        return builder.build();
    }
}

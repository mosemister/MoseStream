package org.mosestream.stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.MoseStream;
import org.mosestream.iterator.CombinedIterator;
import org.mosestream.iterator.ThrowableIterator;
import org.mosestream.iterator.ThrowableIteratorWrapper;
import org.mosestream.lamda.ThrowableBiFunction;
import org.mosestream.lamda.ThrowableConsumer;
import org.mosestream.lamda.ThrowableFunction;
import org.mosestream.lamda.ThrowablePredicate;
import org.mosestream.number.MoseDoubleStream;
import org.mosestream.number.MoseIntegerStream;
import org.mosestream.stream.gatherer.DistinctByGatherer;
import org.mosestream.stream.gatherer.DistinctCompareGatherer;
import org.mosestream.stream.gatherer.MoseStreamGatherers;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JavaStreamWrapper<T> implements MoseStream<T> {

    protected final Supplier<Stream<?>> getter;
    protected final Function<Stream<?>, Stream<T>> previousAction;

    public JavaStreamWrapper(Stream<?> stream) {
        this(() -> stream);
    }

    public JavaStreamWrapper(Supplier<Stream<?>> getter) {
        this(getter, (stream) -> (Stream<T>) stream);
    }

    protected JavaStreamWrapper(Supplier<Stream<?>> getter, Function<Stream<?>, Stream<T>> previousAction) {
        this.getter = getter;
        this.previousAction = previousAction;
    }

    private <M> JavaStreamWrapper<M> create(Function<Stream<?>, Stream<M>> action) {
        return new JavaStreamWrapper<>(this.getter, createFunction(action));
    }

    private <M> Function<Stream<?>, Stream<M>> createFunction(Function<Stream<?>, Stream<M>> action) {
        return stream -> action.apply(this.previousAction.apply(stream));
    }

    private Stream<T> apply() {
        return previousAction.apply(getter.get());
    }

    @Override
    public @NotNull <T1 extends Throwable> JavaStreamWrapper<T> filter(@NotNull ThrowablePredicate<T, T1> predicate) {
        return create(stream -> (Stream<T>) stream.filter(item -> {
            try {
                return predicate.apply((T) item);
            } catch (Throwable t) {
                throw new StreamException(item, t);
            }
        }));
    }

    @Override
    public @NotNull <T1 extends Throwable> JavaStreamWrapper<T> filterOut(@NotNull ThrowablePredicate<T, T1> predicate) {
        return filter(item -> !predicate.apply(item));
    }

    @Override
    public @NotNull <M, T1 extends Throwable> MoseStream<M> map(@NotNull ThrowableFunction<T, M, T1> function) {
        return create(stream -> stream.map(item -> {
            try {
                return function.map((T) item);
            } catch (Throwable t) {
                throw new StreamException(item, t);
            }
        }));
    }

    @Override
    public @NotNull <T1 extends Throwable> MoseDoubleStream mapToDouble(@NotNull ThrowableFunction<T, Double, T1> function) {
        return new DoubleJavaStreamWrapper(this.getter, createFunction(stream -> stream.map(item -> {
            try {
                return function.map((T) item);
            } catch (Throwable t) {
                throw new StreamException(item, t);
            }
        })));
    }

    @Override
    public @NotNull <T1 extends Throwable> MoseIntegerStream mapToInteger(@NotNull ThrowableFunction<T, Integer, T1> function) {
        return new IntegerJavaStreamWrapper(this.getter, createFunction(stream -> stream.map(item -> {
            try {
                return function.map((T) item);
            } catch (Throwable t) {
                throw new StreamException(item, t);
            }
        })));
    }

    @Override
    public @NotNull <M, T1 extends Throwable> MoseStream<M> flatMap(@NotNull ThrowableFunction<T, Iterable<M>, T1> function) {
        return create(stream -> stream.flatMap(item -> {
            try {
                Iterable<M> result = function.map((T) item);
                return StreamSupport.stream(result.spliterator(), false);
            } catch (Throwable t) {
                throw new StreamException(item, t);
            }
        }));
    }

    @Override
    public @NotNull <T1 extends Throwable> MoseDoubleStream flatMapToDouble(@NotNull ThrowableFunction<T, Iterable<Double>, T1> function) {
        var action = createFunction(stream -> stream.flatMap(item -> {
            try {
                Iterable<Double> result = function.map((T) item);
                return StreamSupport.stream(result.spliterator(), false);
            } catch (Throwable t) {
                throw new StreamException(item, t);
            }
        }));
        return new DoubleJavaStreamWrapper(this.getter, action);
    }

    @Override
    public @NotNull <T1 extends Throwable> MoseIntegerStream flatMapToInteger(@NotNull ThrowableFunction<T, Iterable<Integer>, T1> function) {
        var action = createFunction(stream -> stream.flatMap(item -> {
            try {
                Iterable<Integer> result = function.map((T) item);
                return StreamSupport.stream(result.spliterator(), false);
            } catch (Throwable t) {
                throw new StreamException(item, t);
            }
        }));
        return new IntegerJavaStreamWrapper(this.getter, action);
    }

    @Override
    public @NotNull <T1 extends Throwable> JavaStreamWrapper<T> distinctBy(@NotNull ThrowableFunction<T, ?, T1> function) {
        return create(stream -> ((Stream<T>) stream).gather(MoseStreamGatherers.distinctBy(value -> {
            try {
                return function.map(value);
            } catch (Throwable e) {
                throw new StreamException(value, e);
            }
        })));
    }

    @Override
    public @NotNull JavaStreamWrapper<T> distinct() {
        return create(stream -> (Stream<T>) stream.distinct());
    }

    @Override
    public @NotNull JavaStreamWrapper<T> distinct(@NotNull BiPredicate<T, T> predicate) {
        return create(stream -> ((Stream<T>) stream).gather(MoseStreamGatherers.distinctCompare(predicate)));
    }

    @Override
    public @NotNull JavaStreamWrapper<T> limit(long limit) {
        return create(stream -> (Stream<T>) stream.limit(limit));
    }

    @Override
    public @NotNull JavaStreamWrapper<T> skip(long skip) {
        return create(stream -> (Stream<T>) stream.skip(skip));
    }

    @Override
    public @NotNull JavaStreamWrapper<T> sorted(@NotNull Comparator<T> compare) {
        return create(stream -> ((Stream<T>) stream).sorted(compare));
    }

    @Override
    public @NotNull <T1 extends Throwable> JavaStreamWrapper<T> each(@NotNull ThrowableConsumer<T, T1> consumer) {
        return create(stream -> (Stream<T>) stream.peek(item -> {
            try {
                consumer.apply((T) item);
            } catch (Throwable t) {
                throw new StreamException(item, t);
            }
        }));
    }

    @Override
    public @NotNull <By, T1 extends Throwable> MoseStream<MoseStream<T>> groupBy(ThrowableFunction<T, By, T1> function) {
        return create(stream -> ((Stream<T>) stream).gather(MoseStreamGatherers.groupBy((T value) -> {
            try {
                return function.map(value);
            } catch (Throwable e) {
                throw new StreamException(value, e);
            }
        })).map(JavaStreamWrapper::new));
    }

    @Override
    public <R, T1 extends Throwable> @UnknownNullability R reduce(@UnknownNullability R initialValue, ThrowableBiFunction<T, R, R, T1> function) throws T1 {
        try {
            var stream = apply();
            return stream.reduce(initialValue, (target, item) -> {
                try {
                    return function.map((T) item, target);
                } catch (Throwable t) {
                    throw new StreamException(item, t);
                }
            }, (r, r2) -> r);
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public <T1 extends Throwable> Optional<T> reduce(ThrowableBiFunction<T, T, T, T1> function) throws T1 {
        return Optional.ofNullable(reduce(null, function));
    }

    @Override
    public <R, A, T1 extends Throwable> @NotNull R collect(@NotNull Collector<? super T, A, R> collector) throws T1 {
        try {
            return apply().collect(collector);
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public @NotNull <T1 extends Throwable> Optional<T> first() throws T1 {
        try {
            return apply().findFirst();
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public <Throw extends Throwable> @NotNull ThrowableIterator<T, Throw> iterator() {
        return new ThrowableIteratorWrapper<>(apply().iterator());
    }

    @Override
    public <T1 extends Throwable> boolean allMatch(@NotNull ThrowablePredicate<T, T1> value) throws T1 {
        try {
            return apply().allMatch(item -> {
                try {
                    return value.apply(item);
                } catch (Throwable e) {
                    throw new StreamException(item, e);
                }
            });
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public <T1 extends Throwable> boolean noneMatch(@NotNull ThrowablePredicate<T, T1> value) throws T1 {
        try {
            return apply().noneMatch(item -> {
                try {
                    return value.apply(item);
                } catch (Throwable e) {
                    throw new StreamException(item, e);
                }
            });
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public <T1 extends Throwable> boolean anyMatch(@NotNull ThrowablePredicate<T, T1> value) throws T1 {
        try {
            return apply().anyMatch(item -> {
                try {
                    return value.apply(item);
                } catch (Throwable e) {
                    throw new StreamException(item, e);
                }
            });
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public <T1 extends Throwable> long count() throws T1 {
        try {
            return apply().count();
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public <T1 extends Throwable> void forEach(@NotNull ThrowableConsumer<T, T1> consumer) throws T1 {
        try {
            apply().forEach(item -> {
                try {
                    consumer.apply(item);
                } catch (Throwable e) {
                    throw new StreamException(item, e);
                }
            });
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public @NotNull <T1 extends Throwable> Optional<T> max(@NotNull Comparator<T> compare) throws T1 {
        try {
            return apply().max(compare);
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public @NotNull <T1 extends Throwable> Optional<T> min(@NotNull Comparator<T> compare) throws T1 {
        try {
            return apply().min(compare);
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public <T1 extends Throwable> T[] toArray(@NotNull IntFunction<T[]> generator) throws T1 {
        try {
            return apply().toArray(generator);
        } catch (StreamException e) {
            throw (T1) e.getCause();
        }
    }

    @Override
    public <T1 extends Throwable> Stream<T> toStream() throws T1 {
        return apply();
    }

    @Override
    public @NotNull MoseStream<T> include(@NotNull MoseStream<T> stream) {
        Supplier<Stream<T>> fromStream = stream::toStream;
        return new JavaStreamWrapper<>(() -> Stream.concat(apply(), fromStream.get()));
    }
}

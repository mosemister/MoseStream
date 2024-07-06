package org.mosestream.base;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mosestream.AbstractStream;
import org.mosestream.MoseStream;
import org.mosestream.action.stream.AbstractActionStream;
import org.mosestream.action.stream.SimpleActionStream;
import org.mosestream.iterator.ThrowableIterator;
import org.mosestream.iterator.ThrowableIteratorWrapper;

@ApiStatus.Internal
public abstract class AbstractBaseStream<V, S extends AbstractActionStream<V, S>> extends AbstractStream<V, S> {

    private final Iterable<V> iterable;

    public AbstractBaseStream(Iterable<V> iterable) {
        this.iterable = iterable;
    }

    @Override
    public <T> MoseStream<T> newAction() {
        return new SimpleActionStream<>(this);
    }

    @Override
    public AbstractStream<V, ?> baseStream() {
        return this;
    }

    @Override
    public @NotNull ThrowableIterator<V> iterator() {
        return new ThrowableIteratorWrapper<>(this.iterable.iterator());
    }
}

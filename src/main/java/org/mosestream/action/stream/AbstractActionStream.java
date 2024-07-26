package org.mosestream.action.stream;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mosestream.AbstractStream;
import org.mosestream.MoseStream;
import org.mosestream.iterator.MoseStreamIterator;
import org.mosestream.iterator.ThrowableIterator;

@ApiStatus.Internal
public abstract class AbstractActionStream<V, S extends AbstractActionStream<V, S>> extends AbstractStream<V, S> {

    private final AbstractStream<?, ?> base;
    private final AbstractStream<?, ?> parent;

    protected AbstractActionStream(AbstractStream<?, ?> from) {
        this.base = from.baseStream();
        this.parent = from;
    }

    @Override
    public <T> MoseStream<T> newAction() {
        return new SimpleActionStream<>(this);
    }

    public AbstractStream<?, ?> parent() {
        return this.parent;
    }

    @Override
    public AbstractStream<?, ?> baseStream() {
        return this.base;
    }

    @Override
    public <Throw extends Throwable> @NotNull ThrowableIterator<V, Throw> iterator() {
        return new MoseStreamIterator<>(this);
    }

}

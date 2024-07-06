package org.mosestream.action.stream;

import org.jetbrains.annotations.ApiStatus;
import org.mosestream.AbstractStream;

@ApiStatus.Internal
public class SimpleActionStream<V> extends AbstractActionStream<V, SimpleActionStream<V>> {
    public SimpleActionStream(AbstractStream<?, ?> from) {
        super(from);
    }

    @Override
    public SimpleActionStream<V> newInstance() {
        return new SimpleActionStream<>(this);
    }
}

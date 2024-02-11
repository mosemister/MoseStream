package org.mosestream.base;

import org.jetbrains.annotations.ApiStatus;
import org.mosestream.action.stream.SimpleActionStream;

@ApiStatus.Internal
public class SimpleBaseStream<V> extends AbstractBaseStream<V, SimpleActionStream<V>> {

    public SimpleBaseStream(Iterable<V> iterable) {
        super(iterable);
    }

    @Override
    public SimpleActionStream<V> newInstance() {
        return new SimpleActionStream<>(this);
    }
}

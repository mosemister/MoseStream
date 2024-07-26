package org.mosestream.iterator;

import org.jetbrains.annotations.ApiStatus;

import java.util.Iterator;

@ApiStatus.Internal
public class ThrowableIteratorWrapper<V, Throw extends Throwable> implements ThrowableIterator<V, Throw> {

    private final Iterator<V> iterator;

    public ThrowableIteratorWrapper(Iterator<V> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public V next() {
        return this.iterator.next();
    }
}

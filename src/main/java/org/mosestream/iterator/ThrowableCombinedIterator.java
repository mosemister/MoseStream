package org.mosestream.iterator;

import org.jetbrains.annotations.ApiStatus;

import java.util.Iterator;
import java.util.stream.Stream;

@ApiStatus.Internal
public class ThrowableCombinedIterator<Value, Throw extends Throwable> implements ThrowableIterator<Value, Throw> {

    private final ThrowableIterator<Value, Throw>[] iterators;

    public ThrowableCombinedIterator(ThrowableIterator<Value, Throw>... iterators) {
        this.iterators = iterators;
    }

    @Override
    public boolean hasNext() {
        return Stream.of(this.iterators).anyMatch(ThrowableIterator::hasNext);
    }

    @Override
    public Value next() throws Throw {
        for (var iterator : this.iterators) {
            if (!iterator.hasNext()) {
                continue;
            }
            return iterator.next();
        }
        throw new IndexOutOfBoundsException();
    }
}

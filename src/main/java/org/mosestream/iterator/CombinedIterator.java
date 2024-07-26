package org.mosestream.iterator;

import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

@ApiStatus.Internal
public class CombinedIterator<Value> implements Iterator<Value> {

    private final Iterator<Value>[] iterators;

    public CombinedIterator(Iterator<Value>... iterators) {
        this.iterators = iterators;
    }

    @Override
    public boolean hasNext() {
        return Stream.of(this.iterators).anyMatch(Iterator::hasNext);
    }

    @Override
    public Value next() {
        return Stream.of(this.iterators).filter(Iterator::hasNext).map(Iterator::next).findFirst().orElseThrow(IndexOutOfBoundsException::new);
    }
}

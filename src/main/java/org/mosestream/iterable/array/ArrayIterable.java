package org.mosestream.iterable.array;

import org.jetbrains.annotations.NotNull;
import org.mosestream.iterator.array.ArrayIterator;

import java.util.Iterator;

public class ArrayIterable<V> implements Iterable<V> {

    private final V[] array;

    @SafeVarargs
    public ArrayIterable(V... array) {
        this.array = array;
    }

    @NotNull
    @Override
    public Iterator<V> iterator() {
        return new ArrayIterator<>(array);
    }
}

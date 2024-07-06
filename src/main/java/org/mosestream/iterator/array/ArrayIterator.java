package org.mosestream.iterator.array;

import java.util.Iterator;

public class ArrayIterator<V> implements Iterator<V> {

    private final V[] array;
    private int index;

    @SafeVarargs
    public ArrayIterator(V... array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return this.index < array.length;
    }

    @Override
    public V next() {
        if (this.index >= array.length) {
            throw new ArrayIndexOutOfBoundsException(this.index);
        }
        V value = this.array[this.index];
        this.index++;
        return value;
    }
}

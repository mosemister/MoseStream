package org.mosestream.iterator.array;

import java.util.Iterator;

public class IntegerArrayIterator implements Iterator<Integer> {

    private final int[] array;
    private int index;

    public IntegerArrayIterator(int... array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return this.index < array.length;
    }

    @Override
    public Integer next() {
        if (this.index >= array.length) {
            throw new ArrayIndexOutOfBoundsException(this.index);
        }
        int value = this.array[this.index];
        this.index++;
        return value;
    }
}

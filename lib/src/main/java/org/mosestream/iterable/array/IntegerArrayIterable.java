package org.mosestream.iterable.array;

import org.jetbrains.annotations.NotNull;
import org.mosestream.iterator.array.IntegerArrayIterator;

import java.util.Iterator;

public class IntegerArrayIterable implements Iterable<Integer> {

    private final int[] array;

    public IntegerArrayIterable(int... array) {
        this.array = array;
    }

    @NotNull
    @Override
    public Iterator<Integer> iterator() {
        return new IntegerArrayIterator(array);
    }
}

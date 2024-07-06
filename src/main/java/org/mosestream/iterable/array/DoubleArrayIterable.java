package org.mosestream.iterable.array;

import org.jetbrains.annotations.NotNull;
import org.mosestream.iterator.array.DoubleArrayIterator;

import java.util.Iterator;

public class DoubleArrayIterable implements Iterable<Double> {

    private final double[] array;

    public DoubleArrayIterable(double... array) {
        this.array = array;
    }

    @NotNull
    @Override
    public Iterator<Double> iterator() {
        return new DoubleArrayIterator(array);
    }
}

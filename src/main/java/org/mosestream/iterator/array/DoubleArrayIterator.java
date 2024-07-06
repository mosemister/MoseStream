package org.mosestream.iterator.array;

import java.util.Iterator;

public class DoubleArrayIterator implements Iterator<Double> {

    private final double[] array;
    private int index;

    public DoubleArrayIterator(double... array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return this.index < array.length;
    }

    @Override
    public Double next() {
        if (this.index >= array.length) {
            throw new ArrayIndexOutOfBoundsException(this.index);
        }
        double value = this.array[this.index];
        this.index++;
        return value;
    }
}

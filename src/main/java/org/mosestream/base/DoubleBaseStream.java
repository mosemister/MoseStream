package org.mosestream.base;

import org.mosestream.action.stream.DoubleActionStream;
import org.mosestream.number.MoseDoubleStream;

public class DoubleBaseStream extends AbstractBaseStream<Double, DoubleActionStream> implements MoseDoubleStream {
    public DoubleBaseStream(Iterable<Double> iterable) {
        super(iterable);
    }

    @Override
    public DoubleActionStream newInstance() {
        return new DoubleActionStream(this);
    }


}

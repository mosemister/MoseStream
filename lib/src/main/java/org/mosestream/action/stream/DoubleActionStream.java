package org.mosestream.action.stream;

import org.mosestream.AbstractStream;
import org.mosestream.number.MoseDoubleStream;

public class DoubleActionStream extends AbstractActionStream<Double, DoubleActionStream> implements MoseDoubleStream {

    public DoubleActionStream(AbstractStream<?, ?> from) {
        super(from);
    }

    @Override
    public DoubleActionStream newInstance() {
        return new DoubleActionStream(this);
    }
}

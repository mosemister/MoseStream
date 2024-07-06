package org.mosestream.base;

import org.mosestream.action.stream.DoubleActionStream;
import org.mosestream.action.stream.IntegerActionStream;
import org.mosestream.number.MoseDoubleStream;
import org.mosestream.number.MoseIntegerStream;

public class IntegerBaseStream extends AbstractBaseStream<Integer, IntegerActionStream> implements MoseIntegerStream {
    public IntegerBaseStream(Iterable<Integer> iterable) {
        super(iterable);
    }

    @Override
    public IntegerActionStream newInstance() {
        return new IntegerActionStream(this);
    }


}

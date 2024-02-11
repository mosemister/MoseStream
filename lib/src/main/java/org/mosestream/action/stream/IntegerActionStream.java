package org.mosestream.action.stream;

import org.mosestream.AbstractStream;
import org.mosestream.number.MoseIntegerStream;

public class IntegerActionStream extends AbstractActionStream<Integer, IntegerActionStream> implements MoseIntegerStream {

    public IntegerActionStream(AbstractStream<?, ?> from) {
        super(from);
    }

    @Override
    public IntegerActionStream newInstance() {
        return new IntegerActionStream(this);
    }
}

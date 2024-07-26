package org.mosestream.iterator;

import org.jetbrains.annotations.ApiStatus;

import java.util.Iterator;
import java.util.function.Function;

@ApiStatus.Internal
public class ThrowableToIteratorWrapper<Value, Throw extends Throwable> implements Iterator<Value> {

    private final ThrowableIterator<Value, Throw> iterator;
    private final Function<Throw, RuntimeException> toRuntime;

    public ThrowableToIteratorWrapper(ThrowableIterator<Value, Throw> iterator, Function<Throw, RuntimeException> toRuntime) {
        this.iterator = iterator;
        this.toRuntime = toRuntime;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Value next() {
        try {
            return this.iterator.next();
        } catch (Throwable e) {
            if (e instanceof RuntimeException runtime) {
                throw runtime;
            }
            throw this.toRuntime.apply((Throw) e);
        }
    }
}

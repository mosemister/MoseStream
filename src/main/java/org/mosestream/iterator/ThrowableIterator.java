package org.mosestream.iterator;

import org.jetbrains.annotations.CheckReturnValue;

import java.util.Iterator;
import java.util.function.Function;

public interface ThrowableIterator<T, Throw extends Throwable> {

    @CheckReturnValue
    boolean hasNext();

    T next() throws Throw;

    default Iterator<T> toStandard(Function<Throw, RuntimeException> toRuntime){
        return new ThrowableToIteratorWrapper<>(this, toRuntime);
    }


}

package org.mosestream.iterator;

import org.jetbrains.annotations.CheckReturnValue;

public interface ThrowableIterator<T> {

    @CheckReturnValue
    boolean hasNext();

    T next() throws Throwable;


}

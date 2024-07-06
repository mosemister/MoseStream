package org.mosestream.lamda;

import org.jetbrains.annotations.UnknownNullability;

public interface ThrowableConsumer<V, T extends Throwable> {

    void apply(@UnknownNullability V value) throws T;

}

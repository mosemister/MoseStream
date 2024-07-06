package org.mosestream.lamda;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnknownNullability;

@ApiStatus.OverrideOnly
public interface ThrowableFunction<V, N, T extends Throwable> {

    N map(@UnknownNullability V value) throws T;

}

package org.mosestream.lamda;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.OverrideOnly
public interface ThrowableSupplier<V, T extends Throwable> {

    V get() throws T;
}

package org.mosestream.lamda;

import org.jetbrains.annotations.NotNull;

public class ThrowableSingleton<V, T extends Throwable> implements ThrowableSupplier<V, T> {

    private final ThrowableSupplier<V, T> getter;
    private V cached;

    public ThrowableSingleton(@NotNull ThrowableSupplier<V, T> supplier) {
        this.getter = supplier;
    }

    private synchronized V getCached() throws T {
        if (this.cached == null) {
            this.cached = getter.get();
        }
        return this.cached;
    }

    @Override
    public V get() throws T {
        if (this.cached == null) {
            getCached();
        }
        return this.cached;
    }
}

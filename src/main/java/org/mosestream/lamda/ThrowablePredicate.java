package org.mosestream.lamda;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnknownNullability;

@ApiStatus.OverrideOnly
public interface ThrowablePredicate<V, T extends Throwable> {

    boolean apply(@UnknownNullability V value) throws T;
}

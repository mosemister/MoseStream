package org.mosestream.lamda;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.UnknownNullability;

@ApiStatus.OverrideOnly
public interface ThrowableBiFunction<First, Second, Result, T extends Throwable> {

    Result map(@UnknownNullability First first, @UnknownNullability Second second) throws T;

}

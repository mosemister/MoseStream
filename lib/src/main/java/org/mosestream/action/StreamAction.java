package org.mosestream.action;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.CheckReturnValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.misc.ActionTarget;

@ApiStatus.Internal
public interface StreamAction<Income, Result, T extends Throwable> {

    @UnknownNullability
    @CheckReturnValue
    StreamActionResult<Result> apply(@UnknownNullability Income value) throws T;

    @NotNull
    @CheckReturnValue
    ActionTarget target();

}

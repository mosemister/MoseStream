package org.mosestream.action;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public interface StreamActionResult<Result> {

    boolean shouldConsume();

    @Nullable Result result();
}

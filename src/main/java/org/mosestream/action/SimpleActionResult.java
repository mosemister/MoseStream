package org.mosestream.action;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public class SimpleActionResult<Result> implements StreamActionResult<Result> {

    private final boolean shouldConsume;
    private final Result result;

    public SimpleActionResult(Result result, boolean shouldConsume) {
        this.result = result;
        this.shouldConsume = shouldConsume;
    }


    @Override
    public boolean shouldConsume() {
        return this.shouldConsume;
    }

    @Override
    public @Nullable Result result() {
        return this.result;
    }
}

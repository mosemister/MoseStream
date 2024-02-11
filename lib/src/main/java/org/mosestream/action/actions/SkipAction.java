package org.mosestream.action.actions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.SimpleActionResult;
import org.mosestream.action.StreamAction;
import org.mosestream.action.StreamActionResult;
import org.mosestream.action.misc.ActionTarget;

public class SkipAction<V, T extends Throwable> implements StreamAction<V, V, T> {

    private final long limit;
    private long found;

    public SkipAction(long limit) {
        this.limit = limit;
    }

    @Override
    public @UnknownNullability StreamActionResult<V> apply(@UnknownNullability V value) throws T {
        found++;
        if (found <= limit) {
            return new SimpleActionResult<>(null, true);
        }
        return new SimpleActionResult<>(value, false);
    }

    @Override
    public @NotNull ActionTarget target() {
        return ActionTarget.SINGLE;
    }
}

package org.mosestream.action.actions;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.SimpleActionResult;
import org.mosestream.action.StreamAction;
import org.mosestream.action.StreamActionResult;
import org.mosestream.action.misc.ActionTarget;
import org.mosestream.lamda.ThrowablePredicate;

@ApiStatus.Internal
public class FilterAction<V, T extends Throwable> implements StreamAction<V, V, T> {

    private final ThrowablePredicate<V, T> filter;

    public FilterAction(ThrowablePredicate<V, T> predicate) {
        this.filter = predicate;
    }


    @Override
    public @UnknownNullability StreamActionResult<V> apply(@UnknownNullability V value) throws T {
        if (this.filter.apply(value)) {
            return new SimpleActionResult<>(value, false);
        }

        return new SimpleActionResult<>(null, true);
    }

    @Override
    public @NotNull ActionTarget target() {
        return ActionTarget.SINGLE;
    }
}

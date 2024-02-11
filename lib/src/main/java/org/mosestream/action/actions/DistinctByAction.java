package org.mosestream.action.actions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.SimpleActionResult;
import org.mosestream.action.StreamAction;
import org.mosestream.action.StreamActionResult;
import org.mosestream.action.misc.ActionTarget;
import org.mosestream.lamda.ThrowableFunction;

import java.util.Collection;
import java.util.LinkedHashSet;

public class DistinctByAction<V, T extends Throwable> implements StreamAction<V, V, T> {

    private final Collection<Object> values = new LinkedHashSet<>();
    private final ThrowableFunction<V, ?, T> function;

    public DistinctByAction(ThrowableFunction<V, ?, T> function) {
        this.function = function;
    }


    @Override
    public @UnknownNullability StreamActionResult<V> apply(@UnknownNullability V value) throws T {
        Object mapped = this.function.map(value);
        if (values.contains(mapped)) {
            return new SimpleActionResult<>(null, true);
        }
        values.add(mapped);
        return new SimpleActionResult<>(value, false);
    }

    @Override
    public @NotNull ActionTarget target() {
        return ActionTarget.SINGLE;
    }
}

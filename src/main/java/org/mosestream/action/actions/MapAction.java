package org.mosestream.action.actions;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.SimpleActionResult;
import org.mosestream.action.StreamAction;
import org.mosestream.action.StreamActionResult;
import org.mosestream.action.misc.ActionTarget;
import org.mosestream.lamda.ThrowableFunction;

@ApiStatus.Internal
public class MapAction<Value, Mapped, T extends Throwable> implements StreamAction<Value, Mapped, T> {

    private final ThrowableFunction<Value, Mapped, T> mapper;

    public MapAction(ThrowableFunction<Value, Mapped, T> function) {
        this.mapper = function;
    }

    @Override
    public @UnknownNullability StreamActionResult<Mapped> apply(@UnknownNullability Value value) throws T {
        return new SimpleActionResult<>(mapper.map(value), false);
    }

    @Override
    public @NotNull ActionTarget target() {
        return ActionTarget.SINGLE;
    }
}

package org.mosestream.action.actions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.SimpleActionResult;
import org.mosestream.action.StreamAction;
import org.mosestream.action.StreamActionResult;
import org.mosestream.action.misc.ActionTarget;
import org.mosestream.lamda.ThrowableConsumer;

public class EachAction<V, T extends Throwable> implements StreamAction<V, V, T> {

    private final ThrowableConsumer<V, T> consumer;

    public EachAction(ThrowableConsumer<V, T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public @UnknownNullability StreamActionResult<V> apply(@UnknownNullability V value) throws T {
        this.consumer.apply(value);
        return new SimpleActionResult<>(value, false);
    }

    @Override
    public @NotNull ActionTarget target() {
        return ActionTarget.WHOLE;
    }
}

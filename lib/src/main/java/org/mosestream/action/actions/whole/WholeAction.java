package org.mosestream.action.actions.whole;

import org.jetbrains.annotations.NotNull;
import org.mosestream.action.StreamAction;
import org.mosestream.action.misc.ActionTarget;
import org.mosestream.iterator.ThrowableIterator;

public interface WholeAction<Value, Mapped, T extends Throwable> extends StreamAction<ThrowableIterator<Value>, Iterable<Mapped>, T> {

    @Override
    default @NotNull ActionTarget target() {
        return ActionTarget.WHOLE;
    }
}

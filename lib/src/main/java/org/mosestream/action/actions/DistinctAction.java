package org.mosestream.action.actions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.SimpleActionResult;
import org.mosestream.action.StreamAction;
import org.mosestream.action.StreamActionResult;
import org.mosestream.action.misc.ActionTarget;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.BiPredicate;

public class DistinctAction<V, T extends Throwable> implements StreamAction<V, V, T> {

    private final BiPredicate<V, V> compare;
    private final Collection<V> values = new HashSet<>();

    public DistinctAction(BiPredicate<V, V> compare) {
        this.compare = compare;
    }

    @Override
    public @UnknownNullability StreamActionResult<V> apply(@UnknownNullability V value) {
        if (values.stream().anyMatch(compare -> this.compare.test(value, compare))) {
            return new SimpleActionResult<>(null, true);
        }
        values.add(value);
        return new SimpleActionResult<>(value, false);
    }

    @Override
    public @NotNull ActionTarget target() {
        return ActionTarget.SINGLE;
    }
}

package org.mosestream.action.actions.whole;

import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.SimpleActionResult;
import org.mosestream.action.StreamActionResult;
import org.mosestream.iterator.ThrowableIterator;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SortAction<V, T extends Throwable> implements WholeAction<V, V, T> {

    private final Comparator<V> compare;

    public SortAction(Comparator<V> compare) {
        this.compare = compare;
    }

    @Override
    public @UnknownNullability StreamActionResult<Iterable<V>> apply(@UnknownNullability ThrowableIterator<V> value) throws T {
        List<V> list = new LinkedList<>();
        try {
            while (value.hasNext()) {
                list.add(value.next());
            }
            list.sort(this.compare);
        } catch (Throwable e) {
            throw (T) e;
        }
        return new SimpleActionResult<>(list, false);
    }
}

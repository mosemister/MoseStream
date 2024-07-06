package org.mosestream.action.actions.whole;

import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.SimpleActionResult;
import org.mosestream.action.StreamActionResult;
import org.mosestream.iterator.ThrowableIterator;
import org.mosestream.lamda.ThrowableFunction;

import java.util.LinkedList;
import java.util.List;

public class FlatMapAction<Value, Map, T extends Throwable> implements WholeAction<Value, Map, T> {

    private final ThrowableFunction<Value, Iterable<Map>, T> function;

    public FlatMapAction(ThrowableFunction<Value, Iterable<Map>, T> function) {
        this.function = function;
    }

    @Override
    public @UnknownNullability StreamActionResult<Iterable<Map>> apply(@UnknownNullability ThrowableIterator<Value> value) throws T {
        List<Map> result = new LinkedList<>();
        try {
            while (value.hasNext()) {
                Value iterValue = value.next();
                Iterable<Map> mapped = this.function.map(iterValue);
                for (Map mappedValue : mapped) {
                    result.add(mappedValue);
                }
            }
        } catch (Throwable e) {
            throw (T) e;
        }
        return new SimpleActionResult<>(result, false);
    }
}

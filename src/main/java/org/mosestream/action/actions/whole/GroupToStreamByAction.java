package org.mosestream.action.actions.whole;

import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.MoseStream;
import org.mosestream.action.SimpleActionResult;
import org.mosestream.action.StreamActionResult;
import org.mosestream.iterator.ThrowableIterator;
import org.mosestream.lamda.ThrowableFunction;

import java.util.LinkedHashMap;
import java.util.Map;

public class GroupToStreamByAction<Value, By, Throw extends Throwable> implements WholeAction<Value, MoseStream<Value>, Throw> {

    private ThrowableFunction<Value, By, Throw> selector;
    private final Map<By, MoseStream<Value>> values = new LinkedHashMap<>();


    public GroupToStreamByAction(ThrowableFunction<Value, By, Throw> selector) {
        this.selector = selector;
    }

    @Override
    public @UnknownNullability StreamActionResult<Iterable<MoseStream<Value>>> apply(@UnknownNullability ThrowableIterator<Value, Throw> value) throws Throw {
        try {
            while (value.hasNext()) {
                Value next = value.next();
                By by = this.selector.map(next);

                if (values.containsKey(by)) {
                    var stream = values.get(by);
                    var replacement = stream.include(MoseStream.stream(next));
                    values.replace(by, replacement);
                    continue;
                }
                values.put(by, MoseStream.stream(next));
            }
            return new SimpleActionResult<>(values.values(), false);
        } catch (Throwable e) {
            throw (Throw) e;
        }
    }
}

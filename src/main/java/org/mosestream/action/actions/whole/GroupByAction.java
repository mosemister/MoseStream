package org.mosestream.action.actions.whole;

import org.jetbrains.annotations.UnknownNullability;
import org.mosestream.action.SimpleActionResult;
import org.mosestream.action.StreamActionResult;
import org.mosestream.iterator.ThrowableIterator;
import org.mosestream.lamda.ThrowableFunction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GroupByAction<V, K, C, E, T extends Throwable> implements WholeAction<V, E, T> {

    private final Collector<V, C, E> collector;
    private final ThrowableFunction<V, K, T> to;
    private final Map<K, C> values = new LinkedHashMap<>();

    public GroupByAction(Collector<V, C, E> collector, ThrowableFunction<V, K, T> to) {
        this.collector = collector;
        this.to = to;
    }

    @Override
    public @UnknownNullability StreamActionResult<Iterable<E>> apply(@UnknownNullability ThrowableIterator<V> value) throws T {
        try {
            while (value.hasNext()) {
                V iterNext = value.next();
                K key = this.to.map(iterNext);
                if (!this.values.containsKey(key)) {
                    this.values.put(key, this.collector.supplier().get());
                }
                C collection = this.values.get(key);
                this.collector.accumulator().accept(collection, iterNext);
            }
            List<E> result = this.values.values().stream().map(collection -> this.collector.finisher().apply(collection)).collect(Collectors.toList());
            return new SimpleActionResult<>(result, false);
        } catch (Throwable e) {
            throw (T) e;
        }
    }
}

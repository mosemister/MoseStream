package org.mosestream.stream.gatherer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Stream;

public class GroupByGatherer<Value, By> implements Gatherer<Value, Map<By, Stream<Value>>, Stream<Value>> {

    private final Function<Value, By> selector;

    GroupByGatherer(Function<Value, By> selector) {
        this.selector = selector;
    }

    @Override
    public Supplier<Map<By, Stream<Value>>> initializer() {
        return ConcurrentHashMap::new;
    }

    @Override
    public Integrator<Map<By, Stream<Value>>, Value, Stream<Value>> integrator() {
        return Integrator.ofGreedy((state, element, downstream) -> {
            By by = selector.apply(element);
            if (state.containsKey(by)) {
                Stream<Value> stream = state.get(by);
                stream = Stream.concat(stream, Stream.of(element));
                state.replace(by, stream);
                return true;
            }
            Stream<Value> stream = Stream.of(element);
            state.replace(by, stream);
            return true;
        });
    }

    @Override
    public BiConsumer<Map<By, Stream<Value>>, Downstream<? super Stream<Value>>> finisher() {
        return (state, downstream) -> state.values().forEach(downstream::push);
    }
}

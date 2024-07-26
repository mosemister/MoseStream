package org.mosestream.stream.gatherer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public class DistinctByGatherer<Value, By> implements Gatherer<Value, Set<By>, Value> {

    private final Function<Value, By> selector;

    DistinctByGatherer(Function<Value, By> selector) {
        this.selector = selector;
    }

    @Override
    public Supplier<Set<By>> initializer() {
        return HashSet::new;
    }

    @Override
    public Integrator<Set<By>, Value, Value> integrator() {
        return Integrator.ofGreedy((state, element, downstream) -> {
            By by = selector.apply(element);
            if (state.contains(by)) {
                return true;
            }
            state.add(by);
            downstream.push(element);
            return true;
        });
    }
}

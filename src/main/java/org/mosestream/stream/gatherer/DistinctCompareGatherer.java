package org.mosestream.stream.gatherer;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public class DistinctCompareGatherer<Value> implements Gatherer<Value, Set<Value>, Value> {

    private final BiPredicate<Value, Value> selector;

    DistinctCompareGatherer(BiPredicate<Value, Value> selector) {
        this.selector = selector;
    }

    @Override
    public Supplier<Set<Value>> initializer() {
        return HashSet::new;
    }

    @Override
    public Integrator<Set<Value>, Value, Value> integrator() {
        return Integrator.ofGreedy((state, element, downstream) -> {
            boolean hasValue = state.stream().anyMatch(compare -> selector.test(element, compare));
            if (hasValue) {
                return true;
            }
            state.add(element);
            downstream.push(element);
            return true;
        });
    }
}

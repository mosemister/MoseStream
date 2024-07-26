package org.mosestream.stream.gatherer;

import java.util.function.BiPredicate;
import java.util.function.Function;

public class MoseStreamGatherers {

    public static <Value, By> GroupByGatherer<Value, By> groupBy(Function<Value, By> selector) {
        return new GroupByGatherer<>(selector);
    }

    public static <Value, By> DistinctByGatherer<Value, By> distinctBy(Function<Value, By> selector){
        return new DistinctByGatherer<>(selector);
    }

    public static <Value> DistinctCompareGatherer<Value> distinctCompare(BiPredicate<Value, Value> compare){
        return new DistinctCompareGatherer<>(compare);
    }


}

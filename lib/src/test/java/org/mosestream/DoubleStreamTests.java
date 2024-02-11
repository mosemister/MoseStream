package org.mosestream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mosestream.number.MoseDoubleStream;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DoubleStreamTests {

    @Test
    public void canFilter() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3, 4);

        //act
        List<Double> result = stream.filter(value -> value == 3).collect(Collectors.toList());

        //assert
        Assertions.assertEquals(Collections.singletonList(3.0), result);
    }

    @Test
    public void canThrowWhenFiltering() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3, 4);

        //act
        Assertions.assertThrows(IOException.class, () -> stream.filter(value -> {
            throw new IOException("Exception");
        }).toList());
    }

    @Test
    public void canFilterOut() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3);

        //act
        List<Double> result = stream.filterOut(value -> value == 3).collect(Collectors.toList());

        //assert
        Assertions.assertEquals(Arrays.asList(1.0, 2.0), result);
    }

    @Test
    public void canMap() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3);

        //act
        List<String> result = stream.map(Object::toString).toList();

        //assert
        Assertions.assertEquals(Arrays.asList("1.0", "2.0", "3.0"), result);
    }

    @Test
    public void canThrowWhenMapped() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3);

        //act
        Assertions.assertThrows(IOException.class, () -> stream.map((value -> {
            throw new IOException();
        })).toSet());
    }

    @Test
    public void canGetFirstValue() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2);

        //act
        Optional<Double> opFirst = stream.first();

        //assert
        Assertions.assertTrue(opFirst.isPresent());
        Assertions.assertEquals(1, opFirst.get());
    }

    @Test
    public void canGetFirstFilteredValue() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2);

        //act
        Optional<Double> opFirst = stream.filter(value -> value == 2).first();

        //assert
        Assertions.assertTrue(opFirst.isPresent());
        Assertions.assertEquals(2, opFirst.get());
    }

    @Test
    public void canGetEmptyValue() {
        MoseDoubleStream stream = MoseStream.streamDouble();

        //act
        Optional<Double> opFirst = stream.first();

        //assert
        Assertions.assertFalse(opFirst.isPresent());
    }

    @Test
    public void canFlatMapValue() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3);

        //act
        List<Character> characterArray = stream.flatMap(value -> {
            List<Character> array = new LinkedList<>();
            array.add(value.toString().charAt(0));
            array.add(((char) (Math.round(value) + 64)));
            return array;
        }).toList();

        //assert
        Assertions.assertEquals(6, characterArray.size());
        Assertions.assertEquals(Arrays.asList('1', 'A', '2', 'B', '3', 'C'), characterArray);
    }

    @Test
    public void canDistinctBy() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3, 4);

        //act
        List<Double> result = stream.distinctBy(value -> value % 2).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1.0, 2.0), result);
    }

    @Test
    public void canDistinct() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3, 1);

        //act
        List<Double> result = stream.distinct().toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1.0, 2.0, 3.0), result);
    }

    @Test
    public void canDistinctCustom() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3, 1);

        //act
        List<Double> result = stream.distinct(Objects::equals).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1.0, 2.0, 3.0), result);
    }

    @Test
    public void allMatchWithTrue() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 1, 1, 1);

        //act
        boolean result = stream.allMatch(v -> v == 1);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void allMatchWithOneFalse() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 0, 1, 1);

        //act
        boolean result = stream.allMatch(v -> v == 1);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void noneMatchWithFalse() {
        MoseDoubleStream stream = MoseStream.streamDouble(0, 0, 0, 0);

        //act
        boolean result = stream.noneMatch(v -> v == 1);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void noneMatchWithOneTrue() {
        MoseDoubleStream stream = MoseStream.streamDouble(0, 0, 1, 0);

        //act
        boolean result = stream.noneMatch(v -> v == 1);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void anyMatchWithFalse() {
        MoseDoubleStream stream = MoseStream.streamDouble(0, 0, 0, 0);

        //act
        boolean result = stream.anyMatch(v -> v == 1);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void anyMatchWithOneTrue() {
        MoseDoubleStream stream = MoseStream.streamDouble(0, 0, 1, 0);

        //act
        boolean result = stream.anyMatch(v -> v == 1);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void canCount() {
        MoseDoubleStream stream = MoseStream.streamDouble(0, 0, 0, 0);

        //act
        long result = stream.count();

        //assert
        Assertions.assertEquals(4, result);
    }

    @Test
    public void canExecuteForEach() {
        MoseDoubleStream stream = MoseStream.streamDouble(0, 0, 0, 0);
        List<Double> found = new LinkedList<>();

        //act
        stream.forEach(found::add);

        //assert
        Assertions.assertEquals(Arrays.asList(0.0, 0.0, 0.0, 0.0), found);
    }

    @Test
    public void canExecuteEach() {
        MoseDoubleStream stream = MoseStream.streamDouble(0, 0, 0, 0);
        List<Double> found = new LinkedList<>();

        //act
        List<Double> result = stream.each(found::add).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(0.0, 0.0, 0.0, 0.0), found);
        Assertions.assertEquals(found, result);
    }

    @Test
    public void canLimit() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3, 4);

        //act
        List<Double> result = stream.limit(2).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1.0, 2.0), result);
    }

    @Test
    public void canSkip() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3, 4);

        //act
        List<Double> result = stream.skip(2).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(3.0, 4.0), result);
    }

    @Test
    public void canCreateArrayWithParameter() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2);

        //act
        Double[] array = stream.toArray(Double[]::new);

        //assert
        Assertions.assertArrayEquals(new Double[]{1.0, 2.0}, array);
    }

    @Test
    public void canCreateArray() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2);

        //act
        Double[] array = stream.toArray();

        //assert
        Assertions.assertArrayEquals(new Double[]{1.0, 2.0}, array);
    }

    @Test
    public void canCreateDoubleArray() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2);

        //act
        double[] array = stream.toDoubleArray();

        //assert
        Assertions.assertArrayEquals(new double[]{1.0, 2.0}, array);
    }

    @Test
    public void canSort() {
        MoseDoubleStream stream = MoseStream.streamDouble(4, 2, 3, 1);

        //act
        List<Double> result = stream.sorted(Comparator.naturalOrder()).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1.0, 2.0, 3.0, 4.0), result);
    }

    @Test
    public void canGetMaxElement() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 4, 3);

        //act
        Optional<Double> opResult = stream.max();

        //assert
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals(4.0, opResult.get());
    }

    @Test
    public void canGetMinElement() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 4, 3);

        //act
        Optional<Double> opResult = stream.min();

        //assert
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals(1.0, opResult.get());
    }

    @Test
    public void canReduceWithInitialValue() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3);

        //act
        String result = stream.reduce("", (incoming, current) -> current + incoming);

        //assert
        Assertions.assertEquals("1.02.03.0", result);
    }

    @Test
    public void canReduce() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3);

        //act
        Optional<Double> opResult = stream.reduce(Double::sum);

        //assert
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals(6, opResult.get());
    }

    @Test
    public void canGoToStream() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2);

        //act
        List<Double> result = stream.toStream().collect(Collectors.toList());

        //assert
        Assertions.assertEquals(Arrays.asList(1.0, 2.0), result);
    }

    @Test
    public void canGroupBy() {
        MoseDoubleStream stream = MoseStream.streamDouble(1, 2, 3, 4);

        //act
        Set<List<Double>> result = stream.groupBy(Collectors.toList(), value -> value % 2).toSet();

        //assert
        Assertions.assertEquals(2, result.size());
        Iterator<List<Double>> iterator = result.iterator();
        List<Double> firstArray = iterator.next();
        List<Double> secondArray = iterator.next();

        if (firstArray.get(0) == 1) {
            Assertions.assertEquals(Arrays.asList(1.0, 3.0), firstArray);
            Assertions.assertEquals(Arrays.asList(2.0, 4.0), secondArray);
        } else {
            Assertions.assertEquals(Arrays.asList(1.0, 3.0), secondArray);
            Assertions.assertEquals(Arrays.asList(2.0, 4.0), firstArray);
        }
    }
}

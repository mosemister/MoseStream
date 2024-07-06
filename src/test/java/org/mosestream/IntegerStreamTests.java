package org.mosestream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mosestream.number.MoseIntegerStream;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class IntegerStreamTests {

    @Test
    public void canFilter() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3, 4);

        //act
        List<Integer> result = stream.filter(value -> value == 3).collect(Collectors.toList());

        //assert
        Assertions.assertEquals(Collections.singletonList(3), result);
    }

    @Test
    public void canThrowWhenFiltering() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3, 4);

        //act
        Assertions.assertThrows(IOException.class, () -> stream.filter(value -> {
            throw new IOException("Exception");
        }).toList());
    }

    @Test
    public void canFilterOut() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3);

        //act
        List<Integer> result = stream.filterOut(value -> value == 3).collect(Collectors.toList());

        //assert
        Assertions.assertEquals(Arrays.asList(1, 2), result);
    }

    @Test
    public void canMap() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3);

        //act
        List<String> result = stream.map(Object::toString).toList();

        //assert
        Assertions.assertEquals(Arrays.asList("1", "2", "3"), result);
    }

    @Test
    public void canThrowWhenMapped() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3);

        //act
        Assertions.assertThrows(IOException.class, () -> stream.map((value -> {
            throw new IOException();
        })).toSet());
    }

    @Test
    public void canGetFirstValue() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2);

        //act
        Optional<Integer> opFirst = stream.first();

        //assert
        Assertions.assertTrue(opFirst.isPresent());
        Assertions.assertEquals(1, opFirst.get());
    }

    @Test
    public void canGetFirstFilteredValue() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2);

        //act
        Optional<Integer> opFirst = stream.filter(value -> value == 2).first();

        //assert
        Assertions.assertTrue(opFirst.isPresent());
        Assertions.assertEquals(2, opFirst.get());
    }

    @Test
    public void canGetEmptyValue() {
        MoseIntegerStream stream = MoseStream.streamInteger();

        //act
        Optional<Integer> opFirst = stream.first();

        //assert
        Assertions.assertFalse(opFirst.isPresent());
    }

    @Test
    public void canFlatMapValue() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3);

        //act
        List<Character> characterArray = stream.flatMap(value -> {
            List<Character> array = new LinkedList<>();
            array.add(value.toString().charAt(0));
            array.add(((char) (value + 64)));
            return array;
        }).toList();

        //assert
        Assertions.assertEquals(6, characterArray.size());
        Assertions.assertEquals(Arrays.asList('1', 'A', '2', 'B', '3', 'C'), characterArray);
    }

    @Test
    public void canDistinctBy() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3, 4);

        //act
        List<Integer> result = stream.distinctBy(value -> value % 2).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1, 2), result);
    }

    @Test
    public void canDistinct() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3, 1);

        //act
        List<Integer> result = stream.distinct().toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1, 2, 3), result);
    }

    @Test
    public void canDistinctCustom() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3, 1);

        //act
        List<Integer> result = stream.distinct(Objects::equals).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1, 2, 3), result);
    }

    @Test
    public void allMatchWithTrue() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 1, 1, 1);

        //act
        boolean result = stream.allMatch(v -> v == 1);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void allMatchWithOneFalse() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 0, 1, 1);

        //act
        boolean result = stream.allMatch(v -> v == 1);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void noneMatchWithFalse() {
        MoseIntegerStream stream = MoseStream.streamInteger(0, 0, 0, 0);

        //act
        boolean result = stream.noneMatch(v -> v == 1);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void noneMatchWithOneTrue() {
        MoseIntegerStream stream = MoseStream.streamInteger(0, 0, 1, 0);

        //act
        boolean result = stream.noneMatch(v -> v == 1);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void anyMatchWithFalse() {
        MoseIntegerStream stream = MoseStream.streamInteger(0, 0, 0, 0);

        //act
        boolean result = stream.anyMatch(v -> v == 1);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void anyMatchWithOneTrue() {
        MoseIntegerStream stream = MoseStream.streamInteger(0, 0, 1, 0);

        //act
        boolean result = stream.anyMatch(v -> v == 1);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void canCount() {
        MoseIntegerStream stream = MoseStream.streamInteger(0, 0, 0, 0);

        //act
        long result = stream.count();

        //assert
        Assertions.assertEquals(4, result);
    }

    @Test
    public void canExecuteForEach() {
        MoseIntegerStream stream = MoseStream.streamInteger(0, 0, 0, 0);
        List<Integer> found = new LinkedList<>();

        //act
        stream.forEach(found::add);

        //assert
        Assertions.assertEquals(Arrays.asList(0, 0, 0, 0), found);
    }

    @Test
    public void canExecuteEach() {
        MoseIntegerStream stream = MoseStream.streamInteger(0, 0, 0, 0);
        List<Integer> found = new LinkedList<>();

        //act
        List<Integer> result = stream.each(found::add).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(0, 0, 0, 0), found);
        Assertions.assertEquals(found, result);
    }

    @Test
    public void canLimit() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3, 4);

        //act
        List<Integer> result = stream.limit(2).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1, 2), result);
    }

    @Test
    public void canSkip() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3, 4);

        //act
        List<Integer> result = stream.skip(2).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(3, 4), result);
    }

    @Test
    public void canCreateArrayWithParameter() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2);

        //act
        Integer[] array = stream.toArray(Integer[]::new);

        //assert
        Assertions.assertArrayEquals(new Integer[]{1, 2}, array);
    }

    @Test
    public void canCreateArray() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2);

        //act
        Integer[] array = stream.toArray();

        //assert
        Assertions.assertArrayEquals(new Integer[]{1, 2}, array);
    }

    @Test
    public void canCreateDoubleArray() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2);

        //act
        double[] array = stream.toDoubleArray();

        //assert
        Assertions.assertArrayEquals(new double[]{1.0, 2.0}, array);
    }

    @Test
    public void canSort() {
        MoseIntegerStream stream = MoseStream.streamInteger(4, 2, 3, 1);

        //act
        List<Integer> result = stream.sorted(Comparator.naturalOrder()).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1, 2, 3, 4), result);
    }

    @Test
    public void canGetMaxElement() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 4, 3);

        //act
        Optional<Integer> opResult = stream.max();

        //assert
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals(4, opResult.get());
    }

    @Test
    public void canGetMinElement() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 4, 3);

        //act
        Optional<Integer> opResult = stream.min();

        //assert
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals(1, opResult.get());
    }

    @Test
    public void canReduceWithInitialValue() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3);

        //act
        String result = stream.reduce("", (incoming, current) -> current + incoming);

        //assert
        Assertions.assertEquals("123", result);
    }

    @Test
    public void canReduce() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3);

        //act
        Optional<Integer> opResult = stream.reduce(Integer::sum);

        //assert
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals(6, opResult.get());
    }

    @Test
    public void canGoToStream() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2);

        //act
        List<Integer> result = stream.toStream().collect(Collectors.toList());

        //assert
        Assertions.assertEquals(Arrays.asList(1, 2), result);
    }

    @Test
    public void canGroupBy() {
        MoseIntegerStream stream = MoseStream.streamInteger(1, 2, 3, 4);

        //act
        Set<List<Integer>> result = stream.groupBy(Collectors.toList(), value -> value % 2).toSet();

        //assert
        Assertions.assertEquals(2, result.size());
        Iterator<List<Integer>> iterator = result.iterator();
        List<Integer> firstArray = iterator.next();
        List<Integer> secondArray = iterator.next();

        if (firstArray.get(0) == 1) {
            Assertions.assertEquals(Arrays.asList(1, 3), firstArray);
            Assertions.assertEquals(Arrays.asList(2, 4), secondArray);
        } else {
            Assertions.assertEquals(Arrays.asList(1, 3), secondArray);
            Assertions.assertEquals(Arrays.asList(2, 4), firstArray);
        }
    }
}

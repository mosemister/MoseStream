package org.mosestream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SimpleStreamTests {

    @Test
    public void canFilter() {
        List<String> list = Arrays.asList("one", "two", "three");
        MoseStream<String> stream = MoseStream.stream(list);

        //act
        List<String> result = stream.filter(string -> string.equals("three")).collect(Collectors.toList());

        //assert
        Assertions.assertEquals(Collections.singletonList("three"), result);
    }

    @Test
    public void canThrowWhenFiltering() {
        List<String> list = Arrays.asList("one", "two", "three");
        MoseStream<String> stream = MoseStream.stream(list);

        //act
        Assertions.assertThrows(IOException.class, () -> stream.filter(value -> {
            throw new IOException("Exception");
        }).toList());
    }

    @Test
    public void canFilterOut() {
        List<String> list = Arrays.asList("one", "two", "three");
        MoseStream<String> stream = MoseStream.stream(list);

        //act
        List<String> result = stream.filterOut(string -> string.equals("three")).collect(Collectors.toList());

        //assert
        Assertions.assertEquals(Arrays.asList("one", "two"), result);
    }

    @Test
    public void canMap() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        MoseStream<Integer> stream = MoseStream.stream(list);

        //act
        List<String> result = stream.map(Object::toString).toList();

        //assert
        Assertions.assertEquals(Arrays.asList("1", "2", "3"), result);
    }

    @Test
    public void canMapToDouble() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        MoseStream<Integer> stream = MoseStream.stream(list);

        //act
        List<Double> result = stream.mapToDouble(value -> value + 0.1).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1.1, 2.1, 3.1), result);
    }

    @Test
    public void canMapToInteger() {
        List<String> list = Arrays.asList("a", "ab", "abc");
        MoseStream<String> stream = MoseStream.stream(list);

        //act
        List<Integer> result = stream.mapToInteger(String::length).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1, 2, 3), result);
    }

    @Test
    public void canThrowWhenMapped() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        MoseStream<Integer> stream = MoseStream.stream(list);

        //act
        Assertions.assertThrows(IOException.class, () -> stream.map((value -> {
            throw new IOException();
        })).toSet());
    }

    @Test
    public void canGetFirstValue() {
        MoseStream<String> stream = MoseStream.stream("first", "second");

        //act
        Optional<String> opFirst = stream.first();

        //assert
        Assertions.assertTrue(opFirst.isPresent());
        Assertions.assertEquals("first", opFirst.get());
    }

    @Test
    public void canGetFirstFilteredValue() {
        MoseStream<String> stream = MoseStream.stream("first", "second");

        //act
        Optional<String> opFirst = stream.filter(value -> value.equals("second")).first();

        //assert
        Assertions.assertTrue(opFirst.isPresent());
        Assertions.assertEquals("second", opFirst.get());
    }

    @Test
    public void canGetEmptyValue() {
        MoseStream<String> stream = MoseStream.stream();

        //act
        Optional<String> opFirst = stream.first();

        //assert
        Assertions.assertFalse(opFirst.isPresent());
    }

    @Test
    public void canFlatMapValue() {
        MoseStream<String> stream = MoseStream.stream("abc");

        //act
        List<Character> characterArray = stream.flatMap(value -> {
            List<Character> array = new LinkedList<>();
            for (char character : value.toCharArray()) {
                array.add(character);
            }
            return array;
        }).toList();

        //assert
        Assertions.assertEquals(3, characterArray.size());
        Assertions.assertEquals(Arrays.asList('a', 'b', 'c'), characterArray);
    }

    @Test
    public void canFlatMapToDouble() {
        MoseStream<String> stream = MoseStream.stream("abc");

        //act
        List<Double> values = stream.flatMapToDouble(value -> Arrays.asList(2.1, 2.2, 2.3)).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(2.1, 2.2, 2.3), values);
    }

    @Test
    public void canFlatMapToInteger() {
        MoseStream<String> stream = MoseStream.stream("abc");

        //act
        List<Integer> values = stream.flatMapToInteger(value -> Arrays.asList(1, 2, 3)).toList();

        //assert
        Assertions.assertEquals(Arrays.asList(1, 2, 3), values);
    }

    @Test
    public void canDistinctBy() {
        MoseStream<String> stream = MoseStream.stream("first", "second", "third", "fourth");

        //act
        List<String> result = stream.distinctBy(value -> value.charAt(0)).toList();

        //assert
        Assertions.assertEquals(Arrays.asList("first", "second", "third"), result);
    }

    @Test
    public void canDistinct() {
        MoseStream<String> stream = MoseStream.stream("first", "second", "third", "first");

        //act
        List<String> result = stream.distinct().toList();

        //assert
        Assertions.assertEquals(Arrays.asList("first", "second", "third"), result);
    }

    @Test
    public void canDistinctCustom() {
        MoseStream<String> stream = MoseStream.stream("first", "second", "third", "forth");

        //act
        List<String> result = stream.distinct((first, second) -> first.charAt(0) == second.charAt(0)).toList();

        //assert
        Assertions.assertEquals(Arrays.asList("first", "second", "third"), result);
    }

    @Test
    public void allMatchWithTrue() {
        MoseStream<Boolean> stream = MoseStream.stream(true, true, true, true);

        //act
        boolean result = stream.allMatch(v -> v);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void allMatchWithOneFalse() {
        MoseStream<Boolean> stream = MoseStream.stream(true, false, true, true);

        //act
        boolean result = stream.allMatch(v -> v);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void noneMatchWithFalse() {
        MoseStream<Boolean> stream = MoseStream.stream(false, false, false, false);

        //act
        boolean result = stream.noneMatch(v -> v);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void noneMatchWithOneTrue() {
        MoseStream<Boolean> stream = MoseStream.stream(false, false, true, false);

        //act
        boolean result = stream.noneMatch(v -> v);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void anyMatchWithFalse() {
        MoseStream<Boolean> stream = MoseStream.stream(false, false, false, false);

        //act
        boolean result = stream.anyMatch(v -> v);

        //assert
        Assertions.assertFalse(result);
    }

    @Test
    public void anyMatchWithOneTrue() {
        MoseStream<Boolean> stream = MoseStream.stream(false, false, true, false);

        //act
        boolean result = stream.anyMatch(v -> v);

        //assert
        Assertions.assertTrue(result);
    }

    @Test
    public void canCount() {
        MoseStream<String> stream = MoseStream.stream("", "", "", "");

        //act
        long result = stream.count();

        //assert
        Assertions.assertEquals(4, result);
    }

    @Test
    public void canExecuteForEach() {
        MoseStream<String> stream = MoseStream.stream("", "", "", "");
        List<String> found = new LinkedList<>();

        //act
        stream.forEach(found::add);

        //assert
        Assertions.assertEquals(Arrays.asList("", "", "", ""), found);
    }

    @Test
    public void canExecuteEach() {
        MoseStream<String> stream = MoseStream.stream("", "", "", "");
        List<String> found = new LinkedList<>();

        //act
        List<String> result = stream.each(found::add).toList();

        //assert
        Assertions.assertEquals(Arrays.asList("", "", "", ""), found);
        Assertions.assertEquals(found, result);
    }

    @Test
    public void canLimit() {
        MoseStream<String> stream = MoseStream.stream("first", "second", "third", "forth");

        //act
        List<String> result = stream.limit(2).toList();

        //assert
        Assertions.assertEquals(Arrays.asList("first", "second"), result);
    }

    @Test
    public void canSkip() {
        MoseStream<String> stream = MoseStream.stream("first", "second", "third", "forth");

        //act
        List<String> result = stream.skip(2).toList();

        //assert
        Assertions.assertEquals(Arrays.asList("third", "forth"), result);
    }

    @Test
    public void canCreateArray() {
        MoseStream<String> stream = MoseStream.stream("first", "second");

        //act
        String[] array = stream.toArray(String[]::new);

        //assert
        Assertions.assertArrayEquals(new String[]{"first", "second"}, array);
    }

    @Test
    public void canSort() {
        MoseStream<String> stream = MoseStream.stream("first", "second", "third", "forth");

        //act
        List<String> result = stream.sorted(Comparator.naturalOrder()).toList();

        //assert
        Assertions.assertEquals(Arrays.asList("first", "forth", "second", "third"), result);
    }

    @Test
    public void canGetMaxElement() {
        MoseStream<String> stream = MoseStream.stream("first", "second", "third", "forth");

        //act
        Optional<String> opResult = stream.max(Comparator.naturalOrder());

        //assert
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals("first", opResult.get());
    }

    @Test
    public void canGetMinElement() {
        MoseStream<String> stream = MoseStream.stream("first", "second", "third", "forth");

        //act
        Optional<String> opResult = stream.min(Comparator.naturalOrder());

        //assert
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals("third", opResult.get());
    }

    @Test
    public void canReduceWithInitialValue() {
        MoseStream<String> stream = MoseStream.stream("fi", "r", "st");

        //act
        String result = stream.reduce("", (incoming, current) -> current + incoming);

        //assert
        Assertions.assertEquals("first", result);
    }

    @Test
    public void canReduce() {
        MoseStream<String> stream = MoseStream.stream("fi", "r", "st");

        //act
        Optional<String> opResult = stream.reduce((incoming, current) -> current + incoming);

        //assert
        Assertions.assertTrue(opResult.isPresent());
        Assertions.assertEquals("first", opResult.get());
    }

    @Test
    public void canGoToStream() {
        MoseStream<String> stream = MoseStream.stream("first", "second");

        //act
        List<String> result = stream.toStream().collect(Collectors.toList());

        //assert
        Assertions.assertEquals(Arrays.asList("first", "second"), result);
    }

    @Test
    public void canGroupBy() {
        MoseStream<String> stream = MoseStream.stream("first", "second", "third", "forth");

        //act
        Set<List<String>> result = stream.groupBy(Collectors.toList(), String::length).toSet();

        //assert
        Assertions.assertEquals(2, result.size());
        List<String> longArray = result.stream().max(Comparator.comparing(List::size)).orElseThrow(() -> new RuntimeException("No results found"));
        List<String> shortArray = result.stream().min(Comparator.comparing(List::size)).orElseThrow(() -> new RuntimeException("No results found"));

        Assertions.assertEquals(Collections.singletonList("second"), shortArray);
        Assertions.assertEquals(Arrays.asList("first", "third", "forth"), longArray);
    }
}

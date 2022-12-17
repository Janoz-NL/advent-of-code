package com.janoz.aoc.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Infinitely Iterates elements. Loops when end of src is reached.
 * @param <T> type of elements
 */
public class InfiniteIterator<T> implements Iterator<T> {

    private List<T> src;
    private int curpos;

    public InfiniteIterator(List<T> src) {
        this.src = src;
        curpos = -1;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        curpos++;
        if (curpos == src.size()) curpos=0;
        return src.get(curpos);
    }

    public static InfiniteIterator<Character> loopingCharIterator(String input) {
        char[] chars = input.toCharArray();
        return new InfiniteIterator<>(IntStream.range(0,chars.length).mapToObj(i -> (chars[i])).collect(Collectors.toList()));
    }

}

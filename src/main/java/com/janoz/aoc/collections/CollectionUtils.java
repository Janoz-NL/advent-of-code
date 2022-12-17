package com.janoz.aoc.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class CollectionUtils {



    public static <T> Collection<Set<T>> superSets(Set<T> src) {
        return superSets(src,1,src.size() / 2);
    }

    public static <T> Collection<Set<T>> superSets(Set<T> src, int minSize, int maxSize) {
        Set<Set<T>> result = new HashSet<>();
        if (minSize < 1) {
            superSet(result, List.copyOf(src), Collections.emptySet(),0,1,maxSize);
            result.add(Collections.emptySet());
        } else {
            superSet(result, List.copyOf(src), Collections.emptySet(),0,minSize,maxSize);
        }
        return result;
    }

    public static <T> Set<T> complement(Set<T> all, Set<T> other, Function<Set<T>, Set<T>> constructor) {
        Set<T> result = constructor.apply(all);
        result.removeAll(other);
        return result;
    }

    public static <T> Set<T> complement(Set<T> all, Set<T> other) {
        return (complement(all, other, HashSet::new));
    }

    private static <T> void superSet(Collection<Set<T>> result, List<T> src, Set<T> candidate, int curIdx, int min, int max) {
        if (curIdx == src.size()) return;
        if (candidate.size() == max) return;

        //ignore current
        superSet(result, src, candidate, curIdx + 1 , min, max);

        //add current
        if (candidate.size()<max) {
            Set<T> newCandidate = new HashSet<>(candidate);
            newCandidate.add(src.get(curIdx));
            if (newCandidate.size()>= min) {
                result.add(newCandidate);
            }
            superSet(result, src, newCandidate, curIdx + 1, min, max);
        }
    }

    public static <I,O> Iterator<O> wrap(Iterator<I> src, Function<I,O> function) {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return src.hasNext();
            }

            @Override
            public O next() {
                return function.apply(src.next());
            }

            @Override
            public void remove() {
                src.remove();
            }

            @Override
            public void forEachRemaining(Consumer<? super O> action) {
                src.forEachRemaining(i -> action.accept(function.apply(i)));
            }
        };
    }
}

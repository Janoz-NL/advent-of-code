package com.janoz.aoc.collections;

public class Tuple<T1,T2>{
    private final T1 left;
    private final T2 right;

    public Tuple(T1 t1, T2 t2) {
        this.left = t1;
        this.right = t2;
    }

    public T1 getLeft() {
        return left;
    }

    public T2 getRight() {
        return right;
    }
}

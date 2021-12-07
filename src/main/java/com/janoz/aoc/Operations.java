package com.janoz.aoc;

import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.LongFunction;
import java.util.function.Supplier;

public class Operations<T extends Number> {

    public final Supplier<T> zero;
    public final Supplier<T> one;
    public final BiFunction<T,T,T> add;
    public final BiFunction<T,T,T> mul;
    public final BiFunction<T,T,T> div;
    public final LongFunction<T> fromLong;

    private Operations(Supplier<T> zero, Supplier<T> one, BiFunction<T, T, T> add, BiFunction<T, T, T> mul, BiFunction<T,T,T> div, LongFunction<T> fromLong) {
        this.zero = zero;
        this.one = one;
        this.add = add;
        this.mul = mul;
        this.div = div;
        this.fromLong = fromLong;
    }

    public static Operations<BigInteger> bigIntegerOperations() {
        return new Operations<>(
                () -> BigInteger.ZERO,
                () -> BigInteger.ONE,
                BigInteger::add,
                BigInteger::multiply,
                BigInteger::divide,
                BigInteger::valueOf
        );
    }

    public static Operations<Long> longOperations() {
        return new Operations<>(
                () -> 0L ,
                () -> 1L ,
                Long::sum,
                (a,b) -> a*b,
                (a,b) -> a/b,
                Long::valueOf
        );
    }

    public static Operations<Integer> integerOperations() {
        return new Operations<>(
                () -> 0 ,
                () -> 1 ,
                Integer::sum,
                (a,b) -> a*b,
                (a,b) -> a/b,
                l -> (int) l
        );
    }
}

package com.janoz.aoc;

import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Operations<T extends Number> {

    public final Supplier<T> zero;
    public final BiFunction<T,T,T> add;
    public final BiFunction<T,T,T> mul;

    private Operations(Supplier<T> zero, BiFunction<T, T, T> add, BiFunction<T, T, T> mul) {
        this.zero = zero;
        this.add = add;
        this.mul = mul;
    }

    public static Operations<BigInteger> bigIntegerOperations() {
        return new Operations<>(
                () -> BigInteger.ZERO,
                BigInteger::add,
                BigInteger::multiply
        );
    }

    public static Operations<Long> longOperations() {
        return new Operations<>(
                () -> 0L ,
                Long::sum,
                (a,b) -> a*b
        );
    }

    public static Operations<Integer> integerOperations() {
        return new Operations<>(
                () -> 0 ,
                Integer::sum,
                (a,b) -> a*b
        );
    }
}

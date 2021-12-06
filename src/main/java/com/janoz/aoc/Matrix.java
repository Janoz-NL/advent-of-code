package com.janoz.aoc;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class Matrix<T extends Number> {

    private final T[][] data;
    private final Operations<T> operations;

    private Matrix(T[][] data, Operations<T> operations) {
        this.data = data;
        this.operations = operations;
    }

    private Matrix(int[][] data, Function<Integer, T> valueOf, Operations<T> operations) {
        this(calculate(data.length, data[0].length, (r,c) -> valueOf.apply(data[r][c])), operations);
    }

    public Matrix<T> mul(Matrix<T> other) {
        return new Matrix<>(mul(this.data, other.data, operations), operations);
    }

    public Matrix<T> pow(long pow) {
        HashMap<Long, T[][]> cache = new HashMap<>();
        cache.put(1L,this.data);
        return new Matrix<>(pow(this.data, pow, cache, operations), operations);
    }

    public Matrix<T> transpose() {
        return new Matrix<>(calculate(data[0].length, data.length, (r,c) -> data[c][r]), operations);
    }

    private static <T extends Number> T[][] mul(T[][] left, T[][] right, Operations<T> operations) {
        final int itt = right.length;
        return calculate(left.length, right[0].length, (r,c) -> {
          T val = operations.zero.get();
          for (int i=0; i< itt; i++) {
              val = operations.add.apply(val, operations.mul.apply(left[r][i],right[i][c]));
          }
          return val;
        });
    }

    private static <T extends Number> T[][] pow(T[][] matrix, long pow, Map<Long, T[][]> cache, Operations<T> operations) {
        if (cache.containsKey(pow)) {
            return cache.get(pow);
        }
        long half = pow >> 1;
        T[][] result = mul(pow(matrix, half, cache, operations), pow(matrix, pow-half, cache, operations), operations);
        cache.put(pow, result);
        return result;
    }

    @SuppressWarnings({"unchecked"})
    private static <T extends Number> T[][] calculate(int rows, int cols, BiFunction<Integer, Integer, T> calculator) {
        T[][] result = (T[][]) new Number[rows][];
        for (int r = 0; r<rows; r++) {
            result[r] = (T[]) new Number[cols];
            for (int c = 0; c<cols; c++) {
                result[r][c] = calculator.apply(r,c);
            }
        }
        return result;
    }

    public Stream<T> streamContent() {
        return Arrays.stream(data).flatMap(Arrays::stream);
    }

    public void print(String format) {
        for (T[] row : data) {
            for (T i : row) {
                System.out.printf(format, i);
            }
            System.out.println();
        }
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Arrays.deepEquals(data, ((Matrix<T>)o).data);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }

    public static Matrix<BigInteger> bigIntegerMatrix(int[][] data) {
        return new Matrix<>(data, BigInteger::valueOf, Operations.bigIntegerOperations());
    }

    public static Matrix<Long> longMatrix(int[][] data) {
        return new Matrix<>(data, Long::valueOf, Operations.longOperations());
    }

    public static Matrix<Integer> integerMatrix(int[][] data) {
        return new Matrix<>(data, Integer::valueOf, Operations.integerOperations());
    }

}

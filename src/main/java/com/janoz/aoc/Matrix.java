package com.janoz.aoc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Matrix {

    private BigInteger[][] data;

    public Matrix(int[][] data) {
        this.data = new BigInteger[data.length][];
        for (int r=0; r< data.length; r++) {
            this.data[r] = new BigInteger[data[0].length];
            for (int c = 0; c < data[0].length; c++) {
                this.data[r][c] = BigInteger.valueOf(data[r][c]);
            }
        }
    }

    public Matrix(BigInteger[][] data) {
        this.data = data;
    }

    public Matrix mul(Matrix other) {
        return new Matrix(mul(this.data, other.data));
    }

    public Matrix pow(long pow) {
        HashMap<Long, BigInteger[][]> cache = new HashMap<>();
        cache.put(1L,this.data);
        return new Matrix(pow(this.data, pow, cache));
    }

    private static BigInteger[][] mul(BigInteger[][] left, BigInteger[][] right) {
        int rows = left.length;
        int itt = right.length;
        int cols = right[0].length;
        BigInteger[][] result = new BigInteger[rows][];
        for (int r=0; r < rows; r++) {
            result[r] = new BigInteger[cols];
            for (int c=0; c < cols; c++) {
                result[r][c] = BigInteger.ZERO;
                for (int i=0; i<itt; i++) {
                    result[r][c] = result[r][c].add(left[r][i].multiply(right[i][c]));
                }
            }
        }
        return result;
    }

    private static BigInteger[][] pow(BigInteger[][] matrix, long pow, Map<Long, BigInteger[][]> cache) {
        if (cache.containsKey(pow)) {
            return cache.get(pow);
        }
        long half = pow/2;
        BigInteger[][] result = mul(pow(matrix, half, cache), pow(matrix, pow-half, cache));
        cache.put(pow, result);
        return result;
    }

    public Matrix transpose() {
        int rows = data.length;
        int cols = data[0].length;
        BigInteger[][] output = new BigInteger[cols][];
        for (int c = 0; c<cols; c++) {
            output[c] = new BigInteger[rows];
            for (int r = 0; r<rows; r++) {
                output[c][r] = data[r][c];
            }
        }
        return new Matrix(output);
    }

    public Stream<BigInteger> streamContent() {
        return Arrays.stream(data).flatMap(Arrays::stream);
    }

    public void print() {
        for (BigInteger[] row : data) {
            for (BigInteger i : row) {
                System.out.printf(" %5d", i);
            }
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return Arrays.deepEquals(data, matrix.data);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }
}

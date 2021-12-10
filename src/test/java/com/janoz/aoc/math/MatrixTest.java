package com.janoz.aoc.math;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MatrixTest {


    @Test
    void testTranspose() {
        Matrix<Integer> input = Matrix.integerMatrix(new int[][]{{1,2,3},{4,5,6}});
        Matrix<Integer> actual = input.transpose();
        assertThat(actual, equalTo(Matrix.integerMatrix((new int[][]{{1,4},{2,5},{3,6}}))));
    }

    @Test
    void testMultiplication() {
        Matrix<Integer> left = Matrix.integerMatrix(new int[][]{{1,5},{2,3},{1,7}});
        Matrix<Integer> right = Matrix.integerMatrix(new int[][]{{1,2,3,7},{5,2,8,1}});
        assertThat(left.mul(right), equalTo(Matrix.integerMatrix(new int[][] {{26,12,43,12},{17,10,30,17},{36,16,59,14}})));
    }

    @Test
    void testPower() {
        Matrix<Integer> start = Matrix.integerMatrix(new int[][] {{4,3},{6,5}});
        assertThat(start.pow(2), equalTo(Matrix.integerMatrix(new int[][] {{34,27},{54,43}})));
        assertThat(start.pow(5), equalTo(Matrix.integerMatrix(new int[][] {{22930, 18237},{36474, 29009}})));
    }
}

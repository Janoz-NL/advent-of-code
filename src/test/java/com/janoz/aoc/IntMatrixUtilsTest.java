package com.janoz.aoc;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class IntMatrixUtilsTest {

    @Test
    void testTranspose() {
        int[][] input = new int[][] {{1,2,3},{4,5,6}};
        int[][] actual = IntMatrixUtils.transpose(input);
        assertThat(actual, equalTo(new int[][]{{1,4},{2,5},{3,6}}));
    }

    @Test
    void testTransposeInPlaceOddSize() {
        int[][] input = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        IntMatrixUtils.transposeInPlace(input);
        assertThat(input, equalTo(new int[][]{{1,4,7},{2,5,8},{3,6,9}}));
    }

    @Test
    void testTransposeInPlaceEvenSize() {
        int[][] input = new int[][] {{1,2},{3,4}};
        IntMatrixUtils.transposeInPlace(input);
        assertThat(input, equalTo(new int[][]{{1,3},{2,4}}));
    }

    @Test
    void testMap() {
        int[][] input = new int[][] {{1,2,3},{4,5,6}};
        int[] map = new int[]{7,8,9,10,11,12,13};
        int[][] actual = IntMatrixUtils.map(input, map);
        assertThat(actual, equalTo(new int[][] {{8,9,10},{11,12,13}} ));
    }

    @Test
    void testMultiplication() {
        int[][] left = {{1,5},{2,3},{1,7}};
        int[][] right = {{1,2,3,7},{5,2,8,1}};
        assertThat(IntMatrixUtils.mul(left,right), equalTo(new int[][] {{26,12,43,12},{17,10,30,17},{36,16,59,14}}));
    }

    @Test
    void testPower() {
        int[][] start = new int[][] {{4,3},{6,5}};
        assertThat(IntMatrixUtils.pow(start,2), equalTo(new int[][] {{34,27},{54,43}}));
        assertThat(IntMatrixUtils.pow(start,5), equalTo(new int[][] {{22930, 18237},{36474, 29009}}));
    }

    @Test
    void testRead() throws IOException {
        Reader input = new StringReader(
                        "22 13 17 11  0\n" +
                        " 8  2 23  4 24\n" +
                        "21  9 14 16  7\n" +
                        " 6 10  3 18  5\n" +
                        " 1 12 20 15 19");
        int[][] actual = IntMatrixUtils.read(new BufferedReader(input),5);
        assertThat(actual, equalTo(new int[][] {
                        {22, 13, 17, 11,  0},
                        { 8,  2, 23,  4, 24},
                        {21,  9, 14, 16,  7},
                        { 6, 10,  3, 18,  5},
                        { 1, 12, 20, 15, 19},
        } ));

    }

}
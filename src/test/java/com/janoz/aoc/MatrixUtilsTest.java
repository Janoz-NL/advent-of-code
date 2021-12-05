package com.janoz.aoc;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MatrixUtilsTest {

    @Test
    void testTranspose() {
        int[][] input = new int[][] {{1,2,3},{4,5,6}};
        int[][] actual = MatrixUtils.transpose(input);
        assertThat(actual, equalTo(new int[][]{{1,4},{2,5},{3,6}}));
    }

    @Test
    void testTransposeInPlaceOddSize() {
        int[][] input = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        MatrixUtils.transposeInPlace(input);
        assertThat(input, equalTo(new int[][]{{1,4,7},{2,5,8},{3,6,9}}));
    }

    @Test
    void testTransposeInPlaceEvenSize() {
        int[][] input = new int[][] {{1,2},{3,4}};
        MatrixUtils.transposeInPlace(input);
        assertThat(input, equalTo(new int[][]{{1,3},{2,4}}));
    }

    @Test
    void testMap() {
        int[][] input = new int[][] {{1,2,3},{4,5,6}};
        int[] map = new int[]{7,8,9,10,11,12,13};
        int[][] actual = MatrixUtils.map(input, map);
        assertThat(actual, equalTo(new int[][] {{8,9,10},{11,12,13}} ));
    }

    @Test
    void testRead() throws IOException {
        Reader input = new StringReader(
                        "22 13 17 11  0\n" +
                        " 8  2 23  4 24\n" +
                        "21  9 14 16  7\n" +
                        " 6 10  3 18  5\n" +
                        " 1 12 20 15 19");
        int[][] actual = MatrixUtils.read(new BufferedReader(input),5);
        assertThat(actual, equalTo(new int[][] {
                        {22, 13, 17, 11,  0},
                        { 8,  2, 23,  4, 24},
                        {21,  9, 14, 16,  7},
                        { 6, 10,  3, 18,  5},
                        { 1, 12, 20, 15, 19},
        } ));

    }

}
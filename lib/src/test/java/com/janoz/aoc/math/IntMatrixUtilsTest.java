package com.janoz.aoc.math;

import com.janoz.aoc.geo.Point;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

class IntMatrixUtilsTest {

    @Test
    void testTranspose() {
        int[][] input = new int[][] {{1,2,3},{4,5,6}};
        int[][] actual = IntMatrixUtils.transpose(input);
        assertThat(actual).isEqualTo(new int[][]{{1,4},{2,5},{3,6}});
    }

    @Test
    void testTransposeInPlaceOddSize() {
        int[][] input = new int[][] {{1,2,3},{4,5,6},{7,8,9}};
        IntMatrixUtils.transposeInPlace(input);
        assertThat(input).isEqualTo(new int[][]{{1,4,7},{2,5,8},{3,6,9}});
    }

    @Test
    void testTransposeInPlaceEvenSize() {
        int[][] input = new int[][] {{1,2},{3,4}};
        IntMatrixUtils.transposeInPlace(input);
        assertThat(input).isEqualTo(new int[][]{{1,3},{2,4}});
    }

    @Test
    void testMap() {
        int[][] input = new int[][] {{1,2,3},{4,5,6}};
        int[] map = new int[]{7,8,9,10,11,12,13};
        int[][] actual = IntMatrixUtils.map(input, map);
        assertThat(actual).isEqualTo(new int[][] {{8,9,10},{11,12,13}});
    }

    @Test
    void testMultiplication() {
        int[][] left = {{1,5},{2,3},{1,7}};
        int[][] right = {{1,2,3,7},{5,2,8,1}};
        assertThat(IntMatrixUtils.mul(left,right)).isEqualTo(new int[][] {{26,12,43,12},{17,10,30,17},{36,16,59,14}});
    }

    @Test
    void testPower() {
        int[][] start = new int[][] {{4,3},{6,5}};
        assertThat(IntMatrixUtils.pow(start,2)).isEqualTo(new int[][] {{34,27},{54,43}});
        assertThat(IntMatrixUtils.pow(start,5)).isEqualTo(new int[][] {{22930, 18237},{36474, 29009}});
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
        assertThat(actual).isEqualTo(new int[][] {
                        {22, 13, 17, 11,  0},
                        { 8,  2, 23,  4, 24},
                        {21,  9, 14, 16,  7},
                        { 6, 10,  3, 18,  5},
                        { 1, 12, 20, 15, 19},
        } );

    }


    private BiFunction<Point,int[][],Point> pointOperation= (p, matrix) -> IntMatrixUtils.toPoint(IntMatrixUtils.mul(matrix, IntMatrixUtils.fromPoint(p)));

    @Test
    void testPointTranslation() {
        assertThat(pointOperation.apply(new Point(1,2),IntMatrixUtils.translation(3,4))).isEqualTo(new Point(4,6));
    }

    @Test
    void testPointMirroring() {
        assertThat(pointOperation.apply(new Point(1,2),IntMatrixUtils.mirrorX())).isEqualTo(new Point(-1,2));
        assertThat(pointOperation.apply(new Point(1,2),IntMatrixUtils.mirrorY())).isEqualTo(new Point(1,-2));
    }

    @Test
    void testMultipleOperations() {
        // move +3 and mirror around x=0
        assertThat(pointOperation.apply(new Point(1,2),IntMatrixUtils.mul(IntMatrixUtils.mirrorX(),IntMatrixUtils.translation(3,0)))).isEqualTo(new Point(-4,2));
    }

}
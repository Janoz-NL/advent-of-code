package com.janoz.aoc.y2024.day10;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Iterator;

import com.janoz.aoc.geo.Point;

import org.junit.jupiter.api.Test;

public class Day10Test {


    @Test
    void testParsing() {
        String s =
             "10..9..\n" +
             "2...8..\n" +
             "3...7..\n" +
             "4567654\n" +
             "...8..3\n" +
             "...9..2\n" +
             ".....01";
        Day10.init(makeIterator(s));
        assertThat(Day10.trailHeads).hasSize(2);
    }
    @Test
    void testClimbing() {
        String s =
                "10..9..\n" +
                        "2...8..\n" +
                        "3...7..\n" +
                        "4567654\n" +
                        "...8..3\n" +
                        "...9..2\n" +
                        ".....01";
        Day10.init(makeIterator(s));
        assertThat(Day10.climbingTrails()).isEqualTo(3);
    }

    @Test
    void testClimbing2() {
        String s =
                "89010123\n" +
                        "78121874\n" +
                        "87430965\n" +
                        "96549874\n" +
                        "45678903\n" +
                        "32019012\n" +
                        "01329801\n" +
                        "10456732";
        Day10.init(makeIterator(s));
        assertThat(Day10.climbingTrails()).isEqualTo(81);
    }

    @Test
    void testClimb() {
        String s =
                "89010123\n" +
                        "78121874\n" +
                        "87430965\n" +
                        "96549874\n" +
                        "45678903\n" +
                        "32019012\n" +
                        "01329801\n" +
                        "10456732";
        Day10.init(makeIterator(s));
        assertThat(Day10.reachableTops(new Point(2,0)).size()).isEqualTo(5L);
    }
    @Test
    void testClimb2() {
        String s =
                "89010123\n" +
                        "78121874\n" +
                        "87430965\n" +
                        "96549874\n" +
                        "45678903\n" +
                        "32019012\n" +
                        "01329801\n" +
                        "10456732";
        Day10.init(makeIterator(s));
        assertThat(Day10.part1()).isEqualTo(36L);
    }
    Iterator<String> makeIterator(String input) {
        return Arrays.asList(input.split("\\n")).iterator();
    }
}

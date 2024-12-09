package com.janoz.aoc.y2024.day9;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.janoz.aoc.InputProcessor;

import org.junit.jupiter.api.Test;

public class Day9Test {


    @Test
    void testParsing() {
        assertThat(Day9.fileSystemToString(Day9.parse("12345"))).isEqualTo("0..111....22222");
        assertThat(Day9.fileSystemToString(Day9.parse("2333133121414131402"))).isEqualTo("00...111...2...333.44.5555.6666.777.888899");
    }

    @Test
    void testCompacting1() {
        List<Day9.Node> nodes = Day9.parse("12345");
        Day9.compactSingle(nodes);
        assertThat(Day9.fileSystemToString(nodes)).isEqualTo("022111222");
    }

    @Test
    void testCompacting2() {
        List<Day9.Node> nodes = Day9.parse("2333133121414131402");
        Day9.compact(nodes);
        assertThat(Day9.fileSystemToString(nodes)).isEqualTo("0099811188827773336446555566");
    }

    @Test
    void testCompactDefrag2() {
        List<Day9.Node> nodes = Day9.parse("2333133121414131402");
        Day9.compactDefrag(nodes);
        assertThat(Day9.fileSystemToString(nodes)).isEqualTo("00992111777.44.333....5555.6666.....8888");
    }

    @Test
    void testChecksum() {
        List<Day9.Node> nodes = Day9.parse("2333133121414131402");
        Day9.compact(nodes);
        assertThat(Day9.fileSystemToString(nodes)).isEqualTo("0099811188827773336446555566");
        assertThat(Day9.checksum(nodes)).isEqualTo(1928L);
    }

    @Test
    void testChecksum2() {
        List<Day9.Node> nodes = Day9.parse("2333133121414131402");
        Day9.compactDefrag(nodes);
        assertThat(Day9.fileSystemToString(nodes)).isEqualTo("00992111777.44.333....5555.6666.....8888");
        assertThat(Day9.checksum(nodes)).isEqualTo(2858L);
    }

    @Test
    void testPart2() {
        String filesystem = InputProcessor.asIterator("inputs/2024/day09.txt").next();
        List<Day9.Node> nodes = Day9.parse(filesystem);
        Day9.compactDefrag(nodes);
        assertThat(Day9.checksum(nodes)).isLessThan(6469637179774L);
    }
}

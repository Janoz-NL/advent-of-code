package com.janoz.aoc.y2024.day23;

import org.junit.jupiter.api.Test;

class Day23Test {


    @Test
    void testExample() {
        Day23.read("ka-co");
        Day23.read("ta-co");
        Day23.read("de-co");
        Day23.read("ta-ka");
        Day23.read("de-ta");
        Day23.read("ka-de");
        System.out.println(Day23.part2());
    }

}
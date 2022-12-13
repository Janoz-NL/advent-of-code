package com.janoz.aoc.y2021.day25;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FieldTest {


    @Test
    void testExample() {
        Field field = new Field();
        field.readField("inputs/day25example.txt");
        field.print();
        assertThat(field.moveUntilStopped()).isEqualTo(58);
        System.out.println();
        field.print();
    }

}
package com.janoz.aoc.y2021.day25;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


class FieldTest {


    @Test
    void testExample() {
        Field field = new Field();
        field.readField("inputs/day25example.txt");
        field.print();
        assertThat(field.moveUntilStopped(),equalTo(58));
        System.out.println();
        field.print();
    }

}
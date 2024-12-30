package com.janoz.aoc.y2015.day15;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test {

    @Test
    void testIngredient() {
        Day15.Ingredient i = new Day15.Ingredient("Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8");
        assertThat(i.ingredients[0]).isEqualTo(-1);
        assertThat(i.ingredients[1]).isEqualTo(-2);
        assertThat(i.ingredients[2]).isEqualTo(6);
        assertThat(i.ingredients[3]).isEqualTo(3);
        assertThat(i.ingredients[4]).isEqualTo(8);
    }

}
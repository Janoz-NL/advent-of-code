package com.janoz.aoc.y2024.day22;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day22Test {




    @Test
    void testNext() {
        assertThat(Day22.nextSecret(123L)).isEqualTo(15887950L);
        assertThat(Day22.nextSecret(15887950L)).isEqualTo(16495136L);
        assertThat(Day22.nextSecret(16495136L)).isEqualTo(527345L);
    }

//    @Test
//    void testTimes() {
//        assertThat(Day22.generateSecret(1L,2000)).isEqualTo(8685429L);
//        assertThat(Day22.generateSecret(10L,2000)).isEqualTo(4700978L);
//        assertThat(Day22.generateSecret(100L, 2000)).isEqualTo(15273692L);
//        assertThat(Day22.generateSecret(2024L, 2000)).isEqualTo(8667524L);
//    }

    @Test
    void testSequence() {
        Day22.processSecretNumber(123L);
    }

    @Test
    void testNewSequence() {
        int hash = 0;

        hash = Day22.nextHash(hash,-9);
        hash = Day22.nextHash(hash,2);
        hash = Day22.nextHash(hash,9);
        hash = Day22.nextHash(hash,9);
        int previousHash = hash;

        hash = Day22.nextHash(hash,5);
        hash = Day22.nextHash(hash,-9);
        hash = Day22.nextHash(hash,2);
        hash = Day22.nextHash(hash,9);
        assertThat(hash).isNotEqualTo(previousHash);
        hash = Day22.nextHash(hash,9);
        assertThat(hash).isEqualTo(previousHash);
    }
}
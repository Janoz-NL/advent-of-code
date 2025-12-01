package com.janoz.aoc.y2015.day24;

import com.janoz.aoc.input.AocInput;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class Day24Test {


    @Test
    void testSize() {
        BigInteger product = BigInteger.ONE;
        for (Integer i : AocInput.ofString("""
                1
                2
                3
                7
                11
                13
                17
                19
                23
                31
                37
                41
                43
                47
                53
                59
                61
                67
                71
                73
                79
                83
                89
                97
                101
                103
                107
                109
                113
                """).addMapper(Integer::valueOf).asList()) {
            product = product.multiply(BigInteger.valueOf(i));
            System.out.println(product);
        }
    }


}
package com.janoz.aoc.y2021.day16;

import com.janoz.aoc.InputProcessor;

import java.math.BigInteger;

public class Day16 {


    public static void main(String[] args) {
        System.out.println(part1("inputs/day16.txt"));
        System.out.println(part2("inputs/day16.txt"));
    }

    static long part1(String input) {
        return new InputProcessor<>(input, Packet::fromHex).stream().mapToLong(Packet::versionSum).findFirst().getAsLong();
    }

    static BigInteger part2(String input) {
        return new InputProcessor<>(input, Packet::fromHex).stream().map(Packet::getResult).findFirst().get();
    }
}

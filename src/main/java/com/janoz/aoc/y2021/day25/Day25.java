package com.janoz.aoc.y2021.day25;

public class Day25 {


    public static void main(String[] args) {
        Field field = new Field();
        field.readField("inputs/2021/day25.txt");
        System.out.println();
        System.out.println(field.moveUntilStopped());
        System.out.println();
        field.print();
    }
}

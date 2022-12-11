package com.janoz.aoc.y2022.day11;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Item {

    public Item(long worryLevel) {
        this.worryLevel = worryLevel;
    }

    long worryLevel;

    static List<Item> parseItems(String line) {
        assert(line.startsWith("Starting items: "));
        return Arrays.stream(line.substring(15).split(","))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .mapToObj(Item::new)
                .collect(Collectors.toList());
    }
}

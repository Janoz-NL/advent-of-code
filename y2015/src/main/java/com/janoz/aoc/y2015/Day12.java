package com.janoz.aoc.y2015;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.janoz.aoc.input.AocInput;

import java.util.Map;

public class Day12 {
    public static void main(String[] args) throws JsonProcessingException {
        solve(AocInput.of(2015,12));
    }

    static void solve(AocInput<String> input) throws JsonProcessingException {
        JsonNode node = new ObjectMapper().readTree(input.asString(s->s));
        System.out.println("Part 1: " + sum(node));
        ignoreRed = true;
        System.out.println("Part 2: " + sum(node));
    }

    static long sum(JsonNode node) {
        if (node.isNumber()) return node.asLong();
        if (node.isArray()) {
            return sumArray(node);
        } else {
            return sumObject(node);
        }
    }

    static long sumArray(JsonNode array) {
        long result = 0;
        for (JsonNode node : array) {
            result += sum(node);
        }
        return result;
    }

    static boolean ignoreRed = false;

    static long sumObject(JsonNode object) {
        if (!ignoreRed ||
            object.properties().stream()
                    .map(Map.Entry::getValue)
                    .filter(JsonNode::isTextual)
                    .noneMatch(node -> node.textValue().equals("red"))) {
            return object.properties().stream()
                    .map(Map.Entry::getValue)
                    .mapToLong(Day12::sum)
                    .sum();
        }
        return 0;
    }

}

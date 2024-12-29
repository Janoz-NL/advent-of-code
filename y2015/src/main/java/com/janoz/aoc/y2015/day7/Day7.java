package com.janoz.aoc.y2015.day7;

import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.input.AocInput;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Day7 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,7));
    }

    static void solve(AocInput<String> input) {
        input.stream().forEach(Component::new);
        Long a = getResult("a");
        System.out.println("Part 1 :" + a);
        reset();
        components.get("b").setValue(a);
        System.out.println("Part 2 :" + getResult("a"));
    }

    static void reset() {
        components.forEach((k,v) ->{
            if (!Character.isDigit(k.charAt(0))) {
                v.setValue(null);
            }
        });
    }

    static Long getResult(String input) {
        if (components.containsKey(input)) {
            return components.get(input).getValue();
        }
        return null;
    }

    static Map<String, Component> components = new AlwaysHashMap<>(key -> new Component(Long.parseLong(key)));

    private static class Component {
        private final String output;
        private Long value = null;
        private final Function<List<Long>,Long> operation;
        private final List<String> inputs;

        long getValue() {
            if (value == null) {
                value = operation.apply(
                    inputs.stream().map(components::get).map(Component::getValue).toList());
            }
            return value;
        }

        void setValue(Long value) {
            this.value = value;
        }

        Component(Long value) {
            this.value = value;
            components.put(String.valueOf(value), this);
            output = String.valueOf(value);
            operation = null;
            inputs = null;
        }

        Component(String definition) {
            int arrow = definition.indexOf("->");
            output = definition.substring(arrow + 2).trim();
            String[] parts = definition.substring(0,arrow-1).split("\\s");
            if (parts.length == 1) {
                operation = ls -> ls.get(0);
                inputs = Collections.singletonList(parts[0]);
            } else if (parts.length == 2) {
                operation = operations.get("NOT");
                inputs = Collections.singletonList(parts[1]);
            } else {
                operation = operations.get(parts[1]);
                inputs = Arrays.asList(parts[0],parts[2]);
            }
            components.put(this.output,this);
        }
    }

    static final Map<String, Function<List<Long>,Long>> operations;
    static {
        operations = new HashMap<>();
        operations.put("NOT",ls -> ls.get(0) ^ 0xffffL);
        operations.put("AND",ls -> ls.get(0) & ls.get(1));
        operations.put("OR",ls -> ls.get(0) | ls.get(1));
        operations.put("LSHIFT",ls -> (ls.get(0) << ls.get(1)) & 0xffffL);
        operations.put("RSHIFT",ls -> (ls.get(0) >> ls.get(1)));
    }
}
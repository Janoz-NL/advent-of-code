package com.janoz.aoc.y2022.day19;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Blueprint {

    static final int ORE = 0;
    static final int CLAY = 1;
    static final int OBSIDIAN = 2;
    static final int GEODE = 3;

    private static final List<String> RAW_NAMES = Arrays.asList("ore", "clay", "obsidian", "geode");

    int id;

    Cost[] costs = new Cost[4];
    int[] maxCosts = new int[4];

    Blueprint(String input) {
        String[] inputs = input.split(":");
        id = Integer.parseInt(inputs[0].substring(10));
        inputs = inputs[1].split("\\.");
        for (String each : inputs) {
            if (each.length() > 0) {
                Cost cost = new Cost(each.trim());
                costs[cost.produces] = cost;
            }
        }

        for (int material = 0; material < 4; material++) {
            for (int robot = 0; robot < 4; robot++) {
                if (robot != material) {
                    maxCosts[material] = Math.max(maxCosts[material], costs[robot].costs[material]);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Blueprint{" +
                "id=" + id +
                ", costs=" + Arrays.toString(costs) +
                '}';
    }

    private static int oreFromString(String str) {
        return RAW_NAMES.indexOf(str);
    }

    static class Cost{

        static final Pattern p = Pattern.compile("^Each (.+) robot costs (.+)$");

        int produces;
        //map costs[raw] = needed of this raw
        int[] costs = new int[4];

        Cost(String config) {
            Matcher m = p.matcher(config);
            if (!m.matches()) throw new RuntimeException("No match for '" + config + "'");
            String raw = m.group(1);
            produces = oreFromString(raw);
            String[] costs =  m.group(2).split(" and ");
            for (String costStr:costs) {
                int split = costStr.indexOf(' ');
                this.costs[oreFromString(costStr.substring(split+1))] = Integer.parseInt(costStr.substring(0,split));
            }
        }

        @Override
        public String toString() {
            return "Cost{" +
                    "produces=" + produces +
                    ", costs='" + Arrays.toString(costs) + '\'' +
                    '}';
        }
    }
}

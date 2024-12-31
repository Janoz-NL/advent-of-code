package com.janoz.aoc.y2015.day16;

import com.janoz.aoc.input.AocInput;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day16 {

    public static void main(String[] args) {
        solve(AocInput.of(2015, 16));
    }

    static void solve(AocInput<String> input) {
        List<AntSue> aunts = input.addMapper(AntSue::new).asList();
        System.out.println("Part 1: " + aunts.stream().filter(as -> as.match(MFCSAM)).findAny().stream().mapToInt(as -> as.nr).findAny().getAsInt());
        System.out.println("Part 2: " + aunts.stream().filter(as -> as.match2(MFCSAM)).findAny().stream().mapToInt(as -> as.nr).findAny().getAsInt());
    }

    static final Map<String, Integer> MFCSAM;
    static {
        MFCSAM = new HashMap<>();
        MFCSAM.put("children",  3);
        MFCSAM.put("cats", 7);
        MFCSAM.put("samoyeds", 2);
        MFCSAM.put("pomeranians", 3);
        MFCSAM.put("akitas", 0);
        MFCSAM.put("vizslas", 0);
        MFCSAM.put("goldfish", 5);
        MFCSAM.put("trees", 3);
        MFCSAM.put("cars", 2);
        MFCSAM.put("perfumes", 1);
    }

    static class AntSue {
        int nr;
        Map<String, Integer> compounts = new HashMap<>();

        AntSue(String input) {
            String[] parts = input.split(": ",2);
            nr = Integer.parseInt(parts[0].substring(4));
            for (String compount : parts[1].split(", ")) {
                int colon = compount.indexOf(':');
                compounts.put(compount.substring(0,colon), Integer.parseInt(compount.substring(colon+2)));
            }
        }

        boolean match(Map<String, Integer> match) {
            for (Map.Entry<String, Integer> entry : compounts.entrySet()) {
                if (!entry.getValue().equals(match.get(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        }

        static final Set<String> greater = new HashSet<>(Arrays.asList("cats","trees"));
        static final Set<String> smaller = new HashSet<>(Arrays.asList("pomeranians","goldfish"));

        boolean match2(Map<String, Integer> match) {
            for (Map.Entry<String, Integer> entry : compounts.entrySet()) {
                if (greater.contains(entry.getKey())) {
                    if (entry.getValue() <= match.get(entry.getKey())) {
                        return false;
                    }
                } else if (smaller.contains(entry.getKey())) {
                    if (entry.getValue() >= match.get(entry.getKey())) {
                        return false;
                    }
                } else if (!entry.getValue().equals(match.get(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        }
    }
}

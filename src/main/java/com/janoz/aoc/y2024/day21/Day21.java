package com.janoz.aoc.y2024.day21;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.Point;

public class Day21 {



    static Map<String, Long> cache = new HashMap<>();


    public static void main(String[] args) {
        System.out.println("Part 1 :" + InputProcessor.asStream("inputs/2024/day21.txt").mapToLong(s -> score(s,2)).sum());
        System.out.println("Part 2 :" + InputProcessor.asStream("inputs/2024/day21.txt").mapToLong(s -> score(s,25)).sum());
    }

    static long score(String input, int robots) {
        return Long.parseLong(input.substring(0,3)) * findLengthFromCode(input, robots);
    }
    static long findLengthFromCode(String code, int level) {
        char previous = 'A';
        long length = 0;
        for (char c:code.toCharArray()) {
            length += getSequences(previous, c, keypad, 3).stream().mapToLong(s -> findLength(s, level)).min().orElseThrow();
            previous = c;
        }
        return length;
    }

    static long findLength(String s, int level) {
        String key = "" + level + s;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (level == 0) return s.length();
        long length = 0;
        char previous = 'A';
        for (char c:s.toCharArray()) {
            length += fingerPath.get("" + previous + c).stream().mapToLong(x -> findLength(x,level-1)).min().orElseThrow();
            previous = c;
        }
        cache.put(key, length);
        return length;
    }

    static Set<String> getSequences(char charFrom, char charTo, Map<Character, Point> pad, int missingKeyRow) {
        Point from = pad.get(charFrom);
        Point to = pad.get(charTo);
        Point direction = from.directionTo(to);
        int dx = direction.x;
        int dy = direction.y;
        String up = dy<0?"^".repeat(-dy):"";
        String down = dy>0?"v".repeat(dy):"";
        String left = dx<0?"<".repeat(-dx):"";
        String right = dx>0?">".repeat(dx):"";
        Set<String> result = new HashSet<>();
        if (from.x!=0 || to.y!=missingKeyRow) result.add(up+down+left+right + "A");
        if (from.y!=missingKeyRow || to.x!=0) result.add(left+right+up+down + "A");
        return result;
    }

    static final Map<Character, Point> dirpad;
    static {
        dirpad = new HashMap<>();
        dirpad.put('^',new Point(1,0));
        dirpad.put('A',new Point(2,0));

        dirpad.put('<',new Point(0,1));
        dirpad.put('v',new Point(1,1));
        dirpad.put('>',new Point(2,1));
    }
    static final Map<String, Set<String>> fingerPath;
    static {
        fingerPath = new HashMap<>();
        for (char from: dirpad.keySet()) {
            for (char to: dirpad.keySet()) {
                fingerPath.put("" + from + to, getSequences(from,to, dirpad, 0));
            }
        }
    }

    static final Map<Character, Point> keypad;
    static {
        keypad = new HashMap<>();
        keypad.put('7',new Point(0,0));
        keypad.put('8',new Point(1,0));
        keypad.put('9',new Point(2,0));

        keypad.put('4',new Point(0,1));
        keypad.put('5',new Point(1,1));
        keypad.put('6',new Point(2,1));

        keypad.put('1',new Point(0,2));
        keypad.put('2',new Point(1,2));
        keypad.put('3',new Point(2,2));

        keypad.put('0',new Point(1,3));
        keypad.put('A',new Point(2,3));
    }
}

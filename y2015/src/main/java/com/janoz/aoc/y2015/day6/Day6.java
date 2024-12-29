package com.janoz.aoc.y2015.day6;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.janoz.aoc.geo.BoundingBox;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.input.AocInput;

public class Day6 {

    public static void main(String[] args) {
        solve(AocInput.of(2015, 6));
    }

    static Set<Point> lights = new HashSet<>();
    static Map<Point,Long> brightness = new HashMap<>();

    static void solve(AocInput<String> input) {
        input.stream().forEach(Day6::doAction);
        System.out.println("Part 1 :" + lights.size());
        System.out.println("Part 2 :" + brightness.values().stream().mapToLong(Long::longValue).sum());
    }

    static void doAction(String command) {
        int i = command.indexOf("through");
        if (command.startsWith("toggle")) {
            new BoundingBox(
                    Point.parse(command.substring(7,i-1)),
                    Point.parse(command.substring(i+8))).streamAllPoints().forEach(Day6::toggle);
        } else if (command.startsWith("turn on")) {
            new BoundingBox(
                    Point.parse(command.substring(8,i-1)),
                    Point.parse(command.substring(i+8))).streamAllPoints().forEach(Day6::on);
        } else if (command.startsWith("turn off")) {
            new BoundingBox(
                    Point.parse(command.substring(9,i-1)),
                    Point.parse(command.substring(i+8))).streamAllPoints().forEach(Day6::off);
        }
    }

    static void toggle(Point p) {
        if (lights.contains(p)) lights.remove(p);
        else lights.add(p);
        brightness.merge(p,2L,Long::sum);
    }

    static void on(Point p) {
        lights.add(p);
        brightness.merge(p,1L,Long::sum);
    }

    static void off(Point p) {
        lights.remove(p);
        if (brightness.containsKey(p)) {
            brightness.put(p, Math.max(0, brightness.get(p) - 1L));
        }
    }
}

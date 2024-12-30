package com.janoz.aoc.y2015.day14;

import com.janoz.aoc.input.AocInput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


public class Day14 {


    public static void main(String[] args) {
        solve(AocInput.of(2015,14));
    }

    static void solve(AocInput<String> input) {
        List<Reindeer> reindeers = input.stream().map(Reindeer::new).toList();
        System.out.println("Part 1 :" + reindeers.stream().mapToInt(r -> r.getPositionAt(2503)).max().getAsInt());

        Map<String, Integer> scores = new HashMap<>();
        IntStream.rangeClosed(1,2503).forEach(i -> {
            int max = reindeers.stream().mapToInt(r -> r.getPositionAt(i)).max().getAsInt();
            reindeers.stream().filter(r -> r.getPositionAt(i) == max).forEach(r -> scores.merge(r.name, 1, Integer::sum));
        });
        System.out.println("Part 2 :" + scores.values().stream().mapToInt(Integer::intValue).max().getAsInt());
    }

    static class Reindeer {
        final String name;
        final int speed;
        final int runtime;
        final int resttime;

        public Reindeer(String input) {
            String[] parts = input.split(" ");
            this.name = parts[0];
            this.speed = Integer.parseInt(parts[3]);
            this.runtime = Integer.parseInt(parts[6]);
            this.resttime = Integer.parseInt(parts[13]);
        }

        public int getPositionAt(int seconds) {
            int total =0;
            int interval = runtime + resttime;
            int cycles = seconds / interval;
            total += speed * cycles * runtime;
            int secondsLeft = seconds - (cycles * interval);
            return total + (Math.min(runtime, secondsLeft) * speed);
        }
    }
}

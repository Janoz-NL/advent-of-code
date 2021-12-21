package com.janoz.aoc.y2021.day21;

import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.collections.LongTuple;

import java.util.HashMap;
import java.util.Map;

public class Day21 {

    static final Dice dice = new Dice();
    static final Player p1 = new Player(3);
    static final Player p2 = new Player(7);

    static final int[] possibilities=new int[10];
    static {
        for (int d1 = 1;d1<=3;d1++) {
            for (int d2 = 1; d2 <= 3; d2++) {
                for (int d3 = 1; d3 <= 3; d3++) {
                    possibilities[d1+d2+d3]++;
                }
            }
        }
    }

    static AlwaysHashMap<Player, Map<Player, LongTuple>> cache = new AlwaysHashMap<>(HashMap::new);

    public static void main(String[] args) {
        System.out.println(part1());
        System.out.println(part2());
    }

    static int part1() {
        Player turn = p1;
        Player other = p2;
        while (true) {
            Player moved = turn.moved(dice.next() + dice.next() + dice.next());
            if (moved.score >= 1000) return other.score * dice.timesRolled;
            turn = other;
            other = moved;
        }
    }

    static long part2() {
        LongTuple result = splitUniverses(p1,p2);
        return Math.max(result.getLeft(), result.getRight());
    }

    static LongTuple splitUniverses(Player p1, Player p2) {
        LongTuple result = cache.get(p1).get(p2);
        if (result != null) return result;
        result = new LongTuple();
        Player played;
        for (int d=3; d<10; d++) {
            played = p1.moved(d);
            if (played.score >= 21) result = result.incLeft(possibilities[d]);
            else result = result.add(splitUniverses(p2,played).switched().times(possibilities[d]));
        }
        cache.get(p1).put(p2, result);
        return result;
    }
}

package com.janoz.aoc.y2015.day21;

import com.janoz.aoc.collections.CollectionUtils;
import com.janoz.aoc.input.AocInput;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day21 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,21));
    }

    static Player boss;

    static void solve(AocInput<String> input) {
        Iterator<Integer> it = input.addMapper(s -> s.split(": ")[1].trim()).addMapper(Integer::valueOf).iterator();
        boss = new Player(it.next(), it.next(), it.next());
        int[] answers = findLoadoutsCosts();

        System.out.println("Part 1: " + answers[0]);
        System.out.println("Part 2: " + answers[1]);
    }


    static int[] findLoadoutsCosts() {
        int winGold = Integer.MAX_VALUE;
        int loseGold = 0;
        for (Item w:weapons) {
            for (Item a:armor) {
                for (Item r:rings) {
                    Player player = new Player(100,w.damage + r.damage, a.armor+r.armor);
                    if (doesPlayerWinBattle(player)) {
                        winGold = Math.min(winGold, w.gold + a.gold + r.gold);
                    } else {
                        loseGold = Math.max(loseGold, w.gold + a.gold + r.gold);

                    }
                }
            }
        }
        return new int[]{winGold,loseGold};
    }

    static boolean doesPlayerWinBattle(Player player) {
        int damagePlayer = Math.max(1, player.damage - boss.armor);
        int damageBoss   = Math.max(1, boss.damage - player.armor);
        return deadInTurns(player.hitpoints,damageBoss) > deadInTurns(boss.hitpoints, damagePlayer);
    }

    static int deadInTurns(int hitpoints, int damage) {
        return 1 + ((hitpoints-1)/damage);
    }


    record Player(int hitpoints, int damage, int armor){}

    record Item(int gold, int damage, int armor) implements Comparable<Item>{
        @Override
        public int compareTo(Item o) {
            return Integer.compare(gold, o.gold);
        }

        public Item add(Item item) {
            return new Item(item.gold + gold, item.damage + damage, item.armor + armor);
        }
    }

    static final List<Item> weapons;
    static {
        weapons = new ArrayList<>();
        weapons.add(new Item(8,4,0));
        weapons.add(new Item(10,5,0));
        weapons.add(new Item(25,6,0));
        weapons.add(new Item(40,7,0));
        weapons.add(new Item(74,8,0));
    }

    static final List<Item> armor;
    static {
        armor = new ArrayList<>();
        armor.add(new Item(0,0,0)); // no armor
        armor.add(new Item(13,0,1));
        armor.add(new Item(31,0,2));
        armor.add(new Item(53,0,3));
        armor.add(new Item(75,0,4));
        armor.add(new Item(102,0,5));
    }

    static final List<Item> rings;
    static {
        Set<Item> singleRings = new HashSet<>();
        singleRings.add(new Item(25, 1, 0));
        singleRings.add(new Item(50, 2, 0));
        singleRings.add(new Item(100, 3, 0));
        singleRings.add(new Item(20, 0, 1));
        singleRings.add(new Item(40, 0, 2));
        singleRings.add(new Item(80, 0, 3));
        rings = CollectionUtils.combinations(singleRings,0,2).stream().map(l -> {
            Item i = new Item(0,0,0);
            for (Item item : l) {
                i = i.add(item);
            }
            return i;
        }).sorted().collect(Collectors.toList());
    }
}

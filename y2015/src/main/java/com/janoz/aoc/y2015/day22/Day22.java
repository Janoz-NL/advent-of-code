package com.janoz.aoc.y2015.day22;

import com.janoz.aoc.input.AocInput;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.PriorityQueue;

public class Day22 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,22));
    }

    static void solve(AocInput<String> input) {
        Iterator<Integer> it = input.addMapper(s -> s.split(": ")[1].trim()).addMapper(Integer::valueOf).iterator();
        Integer bossHitpoints = it.next();
        Integer bossDamage = it.next();
        System.out.println("Part 1 : " + battle(50,500, bossHitpoints, bossDamage, false));
        System.out.println("Part 2 : " + battle(50,500, bossHitpoints, bossDamage, true));
    }

    static int battle(int hitpoints, int mana, int bossHitpoints, int bossDamage, boolean hard) {
        PriorityQueue<Round> rounds = new PriorityQueue<>();
        rounds.add(new Round(hitpoints,mana,bossHitpoints, bossDamage, hard));
        while (!rounds.isEmpty()) {
            Round round = rounds.poll();
            if (round.bossDead()) {
                return round.manaSpend;
            }
            Arrays.stream(Move.values()).map(round::doRound).filter(Objects::nonNull).forEach(rounds::add);
        }
        return -1;
    }


    static class Round implements Comparable<Round> {
        final int hitpoints;
        final int mana;

        final int bossHitpoints;
        final int bossDamage;

        final int shieldEffect;
        final int poisonEffect;
        final int rechargeEffect;

        final int manaSpend;
        final int hard;

        public Round(int hitpoints, int mana, int bossHitpoints, int bossDamage, boolean hard) {
            this(hitpoints,mana,bossHitpoints,bossDamage,0,0,0,0, hard?1:0);
        }

        public Round(int hitpoints, int mana, int bossHitpoints, int bossDamage, int shieldEffect, int poisonEffect, int rechargeEffect, int manaSpend, int hard) {
            this.mana = mana;
            this.hitpoints = hitpoints;
            this.bossDamage = bossDamage;
            this.bossHitpoints = bossHitpoints;
            this.shieldEffect = shieldEffect;
            this.poisonEffect = poisonEffect;
            this.rechargeEffect = rechargeEffect;
            this.manaSpend = manaSpend;
            this.hard = hard;
        }

        public boolean bossDead() {
            return bossHitpoints <= 0;
        }

        /**
         * Play a round of turns
         * @param move move done by the player
         * @return the state after the player and the boss did a turn or null when player died or move was illegal
         */
        public Round doRound(Move move) {
            if (shieldEffect > 1 && Move.SHIELD.equals(move)) return null;
            if (poisonEffect > 1 && Move.POISON.equals(move)) return null;
            if (rechargeEffect > 1 && Move.RECHARGE.equals(move)) return null;

            //Effects
            int actualMana = mana + ((rechargeEffect > 0)?101:0);
            int actualBossHitpoints = bossHitpoints - ((poisonEffect > 0)?3:0);
            int actualHitpoints = hitpoints - hard;
            if (actualBossHitpoints <= 0) {
                //boss dead so no turns
                return new Round(
                        actualHitpoints, actualMana,
                        actualBossHitpoints, bossDamage,
                        Math.max(0,shieldEffect-1),
                        Math.max(0,poisonEffect-1),
                        Math.max(0,rechargeEffect-1),
                        manaSpend, hard);
            }

            //Player
            actualMana -= move.mana;
            if (actualMana < 0) return null;

            int actualShieldEffect = Move.SHIELD.equals(move)?6:(shieldEffect-1);
            int actualPoisonEffect = Move.POISON.equals(move)?6:(poisonEffect-1);
            int actualRechargeEffect = Move.RECHARGE.equals(move)?5:(rechargeEffect-1);

            if (Move.DRAIN.equals(move)) {
                actualHitpoints += 2;
                actualBossHitpoints -= 2;
            }
            if (Move.MISSILE.equals(move)) {
                actualBossHitpoints -= 4;
            }

            //Effects
            actualBossHitpoints -= ((actualPoisonEffect > 0)?3:0);
            actualMana += ((actualRechargeEffect > 0)?101:0);

            //Boss
            if (actualBossHitpoints >0) {
                actualHitpoints -= Math.max(1, bossDamage - (actualShieldEffect > 0?7:0));;
            }

            if (actualHitpoints <= hard) return null;
            return new Round(
                    actualHitpoints,actualMana,
                    actualBossHitpoints,bossDamage,
                    Math.max(0,actualShieldEffect-1),
                    Math.max(0,actualPoisonEffect-1),
                    Math.max(0,actualRechargeEffect-1),
                    manaSpend + move.mana, hard);
        }

        @Override
        public int compareTo(Round o) {
            return Integer.compare(manaSpend, o.manaSpend);
        }
    }

    enum Move {
        MISSILE(53),
        DRAIN(73),
        SHIELD(113),
        POISON(173),
        RECHARGE(229);

        final int mana;

        Move(int mana) {
            this.mana = mana;
        }
    }
}

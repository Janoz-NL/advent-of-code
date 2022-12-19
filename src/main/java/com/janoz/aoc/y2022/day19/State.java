package com.janoz.aoc.y2022.day19;

import java.util.Arrays;
import java.util.stream.Stream;

class State {
    private final Blueprint bp;
    private final int minutesLeft;
    private final int previousRobot;
    private final int[] storage = new int[4];
    private final int[] robots = new int[4];

    private final int[][] potential;

    public State(Blueprint bp, int minutesLeft) {
        this.bp = bp;
        this.minutesLeft = minutesLeft;
        robots[Blueprint.ORE] = 1;
        previousRobot = 5; //limbo
        potential = potentials();
    }

    public State next() {
        return new State(this);
    }

    public State next(Blueprint.Cost cost) {
        return new State(this, cost);
    }

    public Stream<State> possibleNext() {
        if (potential[0][Blueprint.GEODE] == potential[1][Blueprint.GEODE]) {
            //no more change
            return Stream.empty();
        }

        //always build geode
        Blueprint.Cost geodeCost = bp.costs[Blueprint.GEODE];
        if (affordable(geodeCost)) return Stream.of(next(geodeCost));

        //always build obsidian (not sure though...)
        Blueprint.Cost obsidianCost = bp.costs[Blueprint.OBSIDIAN];
        if (affordable(obsidianCost)) return Stream.of(next(obsidianCost));


        return Stream.concat(
                //build nothing
                Stream.of(next()),
                //build something
                Arrays.stream(bp.costs)

                        //can build
                        .filter(this::affordable)

                        //should build
                        .filter(this::notPreviouslyAffordable)

                        //needed
                        .filter(cost -> robotNeeded(cost.produces))

                        .map(this::next));
    }

    /* * * * * * * * *
     * Filters here
     * * * * * * * * */

    private boolean robotNeeded(int raw) {
        return robots[raw] < bp.maxCosts[raw];
    }

    private boolean notPreviouslyAffordable(Blueprint.Cost cost) {
        if (previousRobot != -1) return true; //build something last time

        for (int i = 0; i < 4; i++) {
            if (cost.costs[i] > storage[i] - robots[i]) return true;
        }
        return false;
    }

    private boolean affordable(Blueprint.Cost cost) {
        for (int i = 0; i < 4; i++) {
            if (cost.costs[i] > storage[i]) return false;
        }
        return true;
    }

    /* * * * * * * * *
     * Filters end
     * * * * * * * * */

    public int[] getMinPotential() {
        return potential[0];
    }

    public int[] getMaxPotential() {
        return potential[1];
    }

    private State(State previous) {
        this.bp = previous.bp;
        this.minutesLeft = previous.minutesLeft - 1;
        for (int i = 0; i < 4; i++) {
            this.storage[i] = previous.storage[i] + previous.robots[i];
            this.robots[i] = previous.robots[i];
        }
        previousRobot = -1;
        potential = potentials();
    }

    private State(State previous, Blueprint.Cost appliedCost) {
        this.bp = previous.bp;
        this.minutesLeft = previous.minutesLeft - 1;
        for (int i = 0; i < 4; i++) {
            this.storage[i] = previous.storage[i] - appliedCost.costs[i] + previous.robots[i];
            this.robots[i] = previous.robots[i];
        }
        this.robots[appliedCost.produces]++;
        previousRobot = appliedCost.produces;
        potential = potentials();
    }

    private int[][] potentials() {
        int[][] result = new int[2][4];

        int timeLeft = minutesLeft;
        if (timeLeft == 0) return new int[][]{storage, storage};

        //min endresult when no more robots build;
        for (int i = 0; i < 4; i++) {
            result[0][i] = storage[i] + (timeLeft * robots[i]);
        }

        int maxRobots;
        //max ore
        maxRobots = Math.max(0, bp.maxCosts[Blueprint.ORE] - robots[Blueprint.ORE]);
        int[] maxOre = new int[timeLeft];
        for (int i = 0; i <= maxRobots; i++) {
            replaceIfBigger(maxOre, getConstructed(robots[Blueprint.ORE], storage[Blueprint.ORE], bp.costs[Blueprint.ORE].costs[Blueprint.ORE], i, timeLeft));
        }
        result[1][Blueprint.ORE] = maxOre[timeLeft - 1];

        //max clay
        maxRobots = Math.max(0, bp.maxCosts[Blueprint.CLAY] - robots[Blueprint.CLAY]);
        int[] maxClay = new int[timeLeft];
        for (int i = 0; i <= maxRobots; i++) {
            replaceIfBigger(maxClay, getConstructed(robots[Blueprint.CLAY], storage[Blueprint.CLAY],
                    new int[][]{new int[timeLeft], maxOre},
                    new int[]{0, bp.costs[Blueprint.CLAY].costs[Blueprint.ORE]},
                    maxRobots, timeLeft));
        }
        result[1][Blueprint.CLAY] = maxClay[timeLeft - 1];

        //max obsidian
        maxRobots = Math.max(0, bp.maxCosts[Blueprint.OBSIDIAN] - robots[Blueprint.OBSIDIAN]);
        int[] maxObsidian = new int[timeLeft];
        for (int i = 0; i <= maxRobots; i++) {
            replaceIfBigger(maxObsidian, getConstructed(robots[Blueprint.OBSIDIAN], storage[Blueprint.OBSIDIAN],
                    new int[][]{maxOre, maxClay},
                    new int[]{bp.costs[Blueprint.OBSIDIAN].costs[Blueprint.ORE], bp.costs[Blueprint.OBSIDIAN].costs[Blueprint.CLAY]},
                    maxRobots, timeLeft));

        }
        result[1][Blueprint.OBSIDIAN] = maxObsidian[timeLeft - 1];

        //max geode
        int[] maxGeode = getConstructed(robots[Blueprint.GEODE], storage[Blueprint.GEODE],
                new int[][]{maxOre, maxObsidian},
                new int[]{bp.costs[Blueprint.GEODE].costs[Blueprint.ORE], bp.costs[Blueprint.GEODE].costs[Blueprint.OBSIDIAN]},
                24, timeLeft);

        result[1][Blueprint.GEODE] = maxGeode[timeLeft - 1];

        return result;
    }

    // only for ore
    private static int[] getConstructed(int robots, int currentAmount, int cost, int maxRobot, int maxSteps) {
        int[] result = new int[maxSteps];
        int currentStep = 0;
        while (currentStep < maxSteps) {
            if (robots < maxRobot && currentAmount >= cost) {
                robots++;
                currentAmount -= (cost + 1);
            }
            currentAmount = currentAmount + robots;
            result[currentStep] = currentAmount;
            currentStep ++;
        }
        return result;
    }

    private static int[] getConstructed(int robots, int currentAmount, int[][] available, int[] cost, int maxRobot, int maxSteps) {
        int[] result = new int[maxSteps];
        int currentStep = 0;
        int[] spend = new int[2];
        while (currentStep < maxSteps) {
            currentAmount = currentAmount + robots;
            if (robots < maxRobot &&
                    (available[0][currentStep] - spend[0]) >= cost[0] &&
                    (available[1][currentStep] - spend[1]) >= cost[1]) {
                spend[0] += cost[0];
                spend[1] += cost[1];
                robots++;
            }
            result[currentStep] = currentAmount;
            currentStep ++;
        }
        return result;
    }

    private static void replaceIfBigger(int[] src, int[] candidates) {
        for (int i=0; i<src.length; i++) {
            src[i] = Math.max(src[i], candidates[i]);
        }
    }

    @Override
    public String toString() {
        return "State{" +
                "minute=" + minutesLeft +
                ", \tpreviousRobot=" + previousRobot +
                ", \tstorage=" + Arrays.toString(storage) +
                ", \trobots=" + Arrays.toString(robots) +
                ", \tpotentialMin=" + Arrays.toString(potential[0]) +
                ", \tpotentialMax=" + Arrays.toString(potential[1]) +
                '}';
    }
}

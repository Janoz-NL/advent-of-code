package com.janoz.aoc.y2022.day2;

public enum RPS {
    ROCK,
    PAPER,
    SCISSOR;

    public static RPS fromChar(char s) {
        switch (s) {
            case 'A':
            case 'X':
                return ROCK;
            case 'B':
            case 'Y':
                return PAPER;
            case 'C':
            case 'Z':
                return SCISSOR;
        }
        throw new RuntimeException("No type for "  + s);
    }

    public int battle(RPS other) {
        if (this == ROCK && other == SCISSOR) {
            return 1;
        }
        if (this == SCISSOR && other == ROCK) {
            return -1;
        }
        return this.ordinal() - other.ordinal();
    }

    public RPS withOutcome(char s) {
        switch (s) {
            case 'X':
                return RPS.values()[(this.ordinal()+2)%3];
            case 'Y':
                return this;
            case 'Z':
                return RPS.values()[(this.ordinal()+1)%3];
        }
        throw new RuntimeException("No outcome for " + s);
    }
}

package com.janoz.aoc.y2024.day17;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class ChronospatialComputer {

    long a;
    long b;
    long c;

    int[] program;

    int pointer;

    List<Integer> output = new ArrayList<>();

    public void reset(long a, long b, long c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.pointer = 0;
        this.output.clear();
    }

    public ChronospatialComputer(long a, long b, long c, int[] program) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.program = program;
        this.pointer = 0;
    }

    String run() {
        while (pointer < program.length) {
            opcode(program[pointer], program[pointer + 1]);
            pointer += 2;
        }
        return output.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    int[] getOutput() {
        return output.stream().mapToInt(Integer::intValue).toArray();
    }


    long getCombo(int combo) {
        switch (combo) {
            case 4:
                return a;
            case 5:
                return b;
            case 6:
                return c;
            default:
                return combo;
        }
    }

    void opcode(int opcode, int operand) {
        switch (opcode) {
            case 0:
                a = a / (1L << getCombo(operand));
                break;
            case 1:
                b = b ^ operand;
                break;
            case 2:
                b = getCombo(operand) & 7;
                break;
            case 3:
                pointer = a==0?pointer:operand - 2;
                break;
            case 4:
                b = b ^ c;
                break;
            case 5:
                output.add((int)(getCombo(operand) & 7));
                break;
            case 6:
                b = a / (1L << getCombo(operand));
                break;
            case 7:
                c = a / (1L << getCombo(operand));
                break;
            default:
                throw new RuntimeException("Unknown opcode " + opcode);
        }
    }

}

package com.janoz.aoc.y2015.day23;

import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.input.AocInput;

import java.util.List;
import java.util.Map;

public class Day23 {

    public static void main(String[] args) {
        solve(AocInput.of(2015,23));
    }

    static void solve(AocInput<String> input) {
        List<Instruction> program = input.addMapper(Instruction::new).asList();
        run(program);
        System.out.println("Part 1: " + registers.get('b'));
        registers.clear();
        registers.put('a', 1);
        run(program);
        System.out.println("Part 2: " + registers.get('b'));
    }


    static void run(List<Instruction> program) {
        int pointer = 0;
        while (pointer < program.size()) {
            Instruction i = program.get(pointer);
            switch (i.command) {
                case HLF:
                    registers.put(i.register,registers.get(i.register) / 2);
                    pointer++;
                    break;
                case TPL:
                    registers.put(i.register,registers.get(i.register) * 3);
                    pointer++;
                    break;
                case INC:
                    registers.put(i.register,registers.get(i.register) + 1);
                    pointer++;
                    break;
                case JMP:
                    pointer += i.parameter;
                    break;
                case JIE:
                    if (registers.get(i.register) % 2 == 0) {
                        pointer += i.parameter;
                    } else {
                        pointer++;
                    }
                    break;
                case JIO:
                    if (registers.get(i.register) == 1) {
                        pointer += i.parameter;
                    } else {
                        pointer++;
                    }
                    break;
            }
        }
    }


    static Map<Character, Integer> registers = new AlwaysHashMap<>(() -> 0);


    static class Instruction {
        final Command command;
        final Character register;
        final int parameter;

        Instruction(String instruction) {
            command = Command.valueOf(instruction.substring(0,3).toUpperCase());
            if (Command.JMP.equals(command)) {
                parameter = Integer.parseInt(instruction.substring(4));
                register = null;
            } else {
                register = instruction.charAt(4);
                if (instruction.length() > 5) {
                    parameter = Integer.parseInt(instruction.substring(7));
                } else {
                    parameter = 0;
                }
            }
        }
    }

    enum Command {
        HLF,
        TPL,
        INC,
        JMP,
        JIE,
        JIO
    }



}

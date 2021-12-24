package com.janoz.aoc.y2021.day24;

import com.janoz.aoc.InputProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class ALU {

    long[] register = new long[4];

    int inputIndex = 0;
    String input;
    int nextInput() {
        return input.charAt(inputIndex++) - '0';
    }



    List<Operation> program;


    void printRegisters() {
        for (int i=0; i<4; i++) System.out.printf("%5d ",register[i]);
        System.out.println();
    }

    void findLargestModelNr() {
        for (long l = 99998499111111L; l>0; l--) {
            input = Long.toString(l);
            if (input.contains("0")) continue;

            run(input);

            if (input.charAt(7) == '9')
                System.out.println(input + " -> " + register[3]);
            if (register[3] == 0) {
                System.out.println("FOUND : " + input);
                break;
            }
        }
    }

    void run(String input, int w, int x, int y, int z) {
        this.input = input;
        register[0] = w;
        register[1] = x;
        register[2] = y;
        register[3] = z;
        inputIndex = 0;
        program.forEach(Operation::execute);
    }


    void run(String input) {
        this.input = input;
        Arrays.fill(register,0);
        inputIndex = 0;
        program.forEach(Operation::execute);
    }

    void compile(String input) {
        program = new InputProcessor<>(input,s -> new Operation(this,s)).stream().collect(Collectors.toList());
    }


    public static void main(String[] args) {
        ALU alu = new ALU();
        alu.compile("inputs/day24.txt");
        alu.findLargestModelNr();
    }


}

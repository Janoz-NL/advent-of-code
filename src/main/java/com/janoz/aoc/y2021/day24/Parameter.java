package com.janoz.aoc.y2021.day24;

import java.util.Arrays;

public class Parameter {

    private ALU alu;



    Long constant;
    Integer index;


    private Parameter(ALU alu){
        this.alu = alu;
    }

    long get() {
        if (constant != null) {
            return constant;
        } if (index != null) {
            return alu.register[index];
        } else {
            return alu.nextInput();
        }
    }

    void put(long result) {
        alu.register[index] = result;
    }

    static Parameter constant(long val, ALU alu) {
        Parameter result = new Parameter(alu);
        result.constant = val;
        return result;
    }

    static Parameter variable(int index, ALU alu) {
        Parameter result = new Parameter(alu);
        result.index = index;
        return result;
    }

    static Parameter input(ALU alu) {
        return new Parameter(alu);
    }

    static Parameter parse(String input, ALU alu) {
        switch(input) {
            case "w" : return variable(0, alu);
            case "x" : return variable(1, alu);
            case "y" : return variable(2, alu);
            case "z" : return variable(3, alu);
            default: return constant(Integer.parseInt(input), alu);
        }

    }
}

package com.janoz.aoc.y2021.day24;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {


    @Test
    void testModA() {
        ALU alu = new ALU();
        alu.register[0] = -1L;
        Parameter out = Parameter.variable(0,alu);
        assertThrows(ArithmeticException.class, ()->Operation.MOD.accept(out, Parameter.constant(1,alu)));
    }

    @Test
    void testModB() {
        ALU alu = new ALU();
        alu.register[0] = 1;
        Parameter out = Parameter.variable(0,alu);
        assertThrows(ArithmeticException.class, ()->Operation.MOD.accept(out, Parameter.constant(0,alu)));
    }


    @Test
    void testDiv() {
        ALU alu = new ALU();
        alu.register[0] = 1;
        Parameter out = Parameter.variable(0,alu);
        assertThrows(ArithmeticException.class, ()->Operation.MOD.accept(out, Parameter.constant(0,alu)));
    }

}


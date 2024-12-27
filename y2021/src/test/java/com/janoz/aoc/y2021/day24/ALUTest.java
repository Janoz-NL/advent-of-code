package com.janoz.aoc.y2021.day24;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ALUTest {

    @Test
    void testToBit() {
        ALU alu = new ALU();
        alu.compile("inputs/day24toBit.txt");

        alu.run("0");
        assertThat(alu.register[0]).isEqualTo(0L);
        assertThat(alu.register[1]).isEqualTo(0L);
        assertThat(alu.register[2]).isEqualTo(0L);
        assertThat(alu.register[3]).isEqualTo(0L);

        alu.run("9");
        assertThat(alu.register[0]).isEqualTo(1L);
        assertThat(alu.register[1]).isEqualTo(0L);
        assertThat(alu.register[2]).isEqualTo(0L);
        assertThat(alu.register[3]).isEqualTo(1L);

        alu.run("6");
        assertThat(alu.register[0]).isEqualTo(0L);
        assertThat(alu.register[1]).isEqualTo(1L);
        assertThat(alu.register[2]).isEqualTo(1L);
        assertThat(alu.register[3]).isEqualTo(0L);
    }

    @Test
    void testEql() {
        ALU alu = new ALU();
        alu.compile("inputs/day24eql.txt");

        alu.run("39");
        assertThat(alu.register[3]).isEqualTo(1L);

        alu.run("13");
        assertThat(alu.register[3]).isEqualTo(1L);

        alu.run("21");
        assertThat(alu.register[3]).isEqualTo(0L);
    }

    @Test
    void testDigit1() {
        ALU alu = new ALU();
        alu.compile("inputs/day24part01.txt");
        for (char c = '1'; c <= '9'; c++) {
            alu.run(""+c);
            alu.printRegisters();
            System.out.println(answer(0,1,14,16,c-'0'));
        }
    }


    @Test
    void testDigit2() {
        ALU alu = new ALU();
        alu.compile("inputs/day24part02.txt");
        for (char c = '1'; c <= '9'; c++) {
//            alu.run(""+c);
            alu.run(""+c, 9,1,25,25);
            alu.printRegisters();
        }
    }

    @Test
    void testDigit14() {
        ALU alu = new ALU();
        alu.compile("inputs/day24part14.txt");
        for (char c = '1'; c <= '9'; c++) {
            alu.run(""+c);
            alu.printRegisters();
            System.out.println(answer(0,26,-12,6,c-'0'));
        }
    }

    @Test
    void testDigit14More() {
        ALU alu = new ALU();
        alu.compile("inputs/day24part14.txt");
        for (int z=0; z<25; z++) {
            for (char c = '1'; c <= '9'; c++) {
                alu.run("" + c, 0, 0, 0, z);
                assertThat(answer(z,26,-12,6,c-'0')).isEqualTo(alu.register[3]);
            }
        }

        alu.compile("inputs/day24part01.txt");
        for (int z=0; z<25; z++) {
            for (char c = '1'; c <= '9'; c++) {
                alu.run("" + c, 0, 0, 0, z);
                assertThat(answer(z,1,14,16,c-'0')).isEqualTo(alu.register[3]);
            }
        }

        alu.compile("inputs/day24part02.txt");
        for (int z=0; z<25; z++) {
            for (char c = '1'; c <= '9'; c++) {
                alu.run("" + c, 0, 0, 0, z);
                assertThat(answer(z,1,11,3,c-'0')).isEqualTo(alu.register[3]);
            }
        }
    }



    static long answer(int z, int a, int b, int c, int input) {
        if (a == 1) {
            z = z * 26 + input + c;
        } else {
            if ((z % 26) != input - b) {
                z = z / 26;
                z = z * 26 + input + c;
            } else {
                z = z / 26;
            }
        }
        return z;
    }




}
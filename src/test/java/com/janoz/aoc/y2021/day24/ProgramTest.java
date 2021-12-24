package com.janoz.aoc.y2021.day24;

import com.janoz.aoc.InputProcessor;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProgramTest {

    @Test
    void testProgram() {
        List<String> operations = new InputProcessor<>("inputs/day24.txt", s -> s).asList();
        boolean print;
        for (int i = 0; i< 18; i++) {
            print = false;
            for (int j=1;j<14;j++) {
                if (!operations.get(i).equals(operations.get(i + 18*j))) {
                    print = true;
                    break;
                }
            }
            if (print) {
                for (int j = 0; j < 14; j++) {
                    System.out.print(operations.get(i + 18 * j) + " \t");
                }
                System.out.println();
            }
        }
    }

}

package com.janoz.aoc.y2021.day22;

import com.janoz.aoc.InputProcessor;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class Day22 {


    static Set<Cube> reactor = new HashSet<>();

    public static void main(String[] args) {
        part2("inputs/day22.txt");
        System.out.println(reactor.stream().mapToLong(Cube::volumne).mapToObj(BigInteger::valueOf).reduce(BigInteger.ZERO,BigInteger::add));
    }


    static void part1(String input) {
        switcherroo(input,c -> c.cube.max.x<=51 && c.cube.min.x>=-50);
    }

    static void part2(String input) {
        switcherroo(input,c -> true);
    }

    static boolean cutReactor(Set<Cube> others, boolean willBeAdded) {
        for (Cube rc: reactor) {
            for (Cube other: others) {
                if (rc.intersects(other)) {
                    if (!rc.equals(other)) {
                        Set<Cube>[] newCubes = rc.cut(other);
                        if (willBeAdded) {
                            others.remove(other);
                            newCubes[1].removeAll(newCubes[0]);
                            others.addAll(newCubes[1]);
                        } else {
                            reactor.remove(rc);
                            newCubes[0].removeAll(newCubes[1]);
                            reactor.addAll(newCubes[0]);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }


    static void switcherroo(String input, Predicate<BoolCube> filter) {
        new InputProcessor<>(input, BoolCube::parse).stream()
                .filter(filter)
                .forEach(Day22::switchCube);
    }



    static int cube = 0;

    static void switchCube(BoolCube c) {
        System.out.println("step " + (cube++) + ", cubes in reactor:" + reactor.size());
        Set<Cube> others = new HashSet<>();
        others.add(c.cube);
        while (cutReactor(others, c.state));
        if (c.state) {
            reactor.addAll(others);
        } else {
            reactor.removeAll(others);
        }
    }


    public static class BoolCube {
        Cube cube;
        boolean state;

        static BoolCube parse(String input) {
            BoolCube result = new BoolCube();
            String[] parts=input.split(" ");
            result.state = parts[0].equals("on");
            parts = parts[1].split(",");
            for (int i=0; i<3; i++) {
                parts[i] = parts[i].split("=")[1];
            }
            String[] x = parts[0].split("\\.\\.");
            String[] y = parts[1].split("\\.\\.");
            String[] z = parts[2].split("\\.\\.");
            result.cube = new Cube(
                    Integer.min(Integer.parseInt(x[0]),Integer.parseInt(x[1])),
                    Integer.min(Integer.parseInt(y[0]),Integer.parseInt(y[1])),
                    Integer.min(Integer.parseInt(z[0]),Integer.parseInt(z[1])),
                    Integer.max(Integer.parseInt(x[0]),Integer.parseInt(x[1])) + 1,
                    Integer.max(Integer.parseInt(y[0]),Integer.parseInt(y[1])) + 1,
                    Integer.max(Integer.parseInt(z[0]),Integer.parseInt(z[1])) + 1);
            return result;
        }
    }

}

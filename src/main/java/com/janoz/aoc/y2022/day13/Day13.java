package com.janoz.aoc.y2022.day13;

import com.janoz.aoc.InputProcessor;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Day13 {

    public static void main(String[] args) {
        String file = "inputs/2022/day13.txt";
        part1(file);
        part2(file);
        validate(file);
    }

    static void part1(String file) {
        Iterator<String> iterator = InputProcessor.asIterator(file);
        int i=0;
        int sum=0;
        while (iterator.hasNext()) {
            i++;
            if (new Package(iterator.next()).compareTo(new Package(iterator.next())) < 0) {
                sum += i;
            }
            if (iterator.hasNext()) iterator.next();
        }
        System.out.println("Part 1: " + sum);
    }

    static void part2(String file) {
        Iterator<String> iterator = InputProcessor.asIterator(file);

        SortedSet<Package> packages = new TreeSet<>();

        Package div1 = new Package("[[2]]");
        Package div2 = new Package("[[6]]");

        packages.add(div1);
        packages.add(div2);

        while (iterator.hasNext()) {
            packages.add(new Package(iterator.next()));
            packages.add(new Package(iterator.next()));
            if (iterator.hasNext()) iterator.next();
        }
        System.out.println("Part 2: "+ (packages.headSet(div1).size() + 1) * (packages.headSet(div2).size() + 1));
    }

    static void validate(String file){
        Iterator<String> iterator = InputProcessor.asIterator(file);
        while (iterator.hasNext()){
            compare(iterator.next());
            compare(iterator.next());
            if (iterator.hasNext()) iterator.next();
        }
    }

    static void compare(String line) {
        String processed = new Package(line).asString();
        if (!processed.equals(line)) {
            System.out.println(line);
            System.out.println(processed);
            System.out.println();
        }
    }

}

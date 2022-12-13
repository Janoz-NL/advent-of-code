package com.janoz.aoc.y2022.day13;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class Day13 {


    public static final Package DIV_1 = new Package("[[2]]");
    public static final Package DIV_2= new Package("[[6]]");


    public static void main(String[] args) {
        String file = "inputs/2022/day13.txt";
        validate(file);

        StopWatch.start();
        part1(file);
        StopWatch.stopPrint();

        StopWatch.start();
        part2(file);
        StopWatch.stopPrint();

        StopWatch.start();
        both(file);
        StopWatch.stopPrint();

    }

    static void both(String file) {
        Iterator<String> iterator = InputProcessor.asIterator(file);
        long i=0;
        long sum=0;
        long iDiv1 = 1;
        long iDiv2 = 2;
        Package p1,p2;
        while (iterator.hasNext()) {
            i++;
            p1 = new Package(iterator.next());
            p2 = new Package(iterator.next());
            if (p1.compareTo(p2) < 0) sum += i;
            if (p1.compareTo(DIV_1) < 0) iDiv1++;
            if (p2.compareTo(DIV_1) < 0) iDiv1++;
            if (p1.compareTo(DIV_2) < 0) iDiv2++;
            if (p2.compareTo(DIV_2) < 0) iDiv2++;
            if (iterator.hasNext()) iterator.next();
        }
        System.out.println("Part 1: " + sum);
        System.out.println("Part 2: " + iDiv1 * iDiv2);
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

        packages.add(DIV_1);
        packages.add(DIV_2);

        while (iterator.hasNext()) {
            packages.add(new Package(iterator.next()));
            packages.add(new Package(iterator.next()));
            if (iterator.hasNext()) iterator.next();
        }
        System.out.println("Part 2: "+ (packages.headSet(DIV_1).size() + 1) * (packages.headSet(DIV_2).size() + 1));
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

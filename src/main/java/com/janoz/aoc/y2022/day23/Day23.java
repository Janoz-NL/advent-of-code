package com.janoz.aoc.y2022.day23;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day23 {

    static GrowingGrid<Elf> grove = new GrowingGrid<>(null);
    static List<Elf> elfs = new ArrayList<>();

    static List<Function<Point,Point>> moves = new ArrayList<>();

    public static void main(String[] args) {
        String file = "inputs/2022/day23.txt";
        initMoves();
        initGrove(file);

        for (int i=0; i<10; i++) moveELfs();
        grove.shrink();
        int height = grove.getHeight();
        int width = grove.getWidth();
        System.out.println("Part 1: " + (width * height - elfs.size()));

        int moves = 11;
        while (moveELfs()) moves++;

        System.out.println("Part 2: " + moves);
    }

    static Point canMove(Point... targets) {
        return Arrays.stream(targets).map(grove::peek).allMatch(Objects::isNull)?targets[1]:null;
    }

    static boolean moveELfs() {
        //elves considering moving
        List<Elf> candidates = elfs.stream()
                .filter(e -> Arrays.stream(e.position.adjacent())
                    .map(grove::peek)
                    .anyMatch(Objects::nonNull))
                .collect(Collectors.toList());
        Set<Point> targets = new HashSet<>();
        Set<Point> collisions = new HashSet<>();

        //proposeMoving
        candidates.forEach(elf -> moves.stream()
                .map(m -> m.apply(elf.position))
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(proposed -> {
                    if (targets.contains(proposed)) {
                        collisions.add(proposed);
                    } else {
                        targets.add(proposed);
                        elf.proposedPosition = proposed;
                    }
                }));

        //did someone move?
        if (targets.isEmpty()) return false;

        //move
        candidates.stream().filter(elf -> elf.proposedPosition!= null).forEach(elf -> {
            if (!collisions.contains(elf.proposedPosition)) {
                grove.put(elf.proposedPosition, elf);
                grove.put(elf.position, (Elf)null);
                elf.position = elf.proposedPosition;
            }
            elf.proposedPosition = null;
        });

        //shift moves
        moves.add(moves.remove(0));
        return true;
    }

    private static void initGrove(String file) {
        Iterator<String> stringIterator = InputProcessor.asIterator(file);
        int row = 0;
        while (stringIterator.hasNext()) {
            String line = stringIterator.next();
            for (int col=0;col < line.length(); col++) {
                if (line.charAt(col) == '#') {
                    Point p = new Point(col,row);
                    Elf e = new Elf(p);
                    grove.put(p,e);
                    elfs.add(e);
                }
            }
            row++;
        }
    }

    static void initMoves() {
        Function<Point,Point> top = p -> canMove(p.translate(-1,-1), p.translate(0,-1), p.translate(1,-1));
        Function<Point,Point> bottom = p -> canMove(p.translate(-1,1), p.translate(0,1), p.translate(1,1));
        Function<Point,Point> left = p -> canMove(p.translate(-1,-1), p.translate(-1,0), p.translate(-1,1));
        Function<Point,Point> right = p -> canMove(p.translate(1,-1), p.translate(1,0), p.translate(1,1));
        moves.add(top);
        moves.add(bottom);
        moves.add(left);
        moves.add(right);
    }

    static class Elf{
        Point position;
        Point proposedPosition;

        public Elf(Point position) {
            this.position = position;
        }
    }
}

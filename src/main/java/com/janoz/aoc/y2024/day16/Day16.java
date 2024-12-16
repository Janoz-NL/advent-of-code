package com.janoz.aoc.y2024.day16;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.algorithms.Dijsktra;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.geo.Direction;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

public class Day16 {

    static GrowingGrid<Character> map;
    static Point end;
    static Point start;

    public static void main(String[] args) {
        Iterator<String> input = InputProcessor.asIterator("inputs/2024/day16.txt");
        map = GrowingGrid.readGrid(input, (p, c) -> {
            switch (c) {
                case 'E':
                    end = p;
                    break;
                case 'S':
                    start = p;
                    break;
            }
        });
        map.put(start,'.');
        map.put(end,'.');

        PathFindingAlgorithm<Position> pfa = new Dijsktra<>((p1,p2)->true, Day16::getPrevious, Day16::score, p->false);
        pfa.calculate(getEnd());
        System.out.println("Part 1: " + pfa.getDistance(new Position(start, Direction.EAST)) );

        Set<Point> interresting = new HashSet<>();
        walk(pfa, new Position(start, Direction.EAST), interresting);
        System.out.println("Part 2: " + interresting.size());
    }

    static void walk(PathFindingAlgorithm<Position> pfa, Position p, Set<Point> visited) {
        Stack<Position> positions = new Stack<>();
        positions.add(p);

        while (!positions.isEmpty()) {
            Position curP = positions.pop();
            visited.add(curP.position);
            long distance = pfa.getDistance(curP);
            getNext(curP).stream()
                    .filter(n -> distance == pfa.getDistance(n) + score(curP, n))
                    .forEach(positions::add);
        }
    }

    static Set<Position> getEnd() {
        return end.streamNeighbour(map::isEmpty)
                .map(p -> new Position(end,p.directionTo(end))).collect(Collectors.toSet());
    }

    static boolean finished(Position p) {
        return p.position.equals(start) && p.facing.equals(Direction.EAST);
    }

    static Long score(Position p1, Position p2) {
        if (p1.position.equals(p2.position)) return 1000L;
        else return 1L;
    }
    static Collection<Position> getNext(Position position) {
        return getNeighbours(position,false);
    }

    static Collection<Position> getPrevious(Position position) {
        return getNeighbours(position,true);
    }
    static Collection<Position> getNeighbours(Position position, boolean reverse){
        Set<Position> result = new HashSet<>();
        //walk
        Point newP = position.position.translate(reverse?position.facing.reverse():position.facing);
        if (map.isEmpty(newP)) {
            result.add(new Position(newP, position.facing));
        }
        //turn
        Arrays.stream(getTurns(position.facing)).forEach(d-> result.add(new Position(position.position, d)));
        return result;
    }

    static Direction[] getTurns(Direction d) {
        if (d.x == 0) {
            return new Direction[]{Direction.EAST, Direction.WEST};
        } else {
            return new Direction[]{Direction.NORTH, Direction.SOUTH};
        }
    }

    static class Position {
        final Point position;
        final Direction facing;

        public Position(Point position, Direction facing) {
            this.position = position;
            this.facing = facing;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position1 = (Position) o;
            return Objects.equals(position, position1.position) && Objects.equals(facing, position1.facing);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, facing);
        }
    }
}

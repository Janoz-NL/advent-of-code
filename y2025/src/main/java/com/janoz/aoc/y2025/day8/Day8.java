package com.janoz.aoc.y2025.day8;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.collections.Histogram;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.collections.MergingMap;
import com.janoz.aoc.collections.Tuple;
import com.janoz.aoc.geo.Point3D;
import com.janoz.aoc.input.AocInput;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Day8 {

    static MergingMap sets;
    static Map<Point3D, Integer> circuitId;
    static List<Point3D> junctions;
    static List<Tuple<Point3D, Point3D>> connections;
    static Map<Tuple<Point3D, Point3D>, Long> distances = new AlwaysHashMap<>(
            c -> c.getLeft().squareDistance(c.getRight())
    );

    public static void main(String[] args) {
        StopWatch.start();
        LongTuple result = solve(AocInput.of(2025,8),1000);
        StopWatch.stopPrint();
        printResult(result);
    }

    static LongTuple solve(AocInput<String> input, int limit) {
        junctions = input
                .addMapper(Point3D::parse)
                .asList();
        generateConnections();
        generateCircuitIds();

        // part 1
        sets = new MergingMap();
        connections
                .stream()
                .limit(limit)
                .forEach(t -> sets.addMapping(circuitId.get(t.getLeft()), circuitId.get(t.getRight())));

        Histogram<Integer, Long> circuitSizes = Histogram.longHistogram();
        junctions.forEach(j -> circuitSizes.inc(sets.getActual(circuitId.get(j))));

        long part1 = circuitSizes.sortedStream(Comparator.comparingInt(i->i))
                .limit(3).mapToLong(circuitSizes::get)
                .reduce(1L, (a,b) -> a*b);

        //part 2
        for (int i = limit; i< connections.size(); i++) {
            Tuple<Point3D, Point3D> currentConnection = connections.get(i);
            sets.addMapping(circuitId.get(currentConnection.getLeft()), circuitId.get(currentConnection.getRight()));
            if (allConnected()) {
                return new LongTuple(
                        part1,
                        currentConnection.getLeft().x * currentConnection.getRight().x);
            }
        }
        throw new RuntimeException("No solution found");
    }

    private static void generateCircuitIds() {
        circuitId = new HashMap<>();
        for (int i = 0; i< junctions.size(); i++) {
            circuitId.put(junctions.get(i),i);
        }
    }

    /**
     *
     * @return true when all circuits are connected
     */
    static boolean allConnected() {
        Collection<Integer> ids = circuitId.values();
        int id = sets.getActual(ids.iterator().next());
        return ids.stream().allMatch(i -> sets.getActual(i) == id);
    }

    /**
     * Generate all possible connections between points sorted by distance. Shortest first
     */
    static void generateConnections() {
        SortedSet<Tuple<Point3D, Point3D>> connectionSet = new TreeSet<>(Comparator.comparingLong(distances::get));
        for (int i=0; i<junctions.size()-1; i++) {
            for (int j=i+1; j<junctions.size(); j++) {
                connectionSet.add(new Tuple<>(junctions.get(i), junctions.get(j)));
            }
        }
        connections =  connectionSet.stream().toList();
    }


    static void printResult(LongTuple results) {
        System.out.printf("Part 1 : %d\nPart 2 : %d",results.getLeft(), results.getRight());
    }
}

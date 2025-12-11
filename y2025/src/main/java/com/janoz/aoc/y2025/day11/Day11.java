package com.janoz.aoc.y2025.day11;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.algorithms.PFABuilder;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.collections.LongTuple;
import com.janoz.aoc.graphs.Edge;
import com.janoz.aoc.graphs.Node;
import com.janoz.aoc.input.AocInput;

import java.util.Map;

public class Day11 {

    public static void main(String[] args) {
        StopWatch.start();
        LongTuple result = solve(AocInput.of(2025,11));
        StopWatch.stopPrint();
        printResult(result);
    }

    static LongTuple solve(AocInput<String> input) {
        input.stream().forEach(Day11::parse);
        PathFindingAlgorithm<Node<String>> pfa = PFABuilder.forNodes(String.class).asDefault();

        long answer1 = pfa.nrOfPaths(nodes.get("you"), nodes.get("out"));
        long d2f = pfa.nrOfPaths(nodes.get("dac"), nodes.get("fft"));
        long f2d = pfa.nrOfPaths(nodes.get("fft"), nodes.get("dac"));
        long answer2;
        if (d2f == 0) {
            answer2 =
                    f2d *
                    pfa.nrOfPaths(nodes.get("svr"), nodes.get("fft")) *
                    pfa.nrOfPaths(nodes.get("dac"), nodes.get("out"));
        } else {
            answer2 =
                    d2f *
                    pfa.nrOfPaths(nodes.get("svr"), nodes.get("dac")) *
                    pfa.nrOfPaths(nodes.get("fft"), nodes.get("out"));
        }
        return new LongTuple(answer1,answer2);
    }

    static void printResult(LongTuple results) {
        System.out.printf("Part 1 : %d\nPart 2 : %d",results.getLeft(), results.getRight());
    }

    static Map<String, Node<String>> nodes = new AlwaysHashMap<>(s -> new Node<>(s));

    static void parse(String line) {
        String[] items = line.split(":? +");
        Node<String> start = nodes.get(items[0]);
        for (int i=1;i<items.length;i++) {
            Node<String> next = nodes.get(items[i]);
            new Edge<>(start,next);
        }
    }
}

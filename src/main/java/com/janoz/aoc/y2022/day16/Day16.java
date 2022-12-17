package com.janoz.aoc.y2022.day16;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.algorithms.BFS;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.collections.CollectionUtils;
import com.janoz.aoc.graphs.Edge;
import com.janoz.aoc.graphs.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day16 {

    static AlwaysHashMap<String, Node> nodes = new AlwaysHashMap<>(Node::new);
    static Map<String,Map<String, Integer>> distances = new AlwaysHashMap<>((Supplier<Map<String, Integer>>) HashMap::new);
    static Map<String,Integer> pressures = new HashMap<>();

    static int time_available = 30;

    public static void main(String[] args) {
        String file = "inputs/2022/day16.txt";
        StopWatch.start();

        loadNetwork(file);
        precalculate();

        System.out.println("Answer 1 is " + calculate(pressures.keySet()));

        time_available = 26;
        System.out.println("Answer 2 is " +
                CollectionUtils.superSets(pressures.keySet(),7,7).stream()
                        .mapToInt(permutation ->
                                        calculate(permutation) +
                                        calculate(CollectionUtils.complement(pressures.keySet(), permutation)))
                        .max()
                        .orElse(-1));
        StopWatch.stopPrint();
    }

    /**
     *
     * @param valves set of valves
     * @return the maximum of presure released by opening these valves in arbritary order
     */
    static int calculate(Set<String> valves) {
        Queue<State> queue = new LinkedList<>();
        queue.add(new State("AA"));
        if (pressures.containsKey("AA")) {
            queue.add(new State("AA", pressures.get("AA")));
        }
        int answer = 0;
        while (!queue.isEmpty()) {
            State s = queue.poll();
            answer = Math.max(answer, s.total());
            valves.stream().map(s::newState).filter(Objects::nonNull).forEach(queue::add);
        }
        return answer;
    }

    /**
     * Construct distances
     */
    static void precalculate() {
        Stream.concat(pressures.keySet().stream(),Stream.of("AA")).forEach(n1 -> {
            PathFindingAlgorithm<Node> pfa = BFS.forNodes();
            pfa.calculate(nodes.get(n1));
            pressures.keySet().stream().filter(n2 -> !n1.equals(n2)).forEach(n2 -> distances.get(n1).put(n2, (int) pfa.getDistance(nodes.get(n2))));
        });
    }

    static void loadNetwork(String file) {
        Pattern p = Pattern.compile("^Valve (.+) has flow rate=(.+); tunnels? leads? to valves? (.+)$");
        InputProcessor.asStream(file).forEach(line -> {
            Matcher m = p.matcher(line);
            if (!m.matches()) throw new RuntimeException("No match for '" + line + "'");
            String name = m.group(1);
            int rate = Integer.parseInt(m.group(2));
            if (rate > 0) pressures.put(name, rate);
            String[] neighbours = m.group(3).split(",");
            Node node = nodes.get(name);
            Arrays.stream(neighbours).map(String::trim).map(nodes::get).forEach(neighbour -> new Edge(node, neighbour));
        });
    }

    private static class State{
        private int minute = 0;
        private int pressureUntilNow = 0;
        private int pressurePerMinute = 0;
        private final List<String> opened = new ArrayList<>();
        private final String curpos;

        int total() {
            return pressureUntilNow + (time_available -minute) * pressurePerMinute;
        }

        State(String newPos) {
            opened.add(newPos);
            curpos = newPos;
        }

        State(String newPos, int pressure) {
            opened.add(newPos);
            curpos = newPos;
            minute = 1;
            pressurePerMinute = pressure;
        }

        State newState(String to) {
            if (curpos.equals(to)) return null;
            if (opened.contains(to)) return null;
            int time = distances.get(curpos).get(to) + 1; //including opening
            if (minute + time > time_available) return null;
            State newState = new State(to);
            newState.minute = minute + time;
            newState.pressureUntilNow = pressureUntilNow + (time * pressurePerMinute);
            newState.pressurePerMinute = pressurePerMinute + pressures.get(to);
            newState.opened.addAll(opened);
            return newState;
        }
    }
}

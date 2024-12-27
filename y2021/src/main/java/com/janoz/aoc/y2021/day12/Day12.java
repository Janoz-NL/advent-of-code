package com.janoz.aoc.y2021.day12;

import com.janoz.aoc.InputProcessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day12 {

    Map<String, Node> nodes = new HashMap<>();
    Node start;
    Node end;


    public static void main(String[] args) {
        Day12 d = new Day12();
        d.read("inputs/2021/day12.txt");
        System.out.println(d.travel());
        System.out.println(d.travelLonger());
        System.out.println(d.travelLonger(d.start, Collections.emptySet(),null, false));
    }

    long travel() {
        return travel(start, Collections.emptySet());
    }

    long travelLonger() {
        return travelLonger(start, Collections.emptySet(), null, false);
    }


    long travel(Node current, Set<Node> visited) {
        if (current == end) return 1;
        Set<Node> candidates = new HashSet<>(current.neighbours);
        candidates.removeAll(visited);
        long options = 0;
        for (Node neighbour:candidates ) {
            Set<Node> nextVisited = new HashSet<>(visited);
            if (current.small) nextVisited.add(current);
            options = options + travel(neighbour,nextVisited);
        }
        return options;
    }

    long travelLonger(Node current, Set<Node> visited, Node visitedSmall, boolean beentwice) {
        if (current == end) {
            return !beentwice && visitedSmall!=null?0:1;
        }
        Set<Node> candidates = new HashSet<>(current.neighbours);
        candidates.removeAll(visited);
        long result = 0;
        for (Node neighbour:candidates ) {
            Set<Node> nextVisited = new HashSet<>(visited);
            if (current.small) {
                nextVisited.add(current);
                if (visitedSmall == null && current != start) {
                    result +=
                            travelLonger(neighbour, nextVisited, null, false) +
                            travelLonger(neighbour, visited, current, false);
                } else {
                    result += travelLonger(neighbour, nextVisited, visitedSmall, beentwice || current == visitedSmall );
                }
            }  else {
                result += travelLonger(neighbour, nextVisited, visitedSmall, beentwice);
            }
        }
        return result;
    }

    void read(String input) {
        new InputProcessor<>(input, s -> s.trim().split("-")).forEach( nids -> {
            Node n1 = getOrConstruct(nids[0]);
            Node n2 = getOrConstruct(nids[1]);
            n1.neighbours.add(n2);
            n2.neighbours.add(n1);
        });
        start = nodes.get("start");
        end = nodes.get("end");
    }

    Node getOrConstruct(String nodeName) {
        Node node = nodes.getOrDefault(nodeName, new Node(nodeName));
        nodes.put(nodeName,node);
        return node;
    }
}

package com.janoz.aoc.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Node {

    Map<Node, Edge> edges = new HashMap<>();

    public Collection<Node> reachable() {
        return edges.keySet();
    }

    public Edge getTo(Node to) {
        return edges.get(to);
    }
}

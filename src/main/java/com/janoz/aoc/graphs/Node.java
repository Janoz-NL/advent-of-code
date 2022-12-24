package com.janoz.aoc.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Node<D> {

    Map<Node<D>, Edge> edges = new HashMap<>();
    D data;

    public Node() {}

    public Node(D data) {
        this.data = data;
    }

    public Collection<Node<D>> reachable() {
        return edges.keySet();
    }

    public Edge getTo(Node<D> to) {
        return edges.get(to);
    }

    public void setData(D data) {
        this.data = data;
    }

    public D getData() {
        return data;
    }
}

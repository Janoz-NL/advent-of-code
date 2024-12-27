package com.janoz.aoc.graphs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Node<D> {

    Map<Node<D>, Edge<D>> edgesTo = new HashMap<>();
    Map<Node<D>, Edge<D>> edgesFrom = new HashMap<>();
    D data;

    public Node() {}

    public Node(D data) {
        this.data = data;
    }

    public Collection<Node<D>> reachable() {
        return edgesTo.keySet();
    }

    public Collection<Node<D>> reverseRachable() {
        return edgesFrom.keySet();
    }

    public Edge<D> getTo(Node<D> to) {
        return edgesTo.get(to);
    }

    public Edge<D> getFrom(Node<D> from) {
        return edgesFrom.get(from);
    }

    public void setData(D data) {
        this.data = data;
    }

    public D getData() {
        return data;
    }
}

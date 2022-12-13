package com.janoz.aoc.graphs;

public class Edge {

    long length;
    Node from,to;

    public Edge(Node from, Node to) {
        this(from,to,1L);
    }

    public Edge(Node from, Node to, long length) {
        this.length = length;
        this.from = from;
        this.to = to;
        from.edges.put(to,this);
    }

    public long getLength() {
        return length;
    }
}

package com.janoz.aoc.graphs;

public class Edge<D> {

    long length;
    Node<D> from,to;

    public Edge(Node<D> from, Node<D> to) {
        this(from,to,1L);
    }

    public Edge(Node<D> from, Node<D> to, long length) {
        this.length = length;
        this.from = from;
        this.to = to;
        from.edgesTo.put(to,this);
        to.edgesFrom.put(from,this);
    }

    public long getLength() {
        return length;
    }
}

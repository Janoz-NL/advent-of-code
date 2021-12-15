package com.janoz.aoc.y2021.day15;

import com.janoz.aoc.geo.Point;

public class Node implements Comparable<Node>{

    Point p;
    int cost;

    public Node(Point p, int cost) {
        this.p = p;
        this.cost = cost;
    }

    Node(int x, int y, int cost) {
        p = new Point(x,y);
        this.cost = cost;
    }

    @Override
    public int compareTo(Node o) {
        return cost - o.cost;
    }
}

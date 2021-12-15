package com.janoz.aoc.y2021.day15;

import com.janoz.aoc.geo.Point;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class Day15Test {


    @Test
    void testNodeQueue() {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Node expensive = new Node(2,3,4);
        Node normal = new Node(1,2,3);
        Node cheap = new Node(4,3,2);
        queue.offer(expensive);
        queue.offer(cheap);
        queue.offer(normal);
        assertThat(queue.poll(), equalTo(cheap));
        assertThat(queue.poll(), equalTo(normal));
        assertThat(queue.poll(), equalTo(expensive));
    }

    @Test
    void testNodeQueueSame() {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Node normal = new Node(2,3,3);
        Node close = new Node(1,2,3);
        Node far = new Node(4,3,3);
        queue.offer(far);
        queue.offer(close);
        queue.offer(normal);
        queue.poll();
        queue.poll();
        queue.poll();
        assertThat(queue.isEmpty(), equalTo(true));
    }

    @Test
    void testExamplePart1() {
        Day15.read("inputs/day15example.txt");
        assertThat(Day15.dijkstra(), Matchers.equalTo(40));
        System.out.println(Day15.riskGrid.toString(i -> String.format("%3d",i)));
    }

    @Test
    void testExpandedGrid() {
        Day15.readExpanded("inputs/day15example.txt");
        assertThat( Day15.grid.get(new Point(0,0)), Matchers.equalTo(1));
        assertThat( Day15.grid.get(new Point(0,10)), Matchers.equalTo(2));
        assertThat( Day15.grid.get(new Point(10,0)), Matchers.equalTo(2));
        assertThat( Day15.grid.get(new Point(10,10)), Matchers.equalTo(3));
        assertThat( Day15.grid.get(new Point(4,19)), Matchers.equalTo(1));

    }

    @Test
    void testExamplePart2() {
        Day15.readExpanded("inputs/day15example.txt");
        System.out.println(Day15.grid.toString(i -> String.format("%1d",i)));
        assertThat( Day15.dijkstra(), Matchers.equalTo(315));
    }

}
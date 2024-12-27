package com.janoz.aoc.y2021.day15;

import com.janoz.aoc.geo.Point;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(queue.poll()).isEqualTo(cheap);
        assertThat(queue.poll()).isEqualTo(normal);
        assertThat(queue.poll()).isEqualTo(expensive);
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
        assertThat(queue.isEmpty()).isTrue();
    }

    @Test
    void testExamplePart1() {
        Day15.read("inputs/day15example.txt");
        assertThat(Day15.dijkstra()).isEqualTo(40);
        System.out.println(Day15.riskGrid.toString(i -> String.format("%3d",i)));
    }

    @Test
    void testExpandedGrid() {
        Day15.readExpanded("inputs/day15example.txt");
        assertThat( Day15.grid.get(new Point(0,0))).isEqualTo(1);
        assertThat( Day15.grid.get(new Point(0,10))).isEqualTo(2);
        assertThat( Day15.grid.get(new Point(10,0))).isEqualTo(2);
        assertThat( Day15.grid.get(new Point(10,10))).isEqualTo(3);
        assertThat( Day15.grid.get(new Point(4,19))).isEqualTo(1);

    }

    @Disabled
    @Test
    void testExamplePart2() {
        Day15.readExpanded("inputs/day15example.txt");
        System.out.println(Day15.grid.toString(i -> String.format("%1d",i)));
        assertThat( Day15.dijkstra()).isEqualTo(315);
    }

}
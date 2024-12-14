package com.janoz.aoc.y2024.day14;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.Direction;
import com.janoz.aoc.geo.Grid;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.graphics.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day14 {

    static List<Robot> robots;

    static int width = 101;
    static int height = 103;

    public static void main(String[] args) {
        part2();
    }

    static void part1() {
        readRobots("inputs/2024/day14.txt");
        moveRobots(100);
        int[] part1 = quadrants();
        System.out.println("Part 1: " + part1[0] * part1[1] * part1[2] * part1[3]);
    }

    static void part2() {
        readRobots("inputs/2024/day14.txt");
        int i=0;
        while(i < 10000) {
            i++;
            moveRobots(1);
            if (testTree()) {
                System.out.println("found " + i);
                Graphics.writePng(
                        Grid.asGrid(width, height, robots.stream().map(r -> r.position).collect(Collectors.toSet())).toImage(x -> Color.RED, BufferedImage.TYPE_INT_RGB),
                        "target/field_"+i+".png"
                );
            }
        }
    }

    static boolean testTree() {
        Set<Point> positions = robots.stream().map(r -> r.position).collect(Collectors.toSet());
        Grid<Boolean> g = Grid.asGrid(width, height, positions);
        Map<Integer, Set<Point>> map = g.connectedSets();
        return map.values().stream()
                .filter(s -> positions.contains(s.iterator().next()))
                .map(Set::size).anyMatch(s -> s > 20);
    }

    static void readRobots(String file) {
        robots = InputProcessor.asStream(file, Robot::new).collect(Collectors.toList());
    }

    static void moveRobots(int i) {
        robots.forEach(r -> {
            r.position = r.position.translate(r.speed.times(i));
            r.position.x = box(r.position.x, width);
            r.position.y = box(r.position.y,height);
        });
    }

    static int box(int input, int range) {
        if (input < 0) {
            input = input - ((input / range) - 1) * range;
        }
        return input % range;
    }

    static int[] quadrants() {
        int[] quadrants = new int[4];
        robots.forEach(r -> {
            if (r.position.x < width/2) {
                if (r.position.y < height/2) {
                    quadrants[0]++;
                } else if (r.position.y > height/2) {
                    quadrants[1]++;
                }
            } else if (r.position.x > width/2) {
                if (r.position.y < height/2) {
                    quadrants[2]++;
                } else if (r.position.y > height/2) {
                    quadrants[3]++;
                }
            }
        });
        return quadrants;
    }

    static class Robot {
        Point position;
        Direction speed;

         Robot(String line) {
            String[] parts = line.split(" ");
            position = Point.parse(parts[0].substring(2));
            speed = new Direction(Point.parse(parts[1].substring(2)));
        }
    }
}

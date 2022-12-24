package com.janoz.aoc.y2022.day24;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;
import com.janoz.aoc.algorithms.AStar;
import com.janoz.aoc.algorithms.Dijsktra;
import com.janoz.aoc.algorithms.PathFindingAlgorithm;
import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.geo.Point;
import com.janoz.aoc.graphs.Edge;
import com.janoz.aoc.graphs.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.janoz.aoc.collections.CollectionUtils.copyMap;

public class Day24 {

    static int height;
    static int width;
    static int startX;
    static int endX;
    static Point endPoint;
    static Point startPoint;
    static final Node endNode = new Node();
    static final Node startNode = new Node();
    static List<Blizzard> blizzards = new ArrayList<>();



    public static void main(String[] args) {
        String file = "inputs/2022/day24.txt";
        loadField(file);

        StopWatch.start();
        constructNodesMap();
        PathFindingAlgorithm<Node> pathFindingToEnd = Dijsktra.forNodes();
        pathFindingToEnd.calculate(endNode);
        PathFindingAlgorithm<Node> pathFindingToStart = Dijsktra.forNodes();
        pathFindingToStart.calculate(startNode);
        StopWatch.stopPrint();

        StopWatch.start();
        int time = 0;
        long result;

        do {
            time++;
            while (!forwardNodes.get(time).containsKey(startPoint.south())) time++;
            result = pathFindingToEnd.getDistance(forwardNodes.get(time).get(startPoint.south()));
        } while (result == Long.MAX_VALUE);

        time = time + (int)result;
        System.out.println();
        System.out.println("Part 1 : " + time);
        System.out.println();

        do {
            time++;
            while (!backwardNodes.get(time % (width * height)).containsKey(endPoint.north())) time++;
            result = pathFindingToStart.getDistance(backwardNodes.get(time % (width * height)).get(endPoint.north()));
        } while (result == Long.MAX_VALUE);

        time = time + (int)result;
        System.out.println("back at : " + time);

        do {
            time++;
            while (!forwardNodes.get(time % (width * height)).containsKey(startPoint.south())) time++;
            result = pathFindingToEnd.getDistance(forwardNodes.get(time % (width * height)).get(startPoint.south()));
        } while (result == Long.MAX_VALUE);

        time = time + (int)result;
        System.out.println();
        System.out.println("Part 2 : " + time);
        System.out.println();
        StopWatch.stopPrint();
    }




    private static final Map<Integer, Map<Point,Node>> forwardNodes = new HashMap<>();
    private static final Map<Integer, Map<Point,Node>> backwardNodes = new HashMap<>();

    private static void constructNodesMap() {

        for (int i=0; i<width * height; i++) {
            Map<Point, Node> forwardLayer = constructLayer(i);
            Map<Point, Node> backwardLayer = copyMap(forwardLayer, (k,v) -> new Node());

            forwardLayer.put(endPoint,endNode);
            backwardLayer.put(startPoint,startNode);

            forwardNodes.put(i,forwardLayer);
            backwardNodes.put(i,backwardLayer);
            if (i>0) {
                Map<Point, Node> previousForwardLayer = forwardNodes.get(i-1);
                stitch(previousForwardLayer, forwardLayer, endPoint);
                Map<Point, Node> previousBackwardLayer = backwardNodes.get(i-1);
                stitch(previousBackwardLayer, backwardLayer, startPoint);
            }
        }
        stitch(forwardNodes.get((width * height) - 1), forwardNodes.get(0), endPoint);
        stitch(backwardNodes.get((width * height) - 1), backwardNodes.get(0), startPoint);
    }

    private static void stitch(Map<Point, Node> current, Map<Point, Node> next, Point ignore) {
        current.keySet().stream().filter(p -> !p.equals(ignore))
                .forEach(start -> Stream.concat(Arrays.stream(start.neighbours()), Stream.of(start))
                        .filter(next::containsKey)
                        .forEach(end -> new Edge(next.get(end), current.get(start))));  //reverse edge
    }

    private static Map<Point, Node> constructLayer(int time) {
        Set<Point> map = mapAt(time);
        Map<Point, Node> layer = new HashMap<>();
        //construct inverse of map
        IntStream.range(0,width).forEach(x -> IntStream.range(0,height)
                .mapToObj(y -> new Point(x,y))
                .filter(p -> !map.contains(p))
                .forEach(p -> layer.put(p, new Node())));
        return layer;
    }



    private static Set<Point> mapAt(int time) {
        return blizzards.stream().map(b -> b.positionAt(time)).collect(Collectors.toSet());
    }

    static void loadField(String file) {
        Iterator<String> input = InputProcessor.asIterator(file);
        String line = input.next();
        width = line.length() - 2;
        startX = line.indexOf('.')-1;
        startPoint = new Point(startX,-1);
        int y=0;
        while (true) {
            line = input.next();
            if (line.charAt(1) == '#' || line.charAt(2) == '#') {
                endX = line.indexOf('.')-1;
                height = y;
                break;
            }
            parseBlizzards(line, y, blizzards);
            y++;
        }
        endPoint = new Point(endX,height);
    }

    private static void parseBlizzards(String line, int y, List<Blizzard> blizzards) {
        for (int x = 1; x < line.length()-1; x++){
            char c = line.charAt(x);
            if (c != '.') {
                blizzards.add(new Blizzard(x-1,y,c));
            }
        }
    }

    private static class Blizzard{
        Point position;
        final int deltaX;
        final int deltaY;

        Blizzard(int x, int y, char dir) {
            this.position = new Point(x,y);
            switch (dir) {
                case '^':
                    deltaX =  0;
                    deltaY = -1;
                    break;
                case '>':
                    deltaX = 1;
                    deltaY = 0;
                    break;
                case 'v':
                    deltaX = 0;
                    deltaY = 1;
                    break;
                case '<':
                    deltaX = -1;
                    deltaY =  0;
                    break;
                default: throw new RuntimeException("Unknown direction");
            }
        }

        Point positionAt(int time) {
            int x = (((time % width) * deltaX) + width + position.x) % width;
            int y = (((time % height) * deltaY) + height + position.y) % height;
            return new Point(x,y);
        }
    }
}

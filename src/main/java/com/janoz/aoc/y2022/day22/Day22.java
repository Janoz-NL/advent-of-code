package com.janoz.aoc.y2022.day22;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.geo.GrowingGrid;
import com.janoz.aoc.geo.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class Day22 {

    private static final int WIDTH = 150;

    private static final int TOP = 3;
    private static final int RIGHT = 0;
    private static final int BOTTOM = 1;
    private static final int LEFT = 2;


    static int SIDE_LENGTH;

    static final int[][] ROTATION_GRID = new int[][]{
            /*       R  B  L  T  */
            /* R */{ 2, 3, 0, 1},
            /* B */{ 1, 2, 3, 0},
            /* L */{ 0, 1, 2, 3},
            /* T */{ 3, 0, 1, 2}
    };

    static final GrowingGrid<Node> GRID = new GrowingGrid<>(null);


    public static void main(String[] args) throws IOException {
        String file = "inputs/2022/day22.txt";
        BufferedReader reader = InputProcessor.getReaderFromResource(file);

        Node start = new MapParser().parse(reader);
        String path = reader.readLine();

        NodeTraveler traveler;

        traveler = walk(start, path);
        System.out.println(extractPassword(traveler));

        if (file.endsWith("test.txt")) {
            foldCube4();
        } else {
            foldCube50();
        }

        traveler = walk(start,path);

        System.out.println(extractPassword(traveler));


    }

    static int extractPassword(NodeTraveler nt) {
        return (1000 * (nt.node.row+1)) + (4 * (nt.node.col+1)) + nt.direction;
    }

    static Node getEdge(int x, int y, int side, int idx) {
        if (side == TOP) {
            return GRID.get(  x    * SIDE_LENGTH +  idx,        y    * SIDE_LENGTH);
        } else if (side == RIGHT) {
            return GRID.get(((x+1) * SIDE_LENGTH) -  1,          y    * SIDE_LENGTH +  idx);
        } else if (side == BOTTOM) {
            return GRID.get(((x+1) * SIDE_LENGTH) - (1 + idx), ((y+1) * SIDE_LENGTH) -  1);
        } else {
            return GRID.get(  x    * SIDE_LENGTH,              ((y+1) * SIDE_LENGTH) - (1 + idx));
        }
    }

    static void stitch(int x1, int y1, int dir1, int x2, int y2, int dir2) {
        for (int i = 0; i< SIDE_LENGTH; i++) {
            Node n1 = getEdge(x1,y1,dir1, i);
            Node n2 = getEdge(x2,y2,dir2, SIDE_LENGTH - (i+1));
            n1.neighbours[dir1] = n2;
            n2.neighbours[dir2] = n1;
            n1.borderRotations.put(n2, ROTATION_GRID[dir1][dir2]);
            n2.borderRotations.put(n1, ROTATION_GRID[dir2][dir1]);
        }
    }

    //Actual puzzle
    static void foldCube50() {
        SIDE_LENGTH = 50;
        stitch(1,0,LEFT  ,0,2,LEFT);
        stitch(1,0,TOP   ,0,3,LEFT);
        stitch(2,0,TOP   ,0,3,BOTTOM);
        stitch(2,0,RIGHT ,1,2,RIGHT);
        stitch(2,0,BOTTOM,1,1,RIGHT);
        stitch(1,1,LEFT  ,0,2,TOP);
        stitch(1,2,BOTTOM,0,3,RIGHT);
    }

    //Testcase
    static void foldCube4() {
        SIDE_LENGTH = 4;
        stitch(2,0,TOP   ,0,1,TOP);
        stitch(2,0,LEFT  ,1,1,TOP);
        stitch(2,0,RIGHT ,3,2,RIGHT);
        stitch(0,1,LEFT  ,3,2,BOTTOM);
        stitch(0,1,BOTTOM,2,2,BOTTOM);
        stitch(1,1,BOTTOM,2,2,LEFT);
        stitch(2,1,RIGHT ,3,2,TOP);
    }

    static NodeTraveler walk(Node start, String path) {
        int curStep = 0;
        NodeTraveler current = new NodeTraveler(start);
        for (int i=0; i<path.length(); i++) {
            char c = path.charAt(i);
            if (c < 'A') {
                curStep = curStep * 10;
                curStep += (c - '0');
            } else {
                walk(current, curStep);
                curStep = 0;
                if (c == 'R') current.turnRight();
                else current.turnLeft();
            }
        }
        walk(current, curStep);
        return current;
    }

    static void walk(NodeTraveler current, int steps) {
        for (int i = 0; i< steps; i++) {
            if (!current.step()) break;
        }
    }


    static class Node {
        boolean wall;
        Node[] neighbours = new Node[4];
        Map<Node,Integer> borderRotations = new HashMap<>();

        int col;
        int row;

        Node(char c, int col, int row) {
            this.wall = c == '#';
            this.col = col;
            this.row = row;
            GRID.put(new Point(col,row), this);
        }

        void setNeighbour(Node neighbour, int direction) {
            if (neighbour != null) {
                neighbours[direction] = neighbour;
                neighbour.neighbours[(direction + 2) % 4] = this;
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "wall=" + wall +
                    ", col=" + col +
                    ", row=" + row +
                    '}';
        }
    }

    static class NodeTraveler {
        private Node node;
        private int direction;

        NodeTraveler(Node start) {
            this.node = start;
            this.direction = RIGHT;
        }

        void turnLeft() {
            update(3);
        }

        void turnRight() {
            update(1);
        }

        boolean step() {
            Node nextNode = node.neighbours[direction];
            if (!nextNode.wall) {
                update(node.borderRotations.getOrDefault(nextNode,0));
                node = nextNode;
                return true;
            } else {
                return false;
            }
        }

        void update(int delta) {
            direction =(direction + delta) % 4;
        }
    }

    static class MapParser {
        private Node[] topRow = new Node[WIDTH];
        private Node start;

        Node parse(BufferedReader reader) throws IOException {
            String line;
            Node[] previous = new Node[WIDTH];
            int row = 0;
            while (!(line = reader.readLine()).isEmpty()) {
                previous = constructRow(row, line, previous);
                row++;
                updateTop(previous);
            }
            Arrays.stream(topRow).filter(Objects::nonNull).forEach(topNode -> {
                Node candidate = topNode;
                while (candidate.neighbours[BOTTOM] != null) candidate = candidate.neighbours[BOTTOM];
                topNode.setNeighbour(candidate,TOP);
            });
            return start;
        }

        private Node[] constructRow(int row, String input, Node[] previous) {
            Node first;
            Node[] result = new Node[WIDTH];
            int col=0;
            while (input.charAt(col) == ' ') col++;

            first = new Node(input.charAt(col), col, row);
            first.setNeighbour(previous[col], TOP);
            result[col] = first;

            if (start == null) start = first;

            Node n;
            col++;
            while (col < input.length() &&
                    (input.charAt(col) == '.' || input.charAt(col) == '#')) {
                n = new Node(input.charAt(col), col, row);
                n.setNeighbour(previous[col], TOP);
                n.setNeighbour(result[col -1], LEFT);
                result[col] = n;
                col++;
            }
            first.setNeighbour(result[col -1], LEFT);

            return result;
        }

        private void updateTop(Node[] row) {
            IntStream.range(0,WIDTH).filter(i -> topRow[i] == null).forEach(i -> topRow[i] = row[i]);
        }
    }
}
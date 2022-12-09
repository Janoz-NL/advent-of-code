package com.janoz.aoc.y2022.day7;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Day07 {

    static Node root = new Node("dir /");
    static Node current = root;

    public static void main(String[] args) throws IOException {
        StopWatch.start();

        String file = "inputs/2022/day07.txt";

        InputProcessor.asStream(file, Day07::parse).filter(Objects::nonNull).forEach(Day07::consoleLineConsumer);

        Set<Node> allNodes = new HashSet<>();
        root.traverse(allNodes::add);

        System.out.println("Answer 1: " +
            allNodes.stream().filter(n -> !n.isFile).filter(n -> n.getSize() <= 100000).mapToLong(Node::getSize).sum());

        long total = 70000000L;
        long required = 30000000L;
        long needed = root.getSize() + required - total;

        System.out.println("Answer 2: " +
            allNodes.stream().filter(n -> !n.isFile).filter(n -> n.getSize() >= needed).mapToLong(Node::getSize).min().getAsLong());

        StopWatch.stopPrint();
    }

    private static void consoleLineConsumer(Object o) {
        if (o instanceof Command) {
            processCommand((Command)o);
        } else {
            processLs((Node)o);
        }
    }

    private static void processCommand(Command c) {
        if ("/".equals(c.target)) {
            current = root;
        } else if ("..".equals(c.target)) {
            current = current.parent;
        } else {
            current = current.getDir(c.target);
        }
    }

    private static void processLs(Node n) {
        current.addNode(n);
    }

    static Object parse(String line) {
        if ("$ ls".equals(line)) {
            return null;
        }
        if (line.startsWith("$")) {
            return new Command(line.split(" ")[2]);
        }
        return new Node(line);
    }
}

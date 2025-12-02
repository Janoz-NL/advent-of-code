package com.janoz.aoc.y2024.day9;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.janoz.aoc.StopWatch;
import com.janoz.aoc.input.AocInput;

public class Day9 {

    public static void main(String[] args) {
        StopWatch.start();
        String filesystem = AocInput.of(2024,9).asString(s->s);
        List<Node> nodes = parse(filesystem);
        List<Node> nodesPart1 = nodes;
        List<Node> nodesPart2 = deepCopy(nodes);
        compact(nodesPart1);
        System.out.println(checksum(nodesPart1));
        compactDefrag(nodesPart2);
        System.out.println(checksum(nodesPart2));
        StopWatch.stopPrint();
    }

    static List<Node> deepCopy(List<Node> input) {
        return input.stream().map(n -> new Node(n.id,n.length,n.pos)).collect(Collectors.toList());
    }

    static void compact(List<Node> nodes) {
        while(!compactSingle(nodes));
    }

    static long checksum(List<Node> nodes) {
        return nodes.stream().mapToLong(n -> {
            long sum = 0;
            for (long i=n.pos; i<n.pos+n.length; i++) {
                sum += i * n.id;
            }
            return sum;
        }).sum();
    }

    static boolean compactSingle(List<Node> nodes) {
        Node node = nodes.remove(nodes.size()-1);
        for (int i=0; i< nodes.size()-1; i++) {
            Node current = nodes.get(i);
            Node next = nodes.get(i+1);
            int freeSpace = next.pos - (current.pos + current.length);
            if (freeSpace > node.length) {
                node.pos = current.pos + current.length;
                nodes.add(i+1, node);
                return false;
            }
            if (freeSpace > 0) {
                Node part = new Node(node.id, freeSpace,current.pos + current.length);
                node.length = node.length - freeSpace;
                nodes.add(i+1, part);
            }
        }
        Node last = nodes.get(nodes.size()-1);
        node.pos = last.pos + last.length;
        nodes.add(node);
        return true;
    }

    static void compactDefrag(List<Node> nodes) {
        int lastId = nodes.get(nodes.size()-1).id;
        for (int id = lastId; id>0; id--) {
            int idx = findIndex(nodes, id);
            Node node = nodes.get(idx);
            for (int i=0; i< idx; i++) {
                Node current = nodes.get(i);
                Node next = nodes.get(i+1);
                int freeSpace = next.pos - (current.pos + current.length);
                if (freeSpace>=node.length) {
                    nodes.remove(idx);
                    node.pos = current.pos+current.length;
                    nodes.add(i+1,node);
                    break;
                }
            }
        }
    }

    static int findIndex(List<Node> nodes, int id) {
        for (int i=id; i< nodes.size(); i++) {
            if (nodes.get(i).id == id) return i;
        }
        throw new NoSuchElementException();
    }

    static List<Node> parse(String filesystem) {
        int curId = 0;
        int curNodePos = 0;
        List<Node> nodes = new ArrayList<>();
        for (int i=0; i<filesystem.length(); i+=2) {
            int length = filesystem.charAt(i) - '0';
            nodes.add(new Node(curId, length, curNodePos));
            curId++;
            curNodePos += length;
            if (i+1 < filesystem.length()) {
                curNodePos += filesystem.charAt(i+1) - '0';
            }
        }
        return nodes;
    }

    static String fileSystemToString(List<Node> nodes) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<nodes.size();i++) {
            Node node = nodes.get(i);
            sb.append(String.valueOf(node.id).repeat(node.length));
            if (i+1 < nodes.size()) {
                Node next = nodes.get(i+1);
                sb.append(".".repeat(next.pos - node.pos - node.length));
            }
        }
        return sb.toString();
    }

    static class Node {
        int id;
        int length;
        int pos;

        public Node(int id, int length, int pos) {
            this.id = id;
            this.length = length;
            this.pos = pos;
        }
    }
}

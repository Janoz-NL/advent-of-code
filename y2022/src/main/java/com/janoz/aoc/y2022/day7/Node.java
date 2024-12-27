package com.janoz.aoc.y2022.day7;

import com.janoz.aoc.collections.AlwaysHashMap;

import java.util.function.Consumer;

public class Node {

    AlwaysHashMap<String, Node> children = new AlwaysHashMap<>(Node::asDir);
    Node parent;

    Long size;
    boolean isFile;
    String name;

    public Node(String s) {
        String[] parts = s.split(" ");
        if ("dir".equals(parts[0])) {
            isFile = false;
        } else {
            isFile = true;
            size = Long.parseLong(parts[0]);
        }
        name = parts[1];
    }

    public Node getDir(String target) {
        Node node = children.get(target);
        node.parent = this;
        return node;
    }

    public void addNode(Node n) {
        if (!children.containsKey(n.name)) {
            n.parent = this;
            children.put(n.name,n);
        }
    }

    public static Node asDir(String name) {
        return new Node("dir " + name);
    }

    public long getSize() {
        if (size == null) {
            this.size = children.values().stream().mapToLong(Node::getSize).sum();
        }
        return size;
    }

    public void print(String prefix) {
        System.out.print(prefix);
        System.out.print(name);
        System.out.print('\t');
        System.out.print(size);
        System.out.println();
        children.values().forEach(n -> n.print("  " + prefix));
    }

    public void traverse(Consumer<Node> c) {
        c.accept(this);
        children.values().forEach(n -> n.traverse(c));
    }
}

package com.janoz.aoc.y2021.day12;

import java.util.ArrayList;
import java.util.List;

public class Node {

    String name;
    boolean small;
    List<Node> neighbours = new ArrayList<>();

    Node(String name)  {
        this.small = Character.isLowerCase(name.charAt(0));
        this.name = name;
    }
}

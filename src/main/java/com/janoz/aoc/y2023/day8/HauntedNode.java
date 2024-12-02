package com.janoz.aoc.y2023.day8;

class HauntedNode {
    String name;
    HauntedNode left;
    HauntedNode right;

    public HauntedNode(String name) {
        this.name = name;
    }


    HauntedNode next(Character c) {
        switch (c){
            case 'L': return left;
            case 'R': return right;
        }
        throw new RuntimeException("Unknown direction " + c);
    }
}

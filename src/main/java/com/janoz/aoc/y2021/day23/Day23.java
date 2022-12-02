package com.janoz.aoc.y2021.day23;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import static com.janoz.aoc.y2021.day23.Amphiod.*;

public class Day23 {

    static PriorityQueue<Hall> states = new PriorityQueue<>();
    static HashMap<Integer,Integer> hallHashes = new HashMap<>();

    public static void main(String[] args) {
        int part = 2;
        Hall hall = new Hall(new Room[]{
                Room.initialRoom(AMBER, part, BRONZE, DESSERT),
                Room.initialRoom(BRONZE, part, AMBER, COPPER),
                Room.initialRoom(COPPER, part, AMBER, BRONZE),
                Room.initialRoom(DESSERT, part, DESSERT, COPPER)
//                Room.initialRoom(AMBER, part, BRONZE, AMBER),
//                Room.initialRoom(BRONZE, part, COPPER, DESSERT),
//                Room.initialRoom(COPPER, part, BRONZE, COPPER),
//                Room.initialRoom(DESSERT, part, DESSERT, AMBER)
        });


        System.out.println(solve(Collections.singletonList(hall)));
    }

    static int solve(List<Hall> halls) {
        int result = Integer.MAX_VALUE;
        for (Hall hall:halls) {
            if (hall.potentialScore > 50000) continue;
            if (hall.isDone()) {
                result = Math.min(hall.score, result);
            } else {
                result = Math.min(solve(hall.nextStates()),result);
            }
        }
        return result;
    }
}

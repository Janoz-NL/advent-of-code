package com.janoz.aoc.y2022.day19;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.StopWatch;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Day19 {

    public static void main(String[] args) {
        String file = "inputs/2022/day19.txt";
        StopWatch.start();
        long result = InputProcessor.asStream(file, Blueprint::new).mapToInt(bp -> maxGeode(bp, 24) * bp.id).sum();
        System.out.println(result);
        StopWatch.stopPrint();

        StopWatch.start();
        Iterator<Blueprint> bp = InputProcessor.asIterator(file, Blueprint::new);
        long i1= maxGeode(bp.next(), 32);
        long i2= maxGeode(bp.next(), 32);
        long i3= maxGeode(bp.next(), 32);
        System.out.println(i1 * i2 * i3);
        StopWatch.stopPrint();
    }



    private static int maxGeode(Blueprint bp, int minutes) {
        Queue<State> queue = new LinkedList<>();
        int answer = 0;
        queue.add(new State(bp, minutes));
        State current;

        while (!queue.isEmpty()) {
            current = queue.poll();

            //answer is at least minimal possible
            answer = Math.max(answer, current.getMinPotential()[Blueprint.GEODE]);

            //search only when potential
            if (current.getMaxPotential()[Blueprint.GEODE] > answer) {
                current.possibleNext().forEach(queue::add);
            }
        }
        return answer;
    }


}

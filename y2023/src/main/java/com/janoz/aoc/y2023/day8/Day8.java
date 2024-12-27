package com.janoz.aoc.y2023.day8;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.janoz.aoc.InputProcessor;
import com.janoz.aoc.collections.AlwaysHashMap;
import com.janoz.aoc.collections.InfiniteIterator;


public class Day8 {


    public static void main(String[] args) {
        Day8 d = new Day8();
        d.read("inputs/2023/day08.txt");
        System.out.println(d.solve1());
        d.directions.reset();
        System.out.println(d.solve2());
    }

    void read(String file) {
        Iterator<String> it = InputProcessor.asIterator(file);
        directions = InfiniteIterator.loopingCharIterator(it.next());
        it.next();
        while (it.hasNext()) {
            parseNode(it.next());
        }
    }

    long solve1(){
        return findPath(nodes.get("AAA"), n -> n.name.equals("ZZZ")).getValue();
    }

    Map.Entry<HauntedNode, Long> findPath(HauntedNode start, Predicate<HauntedNode> targetPredicate) {
        HauntedNode current = start;
        long steps = 0;
        while (!targetPredicate.test(current)) {
            steps++;
            current = current.next(directions.next());
        }
        return new AbstractMap.SimpleEntry<>(current, steps);
    }





    long solve2() {
        List<HauntedNode> currentPaths = new ArrayList<>();
        nodes.values().stream().filter(n -> n.name.endsWith("A")).forEach(currentPaths::add);
        SpecialList pathLengths = new SpecialList();
//        for (int i=0; i<currentPaths.size(); i++) {
//            HauntedPath p = paths.get(currentPaths.get(i));
//            pathLengths.add(p.steps);
//            currentPaths.set(i, p.end);
//        }
//
//        while (!pathLengths.allSame()) {
//            int index = pathLengths.minIndex();
//            HauntedPath p = paths.get(currentPaths.get(index));
//            pathLengths.add(index, p.steps + pathLengths.get(index));
//            currentPaths.set(index, p.end);
//        }

        return pathLengths.get(0);
    }

    AlwaysHashMap<String, HauntedNode> nodes = new AlwaysHashMap<>(HauntedNode::new);
    InfiniteIterator<Character> directions;


    private void parseNode(String s) {
        addNode(s.substring(0,3), s.substring(7,10), s.substring(12,15));

    }

    private void addNode(String name, String left, String right) {
        HauntedNode n = nodes.get(name);
        n.left = nodes.get(left);
        n.right = nodes.get(right);
    }



    static class SpecialList extends ArrayList<Long> {

        boolean allSame() {
            for (int i=1; i<size(); i++) {
                if (get(i) != get(0)) return false;
            }
            return true;
        }
        int minIndex() {
            int index = 0;
            long minVal = get(0);
            for (int i=1; i<size(); i++) {
                if (get(i) < minVal) {
                    index = i;
                    minVal = get(i);
                }
            }
            return index;
        }
    }

}
